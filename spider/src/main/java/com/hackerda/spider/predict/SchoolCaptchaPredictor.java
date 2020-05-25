package com.hackerda.spider.predict;


import com.hackerda.spider.captcha.CaptchaImage;
import com.hackerda.spider.captcha.CaptchaProvider;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;


/**
 * 学校验证码预测
 *
 * @author JR Chan
 */
public class SchoolCaptchaPredictor implements CaptchaPredict {

    private static final String DEFAULT_PREDICT_URL = "http://spider.hackerda.com/valid";
    private final String predictUrl;

    private final RestOperations restOperations;

    public SchoolCaptchaPredictor(RestOperations restOperations) {
        this(restOperations, DEFAULT_PREDICT_URL);
    }

    public SchoolCaptchaPredictor(RestOperations restOperations, String predictURL) {
        this.restOperations = restOperations;
        this.predictUrl = predictURL;
    }


    @Override
    public String predict(CaptchaImage captchaImage) {

        String base64 = captchaImage.encodeBase64();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("image", base64);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Result> entity = restOperations.postForEntity(predictUrl, request, Result.class);

        if (entity.getStatusCode().is2xxSuccessful()) {
            Result body = entity.getBody();

            if (body != null) {
                if (body.status == 200) {
                    return body.getData();
                }
                throw new PredictException(entity.getStatusCodeValue(), body.msg);
            }

            throw new PredictException(entity.getStatusCodeValue(), "body is empty");
        }

        throw new PredictException(entity.getStatusCodeValue(), entity.toString());
    }


    @Data
    private static final class Result {
        private int status;
        private String msg;
        private String data;
    }

}
