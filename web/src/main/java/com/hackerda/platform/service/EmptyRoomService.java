package com.hackerda.platform.service;

import com.hackerda.platform.dao.EmptyRoomDao;
import com.hackerda.platform.pojo.EmptyRoom;
import com.hackerda.platform.pojo.vo.EmptyRoomVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Xie
 * @date 2019/9/18
 */
@Slf4j
@Service("emptyRoomService")
@CacheConfig(cacheNames = "empty_Room_data")
public class EmptyRoomService {

    @Autowired
    private EmptyRoomDao emptyRoomDao;
    @Autowired
    RedisTemplate redisTemplate;
    @Resource
    private RoomService roomService;


    /**
     * 对数据进行楼层筛选,如果楼层为0，则不进行筛选
     *
     * @param week      星期数
     * @param teaNum    教学楼编号
     * @param dayOfWeek 星期天数
     * @param order     节次
     * @param floor     楼层
     * @return 返回经过筛选楼层后的空教室数据的list
     */
    public List<EmptyRoom> getEmptyRoomReply(String week, String teaNum, int dayOfWeek, int order, int floor) {
        // 注解@Cacheable是使用AOP代理实现的 ，通过创建内部类来代理缓存方法
        // 类内部的方法调用类内部的缓存方法不会走代理，使得cacheable注解失效，
        // 所以就不能正常创建缓存，因此需要一个代理对象来调用
        String wSection = dayOfWeek + "/" + order;
        List<String> emptyRoomList = emptyRoomDao.getEmptyRoomReply(week, teaNum, wSection);
        String key = "empty_Room_data::" + week + teaNum + wSection;
        //对数据缓存24小时，重复查询会更新这个数据的过期时间
        redisTemplate.expire(key, 24L, TimeUnit.HOURS);
        List<EmptyRoom> result = new ArrayList<>();
        for (String s : Sets.newHashSet(emptyRoomList)) {
            if (checkFloor(s, floor, teaNum)) {
                EmptyRoom room = new EmptyRoom(s);
                room.addOrder(order);
                result.add(room);
            }
        }
        return result;
    }


    /**
     * 提供不分节次的全量查询
     *
     * @param week
     * @param teaNum
     * @param dayOfWeek
     * @param floor
     * @return
     */
    public List<EmptyRoomVo> getFullEmptyRoomReply(String week, String teaNum, int dayOfWeek, int floor) {
        Map<String, EmptyRoom> classRoomMap = new HashMap<>();
        List<Integer> orderList = Lists.newArrayList(1, 3, 5, 7, 9);

        for (int order : orderList) {
            List<EmptyRoom> roomList = getEmptyRoomReply(week, teaNum, dayOfWeek, order, floor);
            for (EmptyRoom room : roomList) {
                if (classRoomMap.containsKey(room.getName())) {
                    classRoomMap.get(room.getName()).addOrder(order);
                } else {
                    classRoomMap.put(room.getName(), room);
                }
            }
        }

        return classRoomMap.values().stream()
                .map(emptyRoom -> new EmptyRoomVo(roomService.getClassRoomByName(emptyRoom.getName()),
                        emptyRoom.getOrderList()))
                .filter(emptyRoomVo -> emptyRoomVo.getUrpClassroom() != null)
                .sorted(Comparator.comparing(o -> o.getUrpClassroom().getNumber()))
                .collect(Collectors.toList());
    }


    /**
     * 判断楼层
     */
    private boolean checkFloor(String className, int floor, String teaNum) {

        if (className.startsWith("科技大厦10楼")) {
            return false;
        }
        if (floor == 0) {
            return true;
        }
        int floorTemp;
        char[] chars = className.replaceAll("\\D", "").toCharArray();

        if (!"02".equals(teaNum)) {
            if (chars.length != 4) {
                return false;
            }
            floorTemp = (chars[0] - '0') * 10 + (chars[1] - '0');
        } else {
            floorTemp = (chars[0] - '0');
        }

        if (floorTemp == floor) {
            return true;
        } else {
            return false;
        }
    }
}

