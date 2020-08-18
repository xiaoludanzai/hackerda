package com.hackerda.platform.domain.community;

import com.hackerda.platform.domain.student.StudentAccount;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StudentPoster extends Poster {

    private final StudentAccount studentAccount;

    private final String name;

    private final String sex;

    private final Integer urpClassNum;

    private final String academyName;

    private final String subjectName;

    private final String className;


    public String getGrade() {
        return studentAccount.getGrade();
    }

    public String getCollage() {
        return academyName;
    }

    public String getRealName() {
        return name;
    }




    private StudentPoster(String userName, String nickName, String avatarUrl, StudentAccount studentAccount,
                         String name, String sex, Integer urpClassNum, String academyName, String subjectName, String className) {
        super(userName, nickName, avatarUrl);
        this.studentAccount = studentAccount;
        this.name = name;
        this.sex = sex;
        this.urpClassNum = urpClassNum;
        this.academyName = academyName;
        this.subjectName = subjectName;
        this.className = className;
    }

    public static StudentPosterBuilder builder(String userName, String nickName, String avatarUrl) {
        return new StudentPosterBuilder(userName, nickName, avatarUrl);
    }

    public static class StudentPosterBuilder {
        /** 用户名称 **/
        private final String userName;

        /** 用户名称 **/
        private final String nickName;

        /** 用户名称 **/
        private final String avatarUrl;

        private StudentAccount studentAccount;
        private String name;
        private String sex;
        private Integer urpClassNum;
        private String academyName;
        private String subjectName;
        private String className;

        StudentPosterBuilder(String userName, String nickName, String avatarUrl) {
            this.userName = userName;
            this.nickName = nickName;
            this.avatarUrl = avatarUrl;
        }

        public StudentPosterBuilder studentAccount(StudentAccount studentAccount) {
            this.studentAccount = studentAccount;
            return this;
        }

        public StudentPosterBuilder name(String name) {
            this.name = name;
            return this;
        }

        public StudentPosterBuilder sex(String sex) {
            this.sex = sex;
            return this;
        }

        public StudentPosterBuilder urpClassNum(Integer urpClassNum) {
            this.urpClassNum = urpClassNum;
            return this;
        }

        public StudentPosterBuilder academyName(String academyName) {
            this.academyName = academyName;
            return this;
        }

        public StudentPosterBuilder subjectName(String subjectName) {
            this.subjectName = subjectName;
            return this;
        }

        public StudentPosterBuilder className(String className) {
            this.className = className;
            return this;
        }

        public StudentPoster build() {
            return new StudentPoster(userName, nickName, avatarUrl, studentAccount, name, sex, urpClassNum, academyName,
                    subjectName, className);
        }

        public String toString() {
            return "StudentPoster.StudentPosterBuilder(studentAccount=" + this.studentAccount + ", name=" + this.name + ", sex=" + this.sex + ", urpClassNum=" + this.urpClassNum + ", academyName=" + this.academyName + ", subjectName=" + this.subjectName + ", className=" + this.className + ")";
        }
    }
}
