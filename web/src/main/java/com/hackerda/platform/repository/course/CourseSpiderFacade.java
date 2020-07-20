package com.hackerda.platform.repository.course;

import com.hackerda.platform.pojo.Course;

public interface CourseSpiderFacade {

    Course get(String courseId, String sequenceNumber, String termYear, int termOrder);
}
