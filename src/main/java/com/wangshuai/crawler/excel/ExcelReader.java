package com.wangshuai.crawler.excel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import xin.allonsy.utils.EasyExcelUtil;
import xin.allonsy.utils.FileUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ExcelReader
 *
 * @author wangshuai
 * @version V1.0
 * @date 2020-11-03 14:20
 */
@Slf4j
public class ExcelReader {

    public static String resourcePath = Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("/target/classes", "/src/main/resources");

    public static List<Map<Integer, String>> readerExcelAsJson(String filePath, String sheetName) {
        try {
            return EasyExcelUtil.readExcelAsStringList(new FileInputStream(filePath), sheetName);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public static String getConsultQuestionJson() {
        List<ConsultQuestionBO> boList = new ArrayList<>();

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("consult_question_list_old.json");
        JSONArray jsonArray = JSON.parseArray(FileUtil.readFile(inputStream));
        jsonArray.forEach(o ->
                {
                    JSONObject jsonObject = (JSONObject) o;
                    ConsultQuestionBO bo = new ConsultQuestionBO();
                    bo.setQuestionId(jsonObject.getInteger("questionId"));
                    bo.setLevelTwo(jsonObject.getString("levelTwo"));
                    bo.setLevelOne(jsonObject.getString("levelOne"));
                    bo.setIsShowToUser(jsonObject.getBoolean("isShowToUser"));
                    bo.setNeedOrder(jsonObject.getBoolean("needOrder"));
                    bo.setNeedImages(jsonObject.getBoolean("needImages"));
                    bo.setOrderStates(Arrays.stream(jsonObject.getJSONArray("orderStates").toArray(new String[0])).collect(Collectors.toSet()));
                    bo.setDescription(jsonObject.getString("description"));
                    bo.setIsActive(false);
                    if (jsonObject.containsKey("faqQuestionIds")) {
                        bo.setFaqQuestionIds(Arrays.stream(jsonObject.getJSONArray("faqQuestionIds").toArray(new Integer[0])).collect(Collectors.toSet()));
                    } else {
                        bo.setFaqQuestionIds(new HashSet<>());
                    }

                    if (jsonObject.containsKey("exampleImageUrls")) {
                        bo.setExampleImageUrls(Arrays.stream(jsonObject.getJSONArray("exampleImageUrls").toArray(new String[0])).collect(Collectors.toSet()));
                    } else {
                        bo.setExampleImageUrls(new HashSet<>());
                    }

                    if (jsonObject.containsKey("needProduct")) {
                        bo.setNeedProduct(jsonObject.getBoolean("needProduct"));
                    } else {
                        bo.setNeedProduct(false);
                    }

                    if (jsonObject.containsKey("showLinkToCancelOrder")) {
                        bo.setShowLinkToCancelOrder(jsonObject.getBoolean("showLinkToCancelOrder"));
                    } else {
                        bo.setShowLinkToCancelOrder(false);
                    }

                    if (jsonObject.containsKey("showLinkToModifyOrderAddress")) {
                        bo.setShowLinkToModifyOrderAddress(jsonObject.getBoolean("showLinkToModifyOrderAddress"));
                    } else {
                        bo.setShowLinkToModifyOrderAddress(false);
                    }

                    if (jsonObject.containsKey("showLinkToCancelSubscribe")) {
                        bo.setShowLinkToCancelSubscribe(jsonObject.getBoolean("showLinkToCancelSubscribe"));
                    } else {
                        bo.setShowLinkToCancelSubscribe(false);
                    }
                    boList.add(bo);
                }
        );
        List<Map<Integer, String>> readResult = (ExcelReader.readerExcelAsJson(resourcePath + "/consultQuestion.xlsx", "Sheet1"));
        // 去掉表头
        readResult.remove(0);
        for (Map<Integer, String> row : readResult) {
            ConsultQuestionBO bo = new ConsultQuestionBO();
            bo.setQuestionId(Integer.parseInt(row.get(0)));
            bo.setLevelTwo(row.get(2));
            bo.setLevelOne(row.get(1));
            // 用户可见
            bo.setIsShowToUser(true);
            bo.setNeedOrder(!StringUtils.isEmpty(row.get(3)));
            bo.setNeedImages(!StringUtils.isEmpty(row.get(6)));
            bo.setOrderStates(row.get(5) == null ? Collections.emptySet() : Arrays.stream(row.get(5).split(",")).collect(Collectors.toSet()));
            bo.setDescription(row.get(12));
            bo.setIsActive(true);
            bo.setFaqQuestionIds(row.get(10) == null ? Collections.emptySet() : Arrays.stream(row.get(10).split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toSet()));
            bo.setExampleImageUrls(row.get(11) == null ? Collections.emptySet() : Arrays.stream(row.get(11).split(",")).collect(Collectors.toSet()));
            bo.setNeedProduct(!StringUtils.isEmpty(row.get(4)));
            bo.setShowLinkToCancelOrder(!StringUtils.isEmpty(row.get(7)));
            bo.setShowLinkToModifyOrderAddress(!StringUtils.isEmpty(row.get(8)));
            bo.setShowLinkToCancelSubscribe(!StringUtils.isEmpty(row.get(9)));
            boList.add(bo);
        }
        return JSON.toJSONString(boList);
    }

    public static String getReplyScriptJson() {
        List<Map<Integer, String>> readResult = (ExcelReader.readerExcelAsJson(resourcePath + "/2.xlsx", "Sheet1"));
        // 去掉表头
        readResult.remove(0);
        JSONArray jsonArray = new JSONArray();
        for (Map<Integer, String> row : readResult) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", row.get(0));
            jsonObject.put("content", row.get(1));
            jsonArray.add(jsonObject);
        }
        return JSON.toJSONString(jsonArray);
    }

    public static String getFinalConsultTypeJson() {
        List<FinalConsultBO> boList = new ArrayList<>();
        EnumSet.allOf(FinalConsultType.class).forEach(enumType -> {
            FinalConsultBO finalConsultBO = new FinalConsultBO();
            finalConsultBO.setIsActive(false);
            finalConsultBO.setTypeId(enumType.getValue());
            finalConsultBO.setLevel4(enumType.getText());
            finalConsultBO.setLevel1(enumType.getLevel1());
            finalConsultBO.setLevel2(enumType.getLevel2());
            finalConsultBO.setLevel3(enumType.getLevel3());
            boList.add(finalConsultBO);
        });

        List<Map<Integer, String>> readResult = (ExcelReader.readerExcelAsJson(resourcePath + "/finalQuestionType.xlsx", "Sheet1"));
        // 去掉表头
        readResult.remove(0);
        for (Map<Integer, String> row : readResult) {
            FinalConsultBO bo = new FinalConsultBO();
            bo.setIsActive(true);
            bo.setTypeId(Integer.parseInt(row.get(0)));
            bo.setLevel1(row.get(1));
            bo.setLevel2(row.get(2));
            bo.setLevel3(row.get(3));
            bo.setLevel4(row.get(4));
            boList.add(bo);
        }
        return JSON.toJSONString(boList);
    }

    public static String getAddShopifyTagsData() {
        JSONArray data = new JSONArray();
        List<Map<Integer, String>> readResult = (ExcelReader.readerExcelAsJson(resourcePath + "/addTagsData.xlsx", "Sheet1"));
        // 去掉表头
        readResult.remove(0);
        for (Map<Integer, String> row : readResult) {
            JSONObject jsonObject = new JSONObject(true);
            jsonObject.put("spuId", row.get(0).trim());
            jsonObject.put("shopId", row.get(1).trim());
            jsonObject.put("tags", row.get(2).trim());
            data.add(jsonObject);
        }
        return data.toJSONString();
    }

    public static void main(String[] args) {
        System.out.println(getAddShopifyTagsData());
//        System.out.println(getFinalConsultTypeJson());
//        System.out.println(getConsultQuestionJson());
//        System.out.println(getReplyScriptJson());
    }

}
