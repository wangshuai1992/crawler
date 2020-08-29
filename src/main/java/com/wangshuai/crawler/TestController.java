package com.wangshuai.crawler;

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

    @RequestMapping("/HJU432MTG/test")
    public Object test() {
        return ossFileUploader.uploadFileFromWebUrl("http://tech.yuceyi.com/upload/c6e4c324f51946b88003df8e8110f9c2_image.png");
    }

}
