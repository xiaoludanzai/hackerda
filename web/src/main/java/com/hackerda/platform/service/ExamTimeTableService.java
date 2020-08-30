package com.hackerda.platform.service;

import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.domain.student.StudentRepository;
import com.hackerda.platform.infrastructure.database.model.*;
import com.hackerda.platform.utils.DateUtils;
import com.hackerda.spider.exception.PasswordUnCorrectException;
import com.hackerda.spider.exception.UrpRequestException;
import com.hackerda.spider.support.UrpExamTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author junrong.chen
 * @date 2018/11/28
 */
@Slf4j
@Service
public class ExamTimeTableService {
    @Resource
    private NewUrpSpiderService newUrpSpiderService;

    @Resource
    private RoomService roomService;

    @Resource
    private StudentRepository studentRepository;


    public List<Exam> getExamTimeListFromSpider(int account) {

        WechatStudentUserBO student = studentRepository.getWetChatUserByAccount(account);
        if (student == null) {
            throw new PasswordUnCorrectException();
        }
        List<UrpExamTime> examTime;
        try {
            examTime = newUrpSpiderService.getExamTime(student);
        } catch (UrpRequestException e) {

            if (e.getCode() >= 500) {
                return Collections.emptyList();
            }
            throw e;
        }


        return examTime.stream()
                .filter(x -> StringUtils.isNotEmpty(x.getDate()))
                .map(x -> {
                            if (StringUtils.isEmpty(x.getExamTime())) {
                                return new Exam()
                                        .setCourse(getCourseFromExamText(x.getCourseName()))
                                        .setDate(new Date())
                                        .setExamName(x.getExamName())
                                        .setClassRoom(new UrpClassroom());

                            }

                            String[] timeSplit = x.getExamTime().split("-");
                            return new Exam()
                                    .setCourse(getCourseFromExamText(x.getCourseName()))
                                    .setDate(DateUtils.localDateToDate(x.getDate(), DateUtils.DEFAULT_PATTERN))
                                    .setExamName(x.getExamName())
                                    .setStartTime(timeSplit[0])
                                    .setEndTime(timeSplit[1])
                                    .setClassRoom(getClassRoomFromText(x.getLocation()))
                                    .setExamDay(x.getWeek())
                                    .setExamWeekOfTerm(x.getWeekOfTerm());
                        }

                )
                .collect(Collectors.toList());
    }


    /**
     * 由于这个接口非常耗时，而且请求量非常高，所以不再每次都从抓取结果，每天更新一次
     *
     * @param account
     * @return
     */
    public List<Exam> getExamTimeList(int account) {

        return Collections.emptyList();
    }

    private Course getCourseFromExamText(String examText) {

        String pattern = "（(.*?)-(.*?)）(.*)";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(examText);

        if (m.find()) {
            String num = m.group(1);
            String order = m.group(2);
            String courseName = m.group(3);
            Course course = new Course().setCourseOrder(order).setName(courseName).setNum(num);
            if (course == null) {
                log.error("can not find course {} {}", num, order);
            }

            return course;

        } else {
            log.error("can not parse exam text {}", examText);
            return new Course();
        }

    }

    private UrpClassroom getClassRoomFromText(String date) {
        String[] split = date.split(" ");
        UrpClassroom room = roomService.getClassRoomByName(split[3]);
        if (room != null) {
            return room;
        }
        return new UrpClassroom();
    }


}
