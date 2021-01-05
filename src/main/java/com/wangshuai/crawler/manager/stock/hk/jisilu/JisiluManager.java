package com.wangshuai.crawler.manager.stock.hk.jisilu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangshuai.crawler.dal.dao.NewStockHkJisiluDAO;
import com.wangshuai.crawler.dal.dataobject.NewStockHkJisiluDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import xin.allonsy.utils.DateUtils;
import xin.allonsy.utils.RedisUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
public class JisiluManager {

    public static final String JISILU_COOKIES_KEY = "jisilu-cookies";

    @Value("${jisilu.host}")
    private String jisiluHost;

    @Value("${jisilu.username}")
    private String userName;

    @Value("${jisilu.password}")
    private String password;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private NewStockHkJisiluDAO newStockHkJisiluDAO;

    public void updateData() {
        String url = "https://" + jisiluHost + "/data/new_stock/hkipo/?___jsl=LST___t=" + System.currentTimeMillis();
        HttpHeaders requestHeaders = new HttpHeaders();
        setHeaders(requestHeaders, true);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        } catch (RestClientResponseException e) {
            log.error("http获取集思录hk新股数据出错 : {}", e.getResponseBodyAsString(), e);
            return;
        }
        if (StringUtils.isEmpty(responseEntity.getBody())) {
            log.warn("http请求 返回结果为空, url : {}", url);
            return;
        }
        JSONArray jsonArray = JSON.parseObject(responseEntity.getBody()).getJSONArray("rows");
        Collections.reverse(jsonArray);
        for (Object obj : jsonArray) {
            JSONObject cell = ((JSONObject) obj).getJSONObject("cell");
            NewStockHkJisiluDO newStockHkJisiluDO = transformCellToDO(cell);
            newStockHkJisiluDAO.insertOrUpdate(newStockHkJisiluDO);
        }
    }

    public void login() {
        log.info("========== 集思录登录，记录cookie begin ===========");
        String url = "https://" + jisiluHost + "/account/ajax/login_process/";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "multipart/form-data; charset=UTF-8");
        requestHeaders.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:57.0) Gecko/20100101 Firefox/57.0");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("user_name", userName);
        map.add("password", password);
        map.add("aes", "1");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        List<String> respCookies = responseEntity.getHeaders().getValuesAsList("Set-Cookie");
        StringBuilder cookies = new StringBuilder();
        for (String str : respCookies) {
            if (!str.contains("deleted")) {
                cookies.append(str).append(";");
            }
        }
        RedisUtil.set(JISILU_COOKIES_KEY, cookies.toString(), RandomUtils.nextInt(24 * 60 * 60, 48 * 60 * 60));
        log.info("========== 集思录登录，记录cookie end ===========");
    }

    private NewStockHkJisiluDO transformCellToDO(JSONObject cell) {
        NewStockHkJisiluDO jisiluDO = new NewStockHkJisiluDO();
        jisiluDO.setStockCode(cell.getString("stock_cd"));
        jisiluDO.setStockName(cell.getString("stock_nm"));
        jisiluDO.setMarket(cell.getString("market"));
        jisiluDO.setApplyDate(cell.getString("apply_dt"));
        jisiluDO.setApplyDate2(DateUtils.parse(cell.getString("apply_dt2"), "yyyy-MM-dd"));
        jisiluDO.setApplyEndDate(cell.getString("apply_end_dt"));
        jisiluDO.setApplyEndDate2(DateUtils.parse(cell.getString("apply_end_dt2"), "yyyy-MM-dd"));
        jisiluDO.setGrayDate(DateUtils.parse(cell.getString("gray_dt"), "yyyy-MM-dd"));
        jisiluDO.setListDate(cell.getString("listDate"));
        jisiluDO.setListDate2(DateUtils.parse(cell.getString("list_dt2"), "yyyy-MM-dd"));
        if (StringUtils.isNotEmpty(cell.getString("price_range"))) {
            String[] sArr = cell.getString("price_range").split("-");
            if (StringUtils.isEmpty(sArr[0])) {
                sArr[0] = sArr[sArr.length - 1];
            }
            jisiluDO.setPriceMin(BigDecimal.valueOf(Double.parseDouble(sArr[0])));
            jisiluDO.setPriceMax(BigDecimal.valueOf(Double.parseDouble(sArr[sArr.length - 1])));
        }
        if (StringUtils.isNotEmpty(cell.getString("issue_price"))) {
            jisiluDO.setIssuePrice(BigDecimal.valueOf(Double.parseDouble(cell.getString("issue_price"))));
        }
        if (StringUtils.isNotEmpty(cell.getString("issue_pe_range"))) {
            String[] sArr = cell.getString("issue_pe_range").split("-");
            if (StringUtils.isEmpty(sArr[0])) {
                sArr[0] = sArr[sArr.length - 1];
            }
            jisiluDO.setIssuePeMin(BigDecimal.valueOf(Double.parseDouble(sArr[0])));
            jisiluDO.setIssuePeMax(BigDecimal.valueOf(Double.parseDouble(sArr[sArr.length - 1])));
        }
        jisiluDO.setJslAdvise(cell.getString("jsl_advise"));
        jisiluDO.setJslFirstIncrRate(cell.getString("jsl_first_incr_rt"));
        jisiluDO.setGreenRate(cell.getString("green_rate"));
        if (StringUtils.isNotEmpty(cell.getString("green_amount"))) {
            jisiluDO.setGreenAmount(BigDecimal.valueOf(Double.parseDouble(cell.getString("green_amount"))));
        }
        jisiluDO.setRefCompany(cell.getString("ref_company"));
        if (StringUtils.isNotEmpty(cell.getString("jsl_above_rate"))) {
            jisiluDO.setJslAboveRate(BigDecimal.valueOf(Double.parseDouble(cell.getString("jsl_above_rate"))));
        }
        if (StringUtils.isNotEmpty(cell.getString("single_draw_money"))) {
            jisiluDO.setSingleDrawMoney(BigDecimal.valueOf(Double.parseDouble(cell.getString("single_draw_money"))));
        }
        if (StringUtils.isNotEmpty(cell.getString("lucky_draw_rt"))) {
            jisiluDO.setLuckyDrawRate(BigDecimal.valueOf(Double.parseDouble(cell.getString("lucky_draw_rt"))));
        }
        if (StringUtils.isNotEmpty(cell.getString("raise_money"))) {
            String[] sArr = cell.getString("raise_money").split("-");
            if (StringUtils.isEmpty(sArr[0])) {
                sArr[0] = sArr[sArr.length - 1];
            }
            jisiluDO.setRaiseMoneyMin(BigDecimal.valueOf(Double.parseDouble(sArr[0])));
            jisiluDO.setRaiseMoneyMax(BigDecimal.valueOf(Double.parseDouble(sArr[sArr.length - 1])));
        }
        if (StringUtils.isNotEmpty(cell.getString("gray_incr_rt"))) {
            jisiluDO.setGrayIncrRate(BigDecimal.valueOf(Double.parseDouble(cell.getString("gray_incr_rt"))));
        }
        if (StringUtils.isNotEmpty(cell.getString("gray_incr_rt2"))) {
            jisiluDO.setGrayIncrRate2(BigDecimal.valueOf(Double.parseDouble(cell.getString("gray_incr_rt2"))));
        }
        if (StringUtils.isNotEmpty(cell.getString("first_incr_rt"))) {
            jisiluDO.setFirstIncrRt(BigDecimal.valueOf(Double.parseDouble(cell.getString("first_incr_rt"))));
        }
        if (StringUtils.isNotEmpty(cell.getString("total_incr_rt"))) {
            jisiluDO.setTotalIncrRt(BigDecimal.valueOf(Double.parseDouble(cell.getString("total_incr_rt"))));
        }
        jisiluDO.setUnderwriter(cell.getString("underwriter"));
        jisiluDO.setProspectus(cell.getString("prospectus"));
        jisiluDO.setIporesult(cell.getString("iporesult"));
        jisiluDO.setApplyFlg(cell.getInteger("apply_flg"));
        jisiluDO.setListFlg(cell.getInteger("list_flg"));
        jisiluDO.setStatusCd(cell.getString("status_cd"));
        if (StringUtils.isNotEmpty(cell.getString("yx_rate"))) {
            jisiluDO.setYxRate(BigDecimal.valueOf(Double.parseDouble(cell.getString("yx_rate"))));
        }
        jisiluDO.setHasAboveRt(cell.getString("has_above_rt"));
        jisiluDO.setNotes(cell.getString("notes"));
        return jisiluDO;
    }

    private void setHeaders(HttpHeaders requestHeaders, boolean withCookies) {
        requestHeaders.add("Content-Type", "application/json; charset=UTF-8");
        if (withCookies) {
            String cookies = (String) RedisUtil.getValue(JISILU_COOKIES_KEY);
            if (StringUtils.isEmpty(cookies)) {
                this.login();
            }
            requestHeaders.add("Cookie", (String) RedisUtil.getValue(JISILU_COOKIES_KEY));
        }
        requestHeaders.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:57.0) Gecko/20100101 Firefox/57.0");
    }

}
