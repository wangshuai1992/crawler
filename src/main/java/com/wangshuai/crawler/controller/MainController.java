package com.wangshuai.crawler.controller;

import com.wangshuai.crawler.dal.dao.HacpaiArticleDAO;
import com.wangshuai.crawler.dal.dataobject.HacpaiArticleDO;
import com.wangshuai.crawler.dal.query.HacpaiArticleQuery;
import com.wangshuai.crawler.manager.novel.NovelDownloader;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xin.allonsy.common.PageQuery;

import javax.annotation.Resource;

/**
 * MainController
 *
 * @author wangshuai
 * @version V1.0
 * @since 2017-08-27 13:24
 */
@Controller
@Slf4j
public class MainController {

    @Resource
    private NovelDownloader novelDownloader;

    @Resource
    private HacpaiArticleDAO hacpaiArticleDAO;

//    @RequestMapping("/")
//    public String index(ModelMap modelMap) {
//        return "main";
//    }

    @RequestMapping("/{refundId}")
    @ResponseBody
    public String getTableSuffix(@PathVariable("refundId") String refundId) {
        if (StringUtil.isNotEmpty(refundId)) {
            int x = Math.abs(refundId.hashCode() % 128);
            String result = "";
            if (x < 10) {
                result = "00" + x;
            } else {
                if (x < 100) {
                    result = "0" + x;
                }
                if (x > 99) {
                    result = x + "";
                }
            }
            return result;
        }
        return null;
    }

    @RequestMapping("/blogList")
    public String blogList(@RequestParam(value = "p", defaultValue = "1") Integer page, ModelMap modelMap) {
        modelMap.put("page", page);
        return "blog-list";
    }

    @RequestMapping("/articleDetail")
    public String articleDetail(@RequestParam(value = "articleId") Long articleId, ModelMap modelMap) {
        modelMap.put("articleId", String.valueOf(articleId));
        return "article/article-detail";
    }

    @RequestMapping("/blog-list.json")
    @ResponseBody
    public PageQuery<HacpaiArticleDO> blogListPageQuery(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize") Integer pageSize) {
        HacpaiArticleQuery<HacpaiArticleDO> query = new HacpaiArticleQuery<>();
        query.setPageNo(pageNo);
        query.setPageSize(pageSize);
        return hacpaiArticleDAO.pageQuery(query);
    }

    @RequestMapping("/get-article.json")
    @ResponseBody
    public HacpaiArticleDO getArticleById(@RequestParam(value = "articleId", defaultValue = "1") Long articleId) {
        HacpaiArticleQuery<HacpaiArticleDO> query = new HacpaiArticleQuery<>();
        query.setArticleId(articleId);
        return hacpaiArticleDAO.fullQuery(query).get(0);
    }

    @RequestMapping("/downloadNovel")
    @ResponseBody
    public String downloadNovel(ModelMap modelMap) throws Exception {
        novelDownloader.downloadNovel();
        return "done";
    }

}
