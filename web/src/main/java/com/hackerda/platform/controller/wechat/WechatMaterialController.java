package com.hackerda.platform.controller.wechat;


import com.hackerda.platform.controller.WebResponse;
import com.hackerda.platform.controller.vo.WechatArticleVO;
import com.hackerda.platform.service.WechatMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wechat/material")
public class WechatMaterialController {

    @Autowired
    private WechatMaterialService wechatMaterialService;

    @RequestMapping("/article")
    public WebResponse<WechatArticleVO> getArticleList() {

        WechatArticleVO article = wechatMaterialService.getArticle();

        return WebResponse.success(article);
    }
}
