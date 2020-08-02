package com.hackerda.platform.infrastructure.repository.course.timetable;

import com.hackerda.platform.domain.student.WechatStudentUserBO;
import com.hackerda.platform.infrastructure.database.model.CourseTimetableDetailDO;

import java.util.List;

public interface CourseTimetableSpiderFacade {

    List<CourseTimetableDetailDO> getCurrentTermTableByAccount(WechatStudentUserBO wechatStudentUserBO);

    List<CourseTimetableDetailDO> getByClassID(String termYear, int termOrder, String classId);
}
