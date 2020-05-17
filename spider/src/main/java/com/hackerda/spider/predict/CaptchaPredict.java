package com.hackerda.spider.predict;


import com.hackerda.spider.captcha.CaptchaImage;

/**
 * 接口指定了图片验证码预测的操作
 *
 * @author JR Chan
 */
public interface CaptchaPredict {


    /**
     * 通过传入的图片预测对应结果
     *
     * @param captchaImage 验证码
     * @return 预测结果
     */
    String predict(CaptchaImage captchaImage);

}
