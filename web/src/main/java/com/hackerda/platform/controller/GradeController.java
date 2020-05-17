package com.hackerda.platform.controller;

import com.hackerda.platform.pojo.WebResponse;
import com.hackerda.platform.pojo.vo.GradeResultVo;
import com.hackerda.platform.pojo.vo.GradeVo;
import com.hackerda.platform.service.NewGradeSearchService;
import com.hackerda.platform.spider.newmodel.grade.detail.GradeDetailSearchPost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author junrong.chen
 */
@Slf4j
@RestController
public class GradeController {
    @Resource
    private NewGradeSearchService newGradeSearchService;


    @RequestMapping(value = "/nowGradeV2", method = RequestMethod.POST)
    public WebResponse getNowGradeV2(@RequestParam("account") String account,
                                     @RequestParam("password") String password) {
        List<GradeVo> result = newGradeSearchService.getCurrentTermGrade(account, password);
        return WebResponse.success(result);
    }

    @RequestMapping(value = "/grade", method = RequestMethod.POST)
    public WebResponse getGrade(@RequestParam("account") String account,
                                     @RequestParam("password") String password) {
        GradeResultVo result = newGradeSearchService.getGrade(account, password);
        return WebResponse.success(result);
    }

}
