package com.hackerda.platform.domain.community;

import com.hackerda.platform.domain.user.Gender;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 身份
 */
public enum IdentityCategory {

    /**
     * 社区
     */
    Community(0){
        @Override
        public String getShowName(Poster poster) {
            return poster.getNickName();
        }

        @Override
        public String getAvatarUrl(Poster poster) {
            return poster.getAvatarUrl();
        }
    },

    /**
     * 年级
     */
    Grade(1) {
        @Override
        public String getShowName(Poster poster) {
            if(poster instanceof StudentPoster) {
                StudentPoster studentPoster = (StudentPoster) poster;
                String grade = studentPoster.getGrade();
                return grade + "级同学";
            }

            throw new UnsupportedOperationException("非学生用户无法获取年级");
        }

        @Override
        public String getAvatarUrl(Poster poster) {
            if(poster.getGender() == Gender.Man) {
               return  male_avatar;
            }
            if(poster.getGender() == Gender.Woman) {
                return  female_avatar;
            }
            return anon;
        }
    },

    /**
     * 学院
     */
    College(2) {
        @Override
        public String getShowName(Poster poster) {
            if(poster instanceof StudentPoster) {
                StudentPoster studentPoster = (StudentPoster) poster;
                String grade = studentPoster.getCollage();
                return grade + "同学";
            }

            throw new UnsupportedOperationException("非学生用户无法获取年级");
        }

        @Override
        public String getAvatarUrl(Poster poster) {
            if(poster.getGender() == Gender.Man) {
                return  male_avatar;
            }
            if(poster.getGender() == Gender.Woman) {
                return  female_avatar;
            }
            return anon;
        }
    },

    /**
     * 匿名
     */
    Anonymous(3) {
        @Override
        public String getShowName(Poster poster) {
            return "匿名";
        }

        @Override
        public String getAvatarUrl(Poster poster) {
            if(poster.getGender() == Gender.Man) {
                return  male_avatar;
            }
            if(poster.getGender() == Gender.Woman) {
                return  female_avatar;
            }
            return anon;
        }
    },

    /**
     * 实名
     */
    RealName(4) {
        @Override
        public String getShowName(Poster poster) {
            if(poster instanceof StudentPoster) {
                StudentPoster studentPoster = (StudentPoster) poster;
                String grade = studentPoster.getRealName();
                return grade + "同学";
            }

            throw new UnsupportedOperationException("非学生用户无法获取年级");
        }

        @Override
        public String getAvatarUrl(Poster poster) {
            if(poster.getGender() == Gender.Man) {
                return  male_avatar;
            }
            if(poster.getGender() == Gender.Woman) {
                return  female_avatar;
            }
            return anon;
        }
    };

    private final int code;

    private static Map<Integer, IdentityCategory> codeMap;

    private static final String female_avatar = "https%3A%2F%2F6861-hackerda-rfukj-1302818883.tcb.qcloud" +
            ".la%2Favatar%2Fundraw_female_avatar_w3jk.png";

    private static final String male_avatar = "https%3A%2F%2F6861-hackerda-rfukj-1302818883.tcb.qcloud.la%2Favatar%2Fundraw_male_avatar_323b.png";

    private static final String anon = "https%3A%2F%2F6861-hackerda-rfukj-1302818883.tcb.qcloud" +
            ".la%2Favatar%2Fundraw_hacker_mind_6y85.png";

    IdentityCategory(int code) {
        this.code = code;
    }

    public abstract String getShowName(Poster poster);

    public abstract String getAvatarUrl(Poster poster);

    public int getCode(){
        return code;
    }

    public static IdentityCategory getByCode(int code) {
        if(codeMap == null) {
            codeMap = Arrays.stream(IdentityCategory.values())
                    .collect(Collectors.toMap(IdentityCategory::getCode, x -> x));
        }

        return codeMap.get(code);
    }
}
