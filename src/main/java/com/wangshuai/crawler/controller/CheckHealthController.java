package com.wangshuai.crawler.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 健康检查
 */
@Slf4j
@Controller
public class CheckHealthController {

    @RequestMapping("/check-health")
    @ResponseBody
    public String checkHealth() throws Exception {
        return "success";
    }
}
