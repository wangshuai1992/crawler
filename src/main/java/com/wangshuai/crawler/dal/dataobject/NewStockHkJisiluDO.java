package com.wangshuai.crawler.dal.dataobject;

import lombok.Data;
import xin.allonsy.common.BaseDO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * NewStockHkJisiluDO
 *
 * @author wangshuai
 * @date 2021-01-05 14:55
 */
@Data
public class NewStockHkJisiluDO extends BaseDO {

    private static final long serialVersionUID = -599319111921731975L;

    /**
     * stock代码 stock_cd
     */
    private String stockCode;

    /**
     * stock名称 stock_nm
     */
    private String stockName;

    /**
     * 上市板块 market
     */
    private String market;

    /**
     * 申购起始 apply_dt
     */
    private String applyDate;

    /**
     * 申购起始yyyy-MM-dd apply_dt2
     */
    private Date applyDate2;

    /**
     * 申购截止 apply_end_dt
     */
    private String applyEndDate;

    /**
     * 申购截止yyyy-MM-dd apply_end_dt2
     */
    private Date applyEndDate2;

    /**
     * 暗盘日 gray_dt
     */
    private Date grayDate;

    /**
     * 上市日 list_dt
     */
    private String listDate;

    /**
     * 上市日yyyy-MM-dd list_dt2
     */
    private Date listDate2;

    /**
     * 最低询价（港元） price_range
     */
    private BigDecimal priceMin;

    /**
     * 最高询价（港元） price_range
     */
    private BigDecimal priceMax;

    /**
     * 发行价 issue_price
     */
    private BigDecimal issuePrice;

    /**
     * 发行价pe最低 issue_pe_range
     */
    private BigDecimal issuePeMin;

    /**
     * 发行价pe最高 issue_pe_range
     */
    private BigDecimal issuePeMax;

    /**
     * 是否建议申购 jsl_advise
     */
    private String jslAdvise;

    /**
     * 预测首日涨幅？ jsl_first_incr_rt
     */
    private String jslFirstIncrRate;

    /**
     * 绿鞋保护/公开发售（带百分号的字符串） green_rate
     */
    private String greenRate;

    /**
     * 绿鞋数量（万股） green_amount
     */
    private BigDecimal greenAmount;

    /**
     * 可比公司 ref_company
     */
    private String refCompany;

    /**
     * 集思录预测超购倍数 jsl_above_rate
     */
    private BigDecimal jslAboveRate;

    /**
     * 一手资金（港元） single_draw_money
     */
    private BigDecimal singleDrawMoney;

    /**
     * 一手中签率（不含%） lucky_draw_rt
     */
    private BigDecimal luckyDrawRate;

    /**
     * 募资金额min（亿港元） raise_money
     */
    private BigDecimal raiseMoneyMin;

    /**
     * 募资金额max（亿港元） raise_money
     */
    private BigDecimal raiseMoneyMax;

    /**
     * 利弗莫尔暗盘涨福（不含%） gray_incr_rt
     */
    private BigDecimal grayIncrRate;

    /**
     * xx暗盘涨福（不含%） gray_incr_rt2
     */
    private BigDecimal grayIncrRate2;

    /**
     * 首日涨福（不含%） first_incr_rt
     */
    private BigDecimal firstIncrRt;

    /**
     * 至今涨幅（不含%） total_incr_rt
     */
    private BigDecimal totalIncrRt;

    /**
     * 承销商 underwriter
     */
    private String underwriter;

    /**
     * 招股说明书 prospectus
     */
    private String prospectus;

    /**
     * ipo配售结果 iporesult
     */
    private String iporesult;

    /**
     * apply_flg
     */
    private Integer applyFlg;

    /**
     * list_flg
     */
    private Integer listFlg;

    /**
     * status_cd
     */
    private String statusCd;

    /**
     * yx_rate
     */
    private BigDecimal yxRate;

    /**
     * 是否有超购倍数true/false has_above_rt
     */
    private String hasAboveRt;

    /**
     * notes
     */
    private String notes;

}
