package com.hackerda.platform.domain.community;

import com.hackerda.platform.domain.student.StudentAccount;
import com.hackerda.platform.domain.user.Gender;
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




    private StudentPoster(String userName, String nickName, String avatarUrl, Gender gender,
                          StudentAccount studentAccount,
                          String name, String sex, Integer urpClassNum, String academyName, String subjectName, String className) {
        super(userName, nickName, avatarUrl, gender);
        this.studentAccount = studentAccount;
        this.name = name;
        this.sex = sex;
        this.urpClassNum = urpClassNum;
        this.academyName = academyName;
        this.subjectName = subjectName;
        this.className = className;
    }

    public static StudentPosterBuilder builder(String userName, String nickName, String avatarUrl, Gender gender) {
        return new StudentPosterBuilder(userName, nickName, avatarUrl, gender);
    }

    public static class StudentPosterBuilder {
        /** 用户名称 **/
        private final String userName;

        /** 用户名称 **/
        private final String nickName;

        /** 用户名称 **/
        private final String avatarUrl;

        private final Gender gender;

        private StudentAccount studentAccount;
        private String name;
        private String sex;
        private Integer urpClassNum;
        private String academyName;
        private String subjectName;
        private String className;


        StudentPosterBuilder(String userName, String nickName, String avatarUrl,  Gender gender) {
            this.userName = userName;
            this.nickName = nickName;
            this.avatarUrl = avatarUrl;
            this.gender = gender;
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
            return new StudentPoster(userName, nickName, avatarUrl, gender, studentAccount, name, sex, urpClassNum,
                    academyName,
                    subjectName, className);
        }

        public String toString() {
            return "StudentPoster.StudentPosterBuilder(studentAccount=" + this.studentAccount + ", name=" + this.name + ", sex=" + this.sex + ", urpClassNum=" + this.urpClassNum + ", academyName=" + this.academyName + ", subjectName=" + this.subjectName + ", className=" + this.className + ")";
        }
    }
}
