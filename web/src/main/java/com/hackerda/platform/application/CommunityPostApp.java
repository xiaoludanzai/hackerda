package com.hackerda.platform.application;

import com.hackerda.platform.MDCThreadPool;
import com.hackerda.platform.domain.community.*;
import com.hackerda.platform.infrastructure.database.model.ImageInfoDO;
import com.hackerda.platform.infrastructure.database.model.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommunityPostApp {

    private ContentSecurityCheckService contentSecurityCheckService;
    @Autowired
    private PosterRepository posterRepository;
    @Autowired
    private RecommendPostRecorder recommendPostRecorder;

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

    public boolean deletePost(PostBO postBO, String userName) {
        if(postBO.canDeleteByUser(userName)) {
            postBO.delete();
            posterRepository.update(postBO);
            return true;
        } else {
            log.warn("user {} no access to delete post {}", userName, postBO);
            return false;
        }
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

    public List<PostDetailBO> getRecommendPost() {
        List<Long> idList = recommendPostRecorder.getPostIdList(new Date());
        return posterRepository.findByIdList(idList)
                .stream().filter(PostBO::isDelete)
                .sorted(Comparator.comparing(PostBO::getId).reversed()).collect(Collectors.toList());
    }

    @Autowired
    public void setContentSecurityCheckService(ContentSecurityCheckService contentSecurityCheckService) {
        this.contentSecurityCheckService = contentSecurityCheckService;
    }

}
