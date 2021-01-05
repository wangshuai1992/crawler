package com.wangshuai.crawler.dal.query;

import lombok.Data;
import xin.allonsy.common.PageQuery;

import java.util.Date;

/**
 * HacpaiArticleQuery
 *
 * @author wangshuai
 * @date 2020-08-30 22:38
 */
@Data
public class HacpaiArticleQuery<T> extends PageQuery<T> {

    private static final long serialVersionUID = -119643891961678945L;

    /**
     * article_id
     */
    private Long articleId;

    /**
     * 标题
     */
    private String articleTitle;

    /**
     * article_preview
     */
    private String articlePreview;

    /**
     * 内容
     */
    private String articleContent;

    /**
     * 发布时间
     */
    private Date postTime;

}
