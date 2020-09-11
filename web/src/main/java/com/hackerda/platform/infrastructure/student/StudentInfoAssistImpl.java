package com.hackerda.platform.infrastructure.student;

import com.google.common.collect.Sets;
import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.student.StudentInfoAssist;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class StudentInfoAssistImpl implements StudentInfoAssist {


    @Override
    public boolean needToCheckWechatCommentUser() {
        return false;
    }
}
