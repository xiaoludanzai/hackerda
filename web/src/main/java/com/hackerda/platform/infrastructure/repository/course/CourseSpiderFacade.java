package com.hackerda.platform.infrastructure.repository.course;

import com.hackerda.platform.infrastructure.database.model.Course;

public interface CourseSpiderFacade {

    Course get(String courseId, String sequenceNumber, String termYear, int termOrder);
}
