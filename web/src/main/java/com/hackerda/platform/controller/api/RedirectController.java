package com.hackerda.platform.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller("/redirect")
public class RedirectController {

    @RequestMapping("axinfu")
    public void axinfu(HttpServletResponse resp) throws Exception {
        resp.sendRedirect("https://h5.axinfu.com/login/inputMobile");
    }

    @RequestMapping("repair")
    public void repair(HttpServletResponse resp) throws Exception {
        resp.sendRedirect("https://koudaigou.net/web/formview/59e0c16ebb7c7c67aea6713d");
    }
}
