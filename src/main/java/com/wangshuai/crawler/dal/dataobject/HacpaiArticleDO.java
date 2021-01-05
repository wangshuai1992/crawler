package com.wangshuai.crawler.dal.dataobject;

import lombok.Data;
import xin.allonsy.common.BaseDO;

import java.util.Date;

/**
 * HacpaiArticleDO
 *
 * @author wangshuai
 * @date 2020-08-30 22:38
 */
@Data
public class HacpaiArticleDO extends BaseDO {

    private static final long serialVersionUID = -495381861144365452L;

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
