package com.hackerda.platform.application;

import com.hackerda.platform.MDCThreadPool;
import com.hackerda.platform.domain.community.*;
import com.hackerda.platform.infrastructure.database.model.ImageInfoDO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CommunityPostApp {

    private ContentSecurityCheckService contentSecurityCheckService;
    @Autowired
    private PosterRepository posterRepository;

    private final ExecutorService imageSecCheckPool = new MDCThreadPool(8, 8,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), r -> new Thread(r, "imageSecCheck"));

    private final RestTemplate template = new RestTemplate();

    public void createPost(PostBO postBO) {

        if(!contentSecurityCheckService.isSecurityContent(postBO.getContent())) {
            postBO.setStatus(RecordStatus.UnderReview);
        }else {
            postBO.setStatus(RecordStatus.Release);
        }

        if(postBO.hasImage()) {
            CountDownLatch latch = new CountDownLatch(postBO.getImageInfoList().size());
            for (ImageInfo image : postBO.getImageInfoList()) {
                imageSecCheckPool.submit(() -> {
                    try {
                        boolean isSec = imageSecCheck(image);
                        image.setRecordStatus(isSec ? RecordStatus.Release :  RecordStatus.UnderReview);
                    }finally {
                        latch.countDown();
                    }
                });
            }
            try {
                latch.await(3000L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                log.error("latch await error", e);
            }
        }
        posterRepository.save(postBO);
    }

    private boolean imageSecCheck(ImageInfo imageInfo) {
        try {
            String url = imageInfo.getPath();

            String decode = URLDecoder.decode(url, StandardCharsets.UTF_8.name());

            ResponseEntity<byte[]> entity = template.getForEntity(decode, byte[].class);

            return contentSecurityCheckService.isSecurityImage(entity.getBody());
        }catch (Throwable throwable) {
            log.error("check imageSec error", throwable);
            return true;
        }


    }

    @Autowired
    public void setContentSecurityCheckService(ContentSecurityCheckService contentSecurityCheckService) {
        this.contentSecurityCheckService = contentSecurityCheckService;
    }

}
