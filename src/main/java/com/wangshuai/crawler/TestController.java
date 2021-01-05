package com.wangshuai.crawler;

import com.wangshuai.crawler.manager.hacpai.HacpaiManager;
import com.wangshuai.crawler.manager.stock.hk.jisilu.JisiluManager;
import com.wangshuai.crawler.oss.OssFileUploader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TestController
 *
 * @author wangshuai
 * @date 2019-07-12 15:49
 */
@RestController
@Slf4j
public class TestController {

    @Resource
    private OssFileUploader ossFileUploader;

    @Resource
    private HacpaiManager hacpaiManager;

    @Resource
    private JisiluManager jisiluManager;

    @RequestMapping("/HJU432MTG/test")
    public Object test() {
        jisiluManager.login();
        return "success";
    }

    @RequestMapping("/HJU432MTG/test1")
    public Object test1() {
        jisiluManager.updateData();
        return "success";
    }

    @RequestMapping("/HJU432MTG/test2")
    public Object test2() {
        hacpaiManager.updateAllArticleContent();
        return "success";
    }

}
