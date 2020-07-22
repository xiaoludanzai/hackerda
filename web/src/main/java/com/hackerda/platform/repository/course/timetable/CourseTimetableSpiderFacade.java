package com.hackerda.platform.repository.course.timetable;

import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.pojo.CourseTimetableDetailDO;

import java.util.List;

public interface CourseTimetableSpiderFacade {

    List<CourseTimetableDetailDO> getCurrentTermTableByAccount(StudentUserBO studentUserBO);

    List<CourseTimetableDetailDO> getByClassID(String termYear, int termOrder, StudentUserBO studentUserBO);
}
