package com.wangshuai.crawler.excel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangshuai.crawler.common.utils.EasyExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
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
        EnumSet.allOf(ConsultQuestion.class).forEach(item ->
                {
                    ConsultQuestionBO bo = new ConsultQuestionBO();
                    bo.setQuestionId(item.getValue());
                    bo.setLevelTwo(item.getText());
                    bo.setLevelOne(item.getLevelOne());
                    bo.setIsShowToUser(item.getIsShowToUser());
                    bo.setNeedOrder(item.getNeedOrder());
                    bo.setNeedImages(item.getNeedImages());
                    bo.setOrderStates(item.getOrderStates());
                    bo.setDescription(item.getDescription());
                    bo.setIsActive(!bo.getIsShowToUser());
                    boList.add(bo);
                }
        );
        List<Map<Integer, String>> readResult = (ExcelReader.readerExcelAsJson(resourcePath + "/1.xlsx", "Sheet2"));
        // 去掉表头
        readResult.remove(0);
        int questionId = 2001;
        for (Map<Integer, String> row : readResult) {
            ConsultQuestionBO bo = new ConsultQuestionBO();
            bo.setQuestionId(questionId);
            bo.setLevelTwo(row.get(1));
            bo.setLevelOne(row.get(0));
            // 用户可见
            bo.setIsShowToUser(true);
            bo.setNeedOrder(!StringUtils.isEmpty(row.get(2)));
            bo.setNeedImages(!StringUtils.isEmpty(row.get(3)));
            bo.setOrderStates(row.get(4) == null ? Collections.emptySet() : Arrays.stream(row.get(4).split(",")).collect(Collectors.toSet()));
            bo.setDescription("");
            bo.setIsActive(true);
            boList.add(bo);
            questionId++;
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

    public static void main(String[] args) {
//        System.out.println(getConsultQuestionJson());
        System.out.println(getReplyScriptJson());
    }

}
