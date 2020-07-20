package com.hackerda.platform.domain.course;

import com.hackerda.platform.domain.course.CourseBO;

import java.util.List;

public interface CourseRepository {

    CourseBO getByKey(CourseUniqueKey courseUniqueKey);

    List<CourseBO> getByKeyList(List<CourseUniqueKey> courseUniqueKeyList);
}
