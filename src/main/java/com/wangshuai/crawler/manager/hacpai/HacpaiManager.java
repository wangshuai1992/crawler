package com.wangshuai.crawler.manager.hacpai;

import com.wangshuai.crawler.dal.dao.HacpaiArticleDAO;
import com.wangshuai.crawler.dal.dataobject.HacpaiArticleDO;
import com.wangshuai.crawler.dal.query.HacpaiArticleQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * HacpaiManager
 *
 * @author wangshuai
 * @version V1.0
 * @date 2020-08-30 22:49
 */
@Service
@Slf4j
public class HacpaiManager {

    @Value("${hacpai.host}")
    private String hacpaiHost;

    @Value("${hacpai.cookies}")
    private String hacpaiCookies;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private HacpaiArticleDAO articleDAO;

    public void resolveListPage() {
        String url = "http://" + hacpaiHost + "/recent";
        HttpHeaders requestHeaders = new HttpHeaders();
        setHeaders(requestHeaders);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        if (StringUtils.isEmpty(responseEntity.getBody())) {
            return;
        }
        Document listPage = Jsoup.parse(responseEntity.getBody());
        int maxPageSize = getMaxPageSize(listPage);
        log.info("========== maxPageSize : {} ===========", maxPageSize);

        for (int i = maxPageSize; i >= 1; i--) {
            url = "http://" + hacpaiHost + "/recent?p=" + i;
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            if (StringUtils.isEmpty(responseEntity.getBody())) {
                continue;
            }
            listPage = Jsoup.parse(responseEntity.getBody());
            List<HacpaiArticleDO> articleDOS = getArticleListFromListPage(listPage);
            Collections.reverse(articleDOS);
            for (HacpaiArticleDO articleDO : articleDOS) {
                articleDAO.insertOrUpdate(articleDO);
            }
        }
    }

    public void updateAllArticleContent() {
        HacpaiArticleQuery<HacpaiArticleDO> query = new HacpaiArticleQuery<>();
        query.setPageSize(10);
        query = articleDAO.pageQuery(query);
        int totalPage = query.getTotalPage();
        for (int i = 1; i <= totalPage; i++) {
            HacpaiArticleQuery<HacpaiArticleDO> articleQuery = new HacpaiArticleQuery<>();
            articleQuery.setPageSize(10);
            articleQuery.setPageNo(i);
            articleQuery = articleDAO.pageQuery(articleQuery);
            List<HacpaiArticleDO> list = articleQuery.getDataList();
            if (!CollectionUtils.isEmpty(list)) {
                for (HacpaiArticleDO articleDO : list) {
                    updateArticle(articleDO);
                }
            }
        }
    }

    private int getMaxPageSize(Document listPage) {
        Element contentElement = listPage.select(".pagination a").last();
        String content = contentElement.html().replace(">>", "").replace("&gt;&gt;", "");
        return Integer.parseInt(content);
    }

    private List<HacpaiArticleDO> getArticleListFromListPage(Document listPage) {
        List<HacpaiArticleDO> list = new ArrayList<>();
        Elements elements = listPage.select(".article-list ul li");
        for(Element element : elements) {
            Element titleElement = element.selectFirst("h2 a");
            String title = titleElement.html();
            long articleId = Long.parseLong(titleElement.attr("data-id"));
            Element previewElement = element.selectFirst("a.abstract");
            String preview = previewElement.html();

            HacpaiArticleDO articleDO = new HacpaiArticleDO();
            articleDO.setArticleId(articleId);
            articleDO.setArticleTitle(title);
            articleDO.setArticlePreview(preview);
            list.add(articleDO);
        }
        return list;
    }

    private void updateArticle(HacpaiArticleDO articleDO) {
        String url = "http://" + hacpaiHost + "/article/" + articleDO.getArticleId();
        HttpHeaders requestHeaders = new HttpHeaders();
        setHeaders(requestHeaders);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        if (StringUtils.isEmpty(responseEntity.getBody())) {
            return;
        }
        Document page = Jsoup.parse(responseEntity.getBody());
        String content = page.selectFirst(".article-content").html();
        articleDO.setArticleContent(content);
        articleDAO.update(articleDO);
    }

    private void setHeaders(HttpHeaders requestHeaders) {
        requestHeaders.add("Cookie", hacpaiCookies);
        requestHeaders.add("User-Agent", "PostmanRuntime/7.26.1");
    }

}
