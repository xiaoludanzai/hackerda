package com.hackerda.platform.repository.course;

import com.hackerda.platform.domain.course.CourseBO;
import com.hackerda.platform.domain.course.CourseRepository;
import com.hackerda.platform.domain.course.CourseUniqueKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseRepositoryImpl implements CourseRepository {

    @Autowired
    private CourseSpiderFacade courseSpiderFacade;


    @Override
    public CourseBO getByKey(CourseUniqueKey courseUniqueKey) {
        return null;
    }

    @Override
    public List<CourseBO> getByKeyList(List<CourseUniqueKey> courseUniqueKeyList) {
        return null;
    }
}
