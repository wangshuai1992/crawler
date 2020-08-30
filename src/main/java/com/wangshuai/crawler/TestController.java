package com.wangshuai.crawler;

import com.wangshuai.crawler.manager.hacpai.HacpaiManager;
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

    @RequestMapping("/HJU432MTG/test")
    public Object test() {
        return ossFileUploader.uploadFileFromWebUrl("http://tech.yuceyi.com/upload/c6e4c324f51946b88003df8e8110f9c2_image.png");
    }

    @RequestMapping("/HJU432MTG/test1")
    public Object test1() {
        hacpaiManager.resolveListPage();
        return "success";
    }

    @RequestMapping("/HJU432MTG/test2")
    public Object test2() {
        hacpaiManager.updateAllArticleContent();
        return "success";
    }

}
