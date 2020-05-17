package com.hackerda.platform.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Slf4j
public class DESUtil {

    /**
     * 获取加密后的密文密码
     * @param account 学号
     * @param password 密码
     * @return 返回加密后的密文密码
     * @throws Exception
     */
    public static String encrypt(String text, String salt) {
        if(StringUtils.isEmpty(text) || StringUtils.isEmpty(salt)){
            throw new RuntimeException("DES加密时用户名或密码为空");
        }
        else{
            try {
                SecretKey secretKey = generateKey(salt);
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "DES");
                Cipher cipher = Cipher.getInstance("DES");
                byte[] byteContent = text.getBytes(StandardCharsets.UTF_8);
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] result = Base64.getEncoder().encode(cipher.doFinal(byteContent));
                return new String(result);
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 获取解密后的明文密码
     * @param text 学号
     * @param salt 加密后的密码文件（一般从数据库读取）
     * @return 返回解密后的明文密码
     * @throws Exception
     */
    public static String decrypt(String text, String salt) {
        if(StringUtils.isEmpty(text) || StringUtils.isEmpty(salt)) {
            throw new RuntimeException("DES解密时用户名或密码为空");
        }
        else{
            try {
                byte[] contentByte = Base64.getDecoder().decode(text);
                SecretKey secretKey = generateKey(salt);
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "DES");
                Cipher cipher = Cipher.getInstance("DES");
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] result = cipher.doFinal(contentByte);
                return new String(result);
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 传入salt生成SecretKey
     * @param salt 学号+公共salt拼接后的salt
     * @return
     */
    private static SecretKey generateKey(String salt){
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            DESKeySpec keySpec = new DESKeySpec(salt.getBytes(StandardCharsets.UTF_8));
            return keyFactory.generateSecret(keySpec);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {

        String encrypt = encrypt("1", "2014025838");
        String decrypt = decrypt(encrypt, "2014025838");
        System.out.println(decrypt);
    }


}
