package com.wangshuai.crawler.oss;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.UUID;

/**
 * 七牛云存储
 * 文档: http://developer.qiniu.com/code/v7/sdk/java.html
 *
 * @author wangshuai
 */
@Component
@Slf4j
public class OssFileUploadManager {

    @Value("${oss.qiniu.httpbase}")
    private String httpBaseUrl;

    @Value("${oss.qiniu.accesskey}")
    private String accesskey;

    @Value("${oss.qiniu.secretkey}")
    private String secretkey;

    @Value("${oss.qiniu.bucket}")
    private String bucket;

//    @Value("${oss.qiniu.callbackurl}")
//    private String callbackUrl;

    @Resource
    private RestTemplate restTemplate;

    /**
     * 上传字节数组 使用uuid作为文件名
     *
     * @param data   字节数组
     * @param format 文件扩展名
     * @return 云存储HTTP地址
     */
    public String upload(byte[] data, String format) {
        String uuid = UUID.randomUUID().toString();
        String target = uuid + "." + format;
        String httpBase = getFormattedBaseUrl();
        this.uploadToOss(data, target);
        return httpBase + target;
    }

    /**
     * 上传字节数组
     *
     * @param data     字节数组
     * @param fileName 文件名
     * @return 云存储HTTP地址
     */
    public String uploadWithFileName(byte[] data, String fileName) {
        this.uploadToOss(data, fileName);
        return getFormattedBaseUrl() + fileName;
    }

    /**
     * 上传字节流 使用uuid作为文件名
     *
     * @param inputStream 字节流
     * @param format      文件扩展名
     * @return 云存储HTTP地址
     */
    public String upload(InputStream inputStream, String format) {
        String uuid = UUID.randomUUID().toString();
        String target = uuid + "." + format;
        String httpBase = getFormattedBaseUrl();
        this.uploadToOss(inputStream, target);
        return httpBase + target;
    }

    /**
     * 上传字节流
     *
     * @param inputStream 字节流
     * @param fileName    文件名
     * @return 云存储HTTP地址
     */
    public String uploadWithFileName(InputStream inputStream, String fileName) {
        this.uploadToOss(inputStream, fileName);
        return getFormattedBaseUrl() + fileName;
    }

    /**
     * 上传本地文件 使用uuid作为文件名
     *
     * @param file   待上传的本地文件
     * @param format 文件扩展名
     * @return 云存储HTTP地址
     */
    public String upload(File file, String format) {
        String uuid = UUID.randomUUID().toString();
        String target = uuid + "." + format;
        String httpBase = getFormattedBaseUrl();
        this.uploadToOss(file, target);
        return httpBase + target;
    }

    /**
     * 上传本地文件
     *
     * @param file     待上传的本地文件
     * @param fileName 文件名
     * @return 云存储HTTP地址
     */
    public String uploadWithFileName(File file, String fileName) {
        this.uploadToOss(file, fileName);
        return getFormattedBaseUrl() + fileName;
    }

    /**
     * 根据网络地址上传文件
     * http://xxx.com/xxx.png
     *
     * @param url
     * @return
     */
    public String uploadFileFromWebUrl(String url) {
        try {
            String[] arr = url.split("/");
            String fileName = arr[arr.length - 1];
            URI uri = new URI(url);
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, byte[].class);
            this.uploadWithFileName(responseEntity.getBody(), fileName);
            return getFormattedBaseUrl() + fileName;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject createUploadToken() {
        Auth auth = Auth.create(accesskey, secretkey);
        StringMap putPolicy = new StringMap();
//        putPolicy.put("callbackUrl", callbackUrl);
        // 预定义变量 https://developer.qiniu.com/kodo/1235/vars#magicvar
//        putPolicy.put("callbackBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        // 可以有自定义的额外参数  https://developer.qiniu.com/kodo/1235/vars#xvar
//        putPolicy.put("callbackBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize),\"user\":\"$(x:user)\",\"age\",$(x:age)}");
//        putPolicy.put("callbackBodyType", "application/json");
        long expireSeconds = 3600;

        JSONObject result = new JSONObject();
        result.put("token", auth.uploadToken(bucket, null, expireSeconds, putPolicy, false));
        result.put("domain", httpBaseUrl);
        return result;
    }

    /**
     * @param data 字节数据
     * @param path 云存储文件路径
     * @return 图片HTTP地址
     */
    private void uploadToOss(byte[] data, String path) {
        try {
            Auth auth = Auth.create(accesskey, secretkey);
            Configuration cfg = new Configuration(Region.huadong());
            UploadManager uploadManager = new UploadManager(cfg);
            Response res = uploadManager.put(data, path, auth.uploadToken(bucket));
            log.info("====== 七牛上传返回：{}", JSON.toJSONString(res));
            if (!res.isOK()) {
                throw new RuntimeException("上传七牛出错：" + res.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param inputStream 字节流
     * @param path        云存储文件路径
     * @return 图片HTTP地址
     */
    private void uploadToOss(InputStream inputStream, String path) {
        try {
            upload(IOUtils.toByteArray(inputStream), path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param file 文件
     * @param path 云存储文件路径
     * @return 图片HTTP地址
     */
    private void uploadToOss(File file, String path) {
        try {
            upload(FileUtils.readFileToByteArray(file), path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getFormattedBaseUrl() {
        if (!httpBaseUrl.endsWith("/")) {
            httpBaseUrl += "/";
        }
        return httpBaseUrl;
    }
}
