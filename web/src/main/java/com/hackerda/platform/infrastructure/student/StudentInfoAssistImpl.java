package com.hackerda.platform.infrastructure.student;

import com.google.common.collect.Sets;
import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.student.StudentInfoAssist;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class StudentInfoAssistImpl implements StudentInfoAssist {

    private final Set<StudentAccount> studentAccountSet = Sets.newHashSet(new StudentAccount("2014025838"));

    @Override
    public boolean inLoginWhiteList(StudentAccount studentAccount) {
        return studentAccountSet.contains(studentAccount);
    }

    @Override
    public boolean needToCheckWechatCommentUser() {
        return false;
    }
}
