package com.hackerda.platform.domain.community;

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
            return null;
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
            return null;
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
            return null;
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
            return null;
        }
    };

    private final int code;

    private static Map<Integer, IdentityCategory> codeMap;

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
