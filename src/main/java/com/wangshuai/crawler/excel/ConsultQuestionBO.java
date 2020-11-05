package com.wangshuai.crawler.excel;

import lombok.Data;

import java.util.Set;

/**
 * ConsultQuestionBO
 *
 * @author wangshuai
 * @version V1.0
 * @date 2020-11-03 15:13
 */
@Data
public class ConsultQuestionBO {

    /**
     * questionId (value)
     */
    private Integer questionId;

    /**
     * levelTwo (text)
     */
    private String levelTwo;

    /**
     * 一级问题的String
     */
    private String levelOne;

    /**
     * 用户是否可见
     */
    private Boolean isShowToUser;

    private Boolean needOrder;

    private Boolean needImages;

    private Set<String> orderStates;

    private String description;

    private Boolean isActive;

}
