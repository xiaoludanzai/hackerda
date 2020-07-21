package com.hackerda.platform.domain.course;

import java.util.List;

public interface CourseRepository {

    CourseBO getByKey(CourseUniqueKey courseUniqueKey);

    List<CourseBO> getByKeyList(List<CourseUniqueKey> courseUniqueKeyList);
}
