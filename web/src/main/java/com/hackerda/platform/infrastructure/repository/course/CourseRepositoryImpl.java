package com.hackerda.platform.infrastructure.repository.course;

import com.hackerda.platform.infrastructure.database.dao.CourseDao;
import com.hackerda.platform.domain.course.CourseBO;
import com.hackerda.platform.domain.course.CourseRepository;
import com.hackerda.platform.domain.course.CourseUniqueKey;
import com.hackerda.platform.infrastructure.database.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseRepositoryImpl implements CourseRepository {

    @Autowired
    private CourseSpiderFacade courseSpiderFacade;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseAdapter courseAdapter;


    @Override
    public CourseBO getByKey(CourseUniqueKey courseUniqueKey) {
        return null;
    }

    @Override
    public List<CourseBO> getByKeyList(List<CourseUniqueKey> courseUniqueKeyList) {
        List<Course> searchList = courseUniqueKeyList.stream()
                .map(x -> new Course()
                        .setNum(x.getCourseId())
                        .setCourseOrder(x.getSequenceNumber())
                        .setTermYear(x.getTermYear())
                        .setTermOrder(x.getTermOrder()))
                .collect(Collectors.toList());

        List<Course> courseList = courseDao.selectByCourseList(searchList);


        if(courseList.size() == courseUniqueKeyList.size()){
            return courseList.stream().map(x-> courseAdapter.toBO(x)).collect(Collectors.toList());
        }


        return null;
    }
}
