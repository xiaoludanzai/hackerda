package com.hackerda.spider.captcha;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.Files;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * @author junrong.chen
 * 验证码
 */
@Data
@Slf4j
public class CaptchaImage {
    private final byte[] data;
    private final List<HttpCookie> cookie;
    private final Date createDate;

    public CaptchaImage(final byte[] data, List<HttpCookie> httpCookie) {
        this.data = data.clone();
        this.cookie = httpCookie;
        this.createDate = new Date();
    }

    public byte[] getData() {
        return data;
    }

    public List<HttpCookie> getCookie() {
        return this.cookie;
    }

    public Date getCreateDate() {
        return createDate;
    }

    @VisibleForTesting
    public void write(String path) {
        path = StringUtils.isEmpty(path) ? "pic.jpg" : path;
        try {
            Files.write(data, new File(path));
        } catch (IOException e) {
            log.error("write verify code error", e);
        }
    }

    /**
     * 对图片进行进行编码
     *
     * @return 编码后的字符串
     */
    public String encodeBase64() {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(getData());
    }

    public boolean isEmpty() {
        return data.length == 0;
    }
}
