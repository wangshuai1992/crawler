package com.wangshuai.crawler.enums;

import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * JisiluAdviseEnum
 *
 * @author wangshuai
 * @version V1.0
 * @date 2021-01-05 16:10
 */
@Getter
public enum JisiluAdviseEnum {

    STRONG_ADVICE("A", "强烈建议", "可使用孖展融资申购，该股上涨概率极高"),
    ADVICE("B", "建议申购", "可使用现金申购，该股上涨概率较高"),
    WEAK_ADVICE("C", "尚可申购", "可使用小额资金现金申购，该股有一定上涨概率"),
    NOT_RECOMMEND("F", "不建议", "不建议申购，该股存破发风险"),
    AVOID("D", "建议回避", "不建议申购，该股有较大破发风险"),
    TO_BE_DETERMINED("X", "待定", "待定"),
    NO_LIST_TEMPORARILY("H", "暂不上市", "暂不上市"),
    ;

    private String value;

    private String text;

    private String tip;

    private static final Map<String, JisiluAdviseEnum> map = new HashMap<>();

    static {
        EnumSet.allOf(JisiluAdviseEnum.class).forEach(item ->
                map.put(item.getValue(),item)
        );
    }

    JisiluAdviseEnum(String value, String text, String tip) {
        this.value = value;
        this.text = text;
        this.tip = tip;
    }

    public static JisiluAdviseEnum getByValue(String value) {
        return map.get(value);
    }

}
