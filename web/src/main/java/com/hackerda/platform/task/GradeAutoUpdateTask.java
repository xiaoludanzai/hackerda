package com.hackerda.platform.task;

import com.hackerda.platform.MDCThreadPool;
import com.hackerda.platform.domain.grade.GradeBO;
import com.hackerda.platform.domain.grade.GradeMsgSender;
import com.hackerda.platform.domain.grade.GradeOverviewBO;
import com.hackerda.platform.application.GradeQueryApp;
import com.hackerda.platform.domain.student.StudentRepository;
import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.domain.constant.SubscribeScene;
import com.hackerda.spider.exception.UrpEvaluationException;
import com.hackerda.spider.exception.UrpException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author Yuki
 * @date 2019/6/6 10:55
 */
@Slf4j
@Service
public class GradeAutoUpdateTask extends BaseSubscriptionTask {
    //这里设置拒绝策略为调用者运行，这样可以降低产生任务的速率
    private static ExecutorService gradeAutoUpdatePool = new MDCThreadPool(8, 8,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), r -> new Thread(r, "gradeUpdate"));

    @Autowired
    private GradeQueryApp gradeQueryApp;
    @Autowired
    private GradeMsgSender gradeMsgSender;
    @Autowired
    private StudentRepository studentRepository;

    private static final BlockingQueue<UrpFetchTask> queue = new LinkedBlockingQueue<>();

    @Value("${scheduled.gradeUpdate}")
    private String updateSwitch;

    //    @Async
    @Scheduled(cron = "0 0/20 * * * ? ")
    //每20分钟执行一次
    public void autoUpdateGrade() {
        if (!isSwitchOn()) {
            return;
        }
        log.info("grade update task run");
        while (true) {
            try {

                Set<UrpFetchTask> fetchTasks = studentRepository.getSubscribe(SubscribeScene.GRADE_AUTO_UPDATE)
                        .stream().map(UrpFetchTask::new)
                        .collect(Collectors.toSet());

                queue.addAll(fetchTasks);

                log.info("grade update task run, queue size {}", queue.size());

                for (int x = 0; x < 8; x++) {
                    CompletableFuture.runAsync(() -> {
                        UrpFetchTask task;
                        try {
                            while ((task = queue.take()) != null) {
                                UUID uuid = UUID.randomUUID();
                                MDC.put("traceId", "gradeUpdateTask-" + uuid.toString());
                                try {
                                    processScheduleTask(task);
                                } catch (UrpException e) {
                                    // TODO  这个可以根据异常类来优化
                                    task.timeoutCount++;
                                    if (task.timeoutCount < 3) {
                                        queue.add(task);
                                    } else {
                                        log.error("grade update task {} urp exception {}", task, e.getMessage());
                                    }
                                } catch (UrpEvaluationException e) {
                                    log.debug("{} 评估未完成", task);
                                } catch (Exception e) {
                                    log.error("grade update task {} error ", task, e);
                                } finally {
                                    MDC.clear();
                                }
                            }
                        } catch (InterruptedException e) {
                            log.error("get grade update task error", e);
                        }

                    }, gradeAutoUpdatePool);
                }
            } catch (Exception e) {
                continue;
            }
            break;
        }


    }

    /**
     * 处理每一个定时任务
     */
    private void processScheduleTask(UrpFetchTask urpFetchTask) {

        WechatStudentUserBO student = urpFetchTask.student;
        if (student == null) {
            return;
        }

        GradeOverviewBO overview = gradeQueryApp.getGradeOverview(student);
        List<GradeBO> sendGrade = overview.getNeedToSendGrade();
        gradeMsgSender.sendUpdateGradeToStudent(student, sendGrade);

    }

    /**
     * 根据配置文件中的属性，判断该定时任务是否可用
     *
     * @return 可用结果
     */
    boolean isSwitchOn() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        log.info("switch {} hour {}", updateSwitch, hour);
        return BooleanUtils.toBoolean(updateSwitch) && (hour > 7 || hour < 1);
    }

    @Data
    private static class UrpFetchTask {
        private int timeoutCount;
        private WechatStudentUserBO student;

        UrpFetchTask(WechatStudentUserBO student) {
            this.student = student;
        }
    }


}