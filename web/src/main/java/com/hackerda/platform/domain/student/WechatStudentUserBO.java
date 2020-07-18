package com.hackerda.platform.domain.student;

import com.hackerda.platform.pojo.StudentUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class WechatStudentUserBO {

    private StudentUser studentUser;

    private List<WechatOpenidBO> wechatOpenidList;


    public WechatStudentUserBO(StudentUser studentUser){
        this.studentUser = studentUser;
    }
}
