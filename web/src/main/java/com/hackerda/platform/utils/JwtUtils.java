package com.hackerda.platform.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author JR Chan
 */
@Slf4j
public class JwtUtils {

    public static final String USERNAME_KEY = "userName";
    /**
     * 生成签名
     * @param username 用户名
     * @param secret 用户的密码
     * @return 加密的token
     */
    public static String signForUserDetail(String username, String[] role, String[] permission, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim(USERNAME_KEY, username)
                .withArrayClaim("role", role)
                .withArrayClaim("permission", permission)
                .sign(algorithm);

    }

    public static String signForWechatStudent(String username, String appId,  String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim(USERNAME_KEY, username)
                .withClaim("appId", appId)
                .sign(algorithm);
    }


    /**
     * 校验token是否正确
     * @param token 密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(USERNAME_KEY, username)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getClaim(String token, String key) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(key).asString();
        } catch (JWTDecodeException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T[] getArrayClaim(String token, String key, Class<T> tClass) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(key).asArray(tClass);
        } catch (JWTDecodeException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String s = signForUserDetail("123", new String[]{"admin"}, new String[]{"test"}, "123");
        String[] roles = getArrayClaim(s, "role", String.class);
        System.out.println(Arrays.toString(roles));
    }

}
