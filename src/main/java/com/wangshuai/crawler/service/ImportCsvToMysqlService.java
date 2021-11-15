package com.wangshuai.crawler.service;

import com.wangshuai.crawler.dal.dao.RedisMemoryDAO;
import com.wangshuai.crawler.dal.dataobject.RedisMemoryDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import xin.allonsy.utils.DateUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ImportCsvToMysql
 *
 * @author wangshuai
 * @version V1.0
 * @date 2021-11-11 16:03
 */
@SuppressWarnings({"AlibabaStringConcat", "StringBufferReplaceableByString"})
@Slf4j
@Service
public class ImportCsvToMysqlService {

    @Resource
    private RedisMemoryDAO redisMemoryDAO;

    public void importCsvToMysql() throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\memory.csv"))) {

            int insertCount = 0;
            List<RedisMemoryDO> lineList = new ArrayList<>();
            String temp;
            String uncompleteLineTemp = null;
            // 忽略第一行
            br.readLine();
            while ((temp = br.readLine()) != null) {
                if (StringUtils.isBlank(temp)) {
                    continue;
                }
                RedisMemoryDO line;
                try {
                    // 是否是不完整行的第二行
                    if (StringUtils.isNotEmpty(uncompleteLineTemp)) {
                        temp = new StringBuilder().append(uncompleteLineTemp).append("\n").append(temp).toString();
                        uncompleteLineTemp = null;
                    }
                    // key: 第几个逗号, value: 逗号的下标
                    Map<Integer, Integer> commaIndexMap = new HashMap<>(30);
                    int commaCount = 0;
                    char[] charArray = temp.toCharArray();
                    for (int i = 0, charArrayLength = charArray.length; i < charArrayLength; i++) {
                        char c = charArray[i];
                        if (c == ',') {
                            commaCount++;
                            commaIndexMap.put(commaCount, i);
                        }
                    }
                    if (commaIndexMap.size() < 7) {
                        // 不完整的行
                        uncompleteLineTemp = temp;
                        continue;
                    }
                    line = convertLine(temp, commaIndexMap);
                } catch (Exception e) {
                    log.error(temp, e);
                    throw e;
                }
                lineList.add(line);
                if (lineList.size() >= 2000) {
                    redisMemoryDAO.batchInsert(lineList);
                    insertCount += lineList.size();
                    lineList.clear();
                    log.info("==== importCsvToMysql 已插入{}行", insertCount);
                }
            }
            if (CollectionUtils.isNotEmpty(lineList)) {
                redisMemoryDAO.batchInsert(lineList);
                insertCount += lineList.size();
            }
            log.info("==== importCsvToMysql 导入完成, 共插入{}行", insertCount);
        }
    }

    /**
     * `database`, `type`, `key`, `size_in_bytes`, `encoding`, `num_elements`, `len_largest_element`, `expiry`
     *
     * 其中key中可能有逗号 key的值位于
     * database: split[0]
     * type: split[1]
     * key: 第二个逗号和倒数第5个逗号之间的字符
     * size_in_bytes: split[length - 5]
     * encoding: split[length - 4]
     * num_elements: split[length - 3]
     * len_largest_element: split[length - 2]
     * expiry: split[length - 1]
     *
     * @param str
     * @param commaIndexMap
     * @return
     */
    private static RedisMemoryDO convertLine(String str, Map<Integer, Integer> commaIndexMap) {
        if (str.endsWith(",")) {
            // 兼容
            str += "x";
        }
        String[] splits = str.split(",");

        RedisMemoryDO memoryLine = new RedisMemoryDO();
        memoryLine.setRedisDatabase(Long.parseLong(splits[0]));
        memoryLine.setRedisType(splits[1]);
        memoryLine.setRedisKey(str.substring(commaIndexMap.get(2) + 1, commaIndexMap.get(commaIndexMap.size() - 4)));
        if (memoryLine.getRedisKey().length() > 750) {
            // 防止数据库索引过长报错
            memoryLine.setRedisKey(memoryLine.getRedisKey().substring(0, 750));
        }
        memoryLine.setSizeInBytes(Long.parseLong(splits[splits.length - 5]));
        memoryLine.setEncoding(splits[splits.length - 4]);
        String numEleStr = splits[splits.length - 3];
        memoryLine.setNumElements(StringUtils.isEmpty(numEleStr) ? 0 : Long.parseLong(numEleStr));
        memoryLine.setLenLargestElement(splits[splits.length - 2]);
        String expiryStr = splits[splits.length - 1];
        memoryLine.setExpiry(DateUtils.parse(StringUtils.isEmpty(expiryStr) || "x".equals(expiryStr) ? null : expiryStr, "yyyy-MM-dd'T'HH:mm:ss"));
        return memoryLine;
    }

}
