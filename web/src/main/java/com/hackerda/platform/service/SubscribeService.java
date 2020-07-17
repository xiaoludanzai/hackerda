package com.hackerda.platform.service;

import com.hackerda.platform.dao.ScheduleTaskDao;
import com.hackerda.platform.pojo.ScheduleTask;
import com.hackerda.platform.pojo.StudentUser;
import com.hackerda.platform.pojo.constant.SubscribeScene;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Yuki
 * @date 2018/11/10 21:54
 */
@Slf4j
@Service
@AllArgsConstructor
public class SubscribeService {

    private final ScheduleTaskDao scheduleTaskDao;
    private final OpenIdService openIdService;

    public Set<StudentUser> getGradeUpdateSubscribeStudent(){
        List<ScheduleTask> scheduleTaskList = scheduleTaskDao.selectByPojo(new ScheduleTask().setScene(Integer.parseInt(SubscribeScene.GRADE_AUTO_UPDATE.getScene())));

        return scheduleTaskList.stream()
                .map(x-> openIdService.getStudentByOpenId(x.getOpenid(), x.getAppid()))
                .filter(Objects::nonNull)
                .filter(StudentUser::getIsCorrect)
                .collect(Collectors.toSet());
    }

}
