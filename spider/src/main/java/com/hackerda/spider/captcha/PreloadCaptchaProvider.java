package com.hackerda.spider.captcha;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 此类实现可以预加需要的验证码
 * 以此来减少验证码获取时间过长的问题
 *
 * @author JR Chan
 */
public class PreloadCaptchaProvider extends CaptchaProvider {
    private final Logger logger = LoggerFactory.getLogger(PreloadCaptchaProvider.class);
    private final BlockingQueue<CaptchaImage> queue;
    private final ExecutorService executorService;
    /**
     * 协助预加载验证码的线程数
     */
    private final static int DEFAULT_PRODUCER_COUNT = 2;
    /**
     * 预加载验证码的数量
     */
    private final static int DEFAULT_PRELOAD_SIZE = 10;
    private final int producerCount;
    private final AtomicInteger preloadCount;
    Timer lbTimer = new Timer("Expire-captcha-cleaner",
            true);

    public PreloadCaptchaProvider(String captchaUrl) {
        this(new RestTemplate(), captchaUrl, null, null, DEFAULT_PRODUCER_COUNT ,DEFAULT_PRELOAD_SIZE);
    }

    public PreloadCaptchaProvider(String captchaUrl, int preloadSize) {
        this(new RestTemplate(), captchaUrl, null, null, DEFAULT_PRODUCER_COUNT ,preloadSize);
    }

    public PreloadCaptchaProvider(RestOperations restOperations, String captchaUrl) {
        this(restOperations, captchaUrl, null, null, DEFAULT_PRELOAD_SIZE, DEFAULT_PRELOAD_SIZE);
    }


    public PreloadCaptchaProvider(RestOperations restOperations, String captchaUrl, BlockingQueue<CaptchaImage> queue
            , ExecutorService executorService, int producerCount, int preloadSize) {
        super(restOperations, captchaUrl);

        this.queue =
                queue == null ? new LinkedBlockingQueue<>(preloadSize) : queue;

        this.executorService = executorService == null?
                new ThreadPoolExecutor(producerCount, producerCount, 0L, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(producerCount)): executorService;

        this.producerCount = producerCount;
        this.preloadCount = new AtomicInteger(preloadSize);
        startPreLoad();
        startCleaner();
    }

    /**
     * 开启对应数量线程生产验证码
     */
    private void startPreLoad() {
        for (int x = 0; x < this.producerCount; x++) {
            executorService.submit(() -> {
                while (!Thread.interrupted()) {
                    try {
                        CaptchaImage task = this.task();
                        if (task != null && !task.getCookie().isEmpty()) {
                            queue.offer(task);
                            preloadCount.getAndIncrement();
                        }
                    } catch (Throwable throwable) {
                        logger.debug("preload captcha error", throwable);
                    }
                }
            });
        }

    }

    /**
     * 清理过期的验证码， 20执行分钟一次
     */
    private void startCleaner() {
        lbTimer.schedule(new CleanTask(), 0, 20 * 1000 * 60);
    }


    @Override
    public CaptchaImage get() {
        return getFromPreLoad();
    }

    @Override
    public CaptchaImage get(long timeout, TimeUnit timeUnit) {
        return getFromPreLoad(timeout, timeUnit);
    }


    @SneakyThrows
    protected CaptchaImage getFromPreLoad() {
        return queue.take();
    }

    @Nullable
    public CaptchaImage getFromPreLoad(long timeout, TimeUnit timeUnit) {
        try {
            return queue.poll(timeout, timeUnit);
        } catch (InterruptedException e) {
            return null;
        }
    }

    private class CleanTask extends TimerTask {

        @Override
        public void run() {
            queue.removeIf(x -> isExpire(x.getCreateDate()));
        }

        boolean isExpire(Date createDate) {
            return System.currentTimeMillis() - createDate.getTime() > 1000 * 60 * 20;
        }

    }

    int getPreloadCaptchaSize(){
        return queue.size();
    }
}
