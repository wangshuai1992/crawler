package com.wangshuai.crawler.home.controller;

import com.wangshuai.crawler.manager.novel.NovelDownloader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * MainController
 *
 * @author wangshuai
 * @version V1.0
 * @since 2017-08-27 13:24
 */
@Controller
public class MainController {

    @Resource
    private NovelDownloader novelDownloader;

    @RequestMapping("/")
    public String index(Model modelMap) {
        return "main";
    }

    @RequestMapping("/downloadNovel")
    @ResponseBody
    public String downloadNovel(Model modelMap) throws Exception {
        novelDownloader.downloadNovel();
        return "done";
    }
}
