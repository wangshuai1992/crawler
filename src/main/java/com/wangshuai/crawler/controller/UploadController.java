package com.wangshuai.crawler.controller;

import com.alibaba.fastjson.JSONObject;
import com.wangshuai.crawler.oss.OssFileUploadManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xin.allonsy.common.vo.ObjectJson;

import javax.annotation.Resource;

/**
 * UploadController
 *
 * @author wangshuai
 * @version V1.0
 * @since 2017-08-27 13:24
 */
@Controller
@Slf4j
public class UploadController {

    @Resource
    private OssFileUploadManager ossFileUploadManager;

    @RequestMapping("/getUpToken.json")
    @ResponseBody
    public ObjectJson<JSONObject> getUpToken() {
        ObjectJson<JSONObject> objectJson = new ObjectJson<>();
        objectJson.setData(ossFileUploadManager.createUploadToken());
        return objectJson;
    }

}
