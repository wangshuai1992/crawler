package com.wangshuai.crawler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 *
 * @author wangshuai
 * @date 2019-07-12 15:49
 */
@RestController
@Slf4j
public class TestController {

    @RequestMapping("/HJU432MTG/test")
    public Object test() {
        return null;
    }

}
