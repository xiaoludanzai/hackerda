package com.hackerda.platform.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.hackerda.platform.dao.UrpClassDao;
import com.hackerda.platform.pojo.UrpClass;
import com.hackerda.platform.spider.newmodel.searchclass.SearchClassInfoPost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author JR Chan
 * @date 2018/12/15
 */
@Slf4j
@Service("clazzService")
public class ClassService {

    @Resource
    private UrpClassDao urpClassDao;
    @Resource
    private UrpSearchService urpSearchService;


    private static final Cache<String, UrpClass> classCache = CacheBuilder.newBuilder()
            .maximumSize(200)
            .build();


    public UrpClass getClassByName(String className, String account){

        try{
            return classCache.get(className, ()-> {
                UrpClass urpClass = urpClassDao.selectByName(className);

                if (urpClass != null){
                    return urpClass;
                }

                SearchClassInfoPost post = new SearchClassInfoPost();
                String start = account.substring(0, 4);
                int end = Integer.parseInt(start) + 1;
                post.setYearNum(start);
                post.setExecutiveEducationPlanNum(start + "-"+ end + "-1-1");
                List<UrpClass> results = urpSearchService.searchUrpClass(post);
                Map<String, UrpClass> collect = results.stream()
                        .collect(Collectors.toMap(UrpClass::getClassName, x -> x));

                classCache.putAll(collect);
                urpClassDao.insertBatch(new ArrayList<>(collect.values()));


                return collect.get(className);
            });
        }catch (Exception e){
            log.error("get className {} exception", className, e);
            throw new RuntimeException(e);
        }

    }











}
