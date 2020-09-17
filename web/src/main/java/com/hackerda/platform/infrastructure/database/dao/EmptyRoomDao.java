package com.hackerda.platform.infrastructure.database.dao;

import com.hackerda.spider.UrpSearchSpider;
import com.hackerda.spider.support.search.classroom.SearchResultWrapper;
import com.hackerda.spider.support.search.emptyroom.EmptyRoomRecord;
import com.hackerda.spider.support.search.emptyroom.SearchEmptyRoomPost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author JR Chan
 */
@Service
@CacheConfig(cacheNames = "empty_Room_data")
@Slf4j
public class EmptyRoomDao {

    @Autowired
    private UrpSearchSpider urpSearchSpider;

    /**
     * 从缓存中获取空教室信息，若redis中没有相关缓存，则爬取
     *
     * @param week     星期数
     * @param teaNum   教学楼编号
     * @param wSection 星期天数/节次
     * @return 返回包含空教室数据的list
     */
    @Cacheable(key = "#p0+#p1+#p2", unless = "#result == null")
    public List<String> getEmptyRoomReply(String week, String teaNum, String wSection) {
        SearchEmptyRoomPost emptyRoomPost = new SearchEmptyRoomPost(week, teaNum, wSection, "1", "50");
        List<SearchResultWrapper<EmptyRoomRecord>> searchEmptyRoom = urpSearchSpider.searchEmptyRoom(emptyRoomPost);
//        log.info("爬取空教室缓存{} {} {}", week, teaNum, wSection);
//        NewUrpSpider spider = new NewUrpSpider("2016024254", "1");
//
//        EmptyRoomPost emptyRoomPost = new EmptyRoomPost(week, teaNum, wSection, "1", "50");
//        EmptyRoomPojo emptyRoomPojo = spider.getEmptyRoom(emptyRoomPost);
//        List<String> records = new ArrayList<>();
//        for (EmptyRoomRecord emptyRoomRecord : emptyRoomPojo.getRecords()) {
//            records.add(emptyRoomRecord.getClassroomName());
//        }
//
//        //times是还需爬取数据的次数，教务网只能一页显示50个数据，需要循环爬取直到爬完
//        int times = emptyRoomPojo.getPageContext().getTotalCount() / 50;
//        //获取剩余的数据
//        for (int i = 2; i <= times + 1; i++) {
//            emptyRoomPost = new EmptyRoomPost(week, teaNum, wSection, String.valueOf(times), "50");
//            emptyRoomPojo = spider.getEmptyRoom(emptyRoomPost);
//            for (EmptyRoomRecord emptyRoomRecord : emptyRoomPojo.getRecords()) {
//                records.add(emptyRoomRecord.getClassroomName());
//            }
//        }

        return Collections.emptyList();
    }
}
