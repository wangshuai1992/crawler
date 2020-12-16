package com.wangshuai.crawler.excel;

import lombok.Data;

/**
 * FinalConsultBO
 *
 * @author wangshuai
 * @version V1.0
 * @date 2020-11-03 15:13
 */
@Data
public class FinalConsultBO {

    /**
     * id
     */
    private Integer typeId;

    /**
     * level four
     */
    private String level4;

    /**
     * 一级类型
     */
    private String level1;

    /**
     * 二级类型
     */
    private String level2;

    /**
     * 三级类型
     */
    private String level3;

    private Boolean isActive = true;

}
