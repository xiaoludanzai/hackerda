package com.hackerda.platform.domain.user;

import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * 黑科校际用户
 *
 */
@Data
public class AppUserBO {

    /** 会员用户名 **/
    private String userName;
    /** 呢称 **/
    private String nickname;
    /** 密码 密码结构: sha256(sha256(密码)+[盐值])  **/
    private String password;
    /** 盐值 **/
    private final String salt;

    /** 头像路径 不写入数据库**/
    private String avatarPath;

    /** 邮箱地址 **/
    private String email;
    /** 绑定手机 **/
    private PhoneNumber phoneNumber;

    /** 用户性别  0：未知、1：男、2：女 **/
    private Gender gender;

    /** 个性签名 **/
    private String introduction;

    private UserType userType;

    private LifeCycleStatus lifeCycleStatus;

    private boolean useDefaultPassword;

    private List<RoleBO> roleList = new ArrayList<>(0);

    public AppUserBO(String userName, String nickname, String password, String avatarPath, PhoneNumber phoneNumber, Gender gender
            , String introduction) {
        this.salt = UUID.randomUUID().toString().replaceAll("-","");

        this.userName = userName;
        this.nickname = nickname;
        this.password = DigestUtils.sha256Hex(password.trim()+ salt);

        this.avatarPath = avatarPath;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.introduction = introduction;
        this.lifeCycleStatus = LifeCycleStatus.Creating;
    }

    public AppUserBO(String userName, String nickname, String password, String salt, String avatarPath, PhoneNumber phoneNumber,
                     Gender gender
            , String introduction, boolean useDefaultPassword) {
        this.salt = salt;

        this.userName = userName;
        this.nickname = nickname;
        this.password = DigestUtils.sha256Hex(password.trim()+ salt);

        this.avatarPath = avatarPath;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.introduction = introduction;
        this.useDefaultPassword = useDefaultPassword;
    }

    public void grantRole(RoleBO roleBO) {
        roleList.add(roleBO);
    }

    public void grantRole(List<RoleBO> roleBOList) {
        roleList.addAll(roleBOList);
    }

}
