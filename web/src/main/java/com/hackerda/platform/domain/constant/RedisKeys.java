package com.hackerda.platform.domain.constant;

/**
 * @author junrong.chen
 */

public enum RedisKeys {
    /**
     * redis key 实例
     */

    EMPTY_ROOM_KEY("app_search_service:emptyRoom:"),

    OPENID_TO_ACCOUNT("mark_openid:"),

    URP_COOKIE("urp_cookie"),

    URP_LOGIN_COOKIE("urp_login_cookie"),

    CAPTCHA("kaptcha"),
    /**
     * 数据库班级信息id与教务网班级id的映射
     */
    URP_CLASS_CODE("classId_urpClassCode"),
    /**
     * 班级课时排行榜
     */
    CLASS_COURSE_HOUR_RANK("class_courseHour_rank"),
    /**
     * 班级课时排行详情
     */
    CLASS_COURSE_HOUR_DETAIL("class_courseHour_detail"),
    /**
     * 班级课时排行详情
     */
    MINI_PROGRAM_ACCESS_TOKEN("miniProgram_AccessToken"),
    /**
     * 待评估的学号集合
     */
    WAITING_EVALUATION_SET("waiting_evaluation_set"),
    /**
     * 已经完成的账号
     */
    FINISH_EVALUATION_SET("teaching_evaluation"),
    /**
     * 已经完成的账号
     */
    PROXY_SELECT_SWITCH("PROXY_DIRECT_SWITCH"),

    /**
     * 当前学期完成抓取学号
     */
    CURRENT_GRAD_FINISH_ACCOUNT("CURRENT_GRAD_FINISH_ACCOUNT"),

    /**
     * 过往学期完成抓取学号
     */
    EVER_GRAD_FINISH_ACCOUNT("EVER_GRAD_FINISH_ACCOUNT"),

    /**
     * GPA 排名
     */
    GPA_RANK("GPA_RANK_ZSET"),

    /**
     * 圈子评论数缓存
     */
    COMMENT_COUNT("COMMENT_COUNT"),

    /**
     * 评论情况
     */
    LIKE_STATUS("LIKE_STATUS"),

    /**
     * 被屏蔽的身份选择
     */
    Identity_Category_Filter("Identity_Category_Filter"),

    POST_UV("post_uv"),

    POST_PV("post_pv"),

    USER_POST_UV("user_post_uv"),

    USER_POST_PV("user_post_pv")

    ;
    private final String name;

    RedisKeys(String name){
        this.name=name;
    }

    public String genKey(String... key){
        return name + "_" + String.join("_", key);
    }

    public String getName(){
        return this.name;
    }


}
