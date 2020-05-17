package com.hackerda.platform.dao;

import com.hackerda.platform.mapper.UrpCourseMapper;
import com.hackerda.platform.pojo.UrpCourse;
import com.hackerda.platform.pojo.example.UrpCourseExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Yuki
 * @date 2019/8/14 21:41
 */
@Service
public class UrpCourseDao {

    @Resource
    private UrpCourseMapper urpCourseMapper;

    public void insertUrpCourse(UrpCourse urpCourse){
        urpCourseMapper.insertSelective(urpCourse);
    }

    public UrpCourse getUrpCourseByCourseId(String courseId){
        UrpCourseExample urpCourseExample = new UrpCourseExample();
        urpCourseExample.createCriteria()
                .andCourseNumberEqualTo(courseId);
        return urpCourseMapper.selectByExample(urpCourseExample).stream().findFirst().orElse(null);
    }

    public boolean ifExistCourse(String uid){
        return urpCourseMapper.ifExistCourse(uid);
    }
}
