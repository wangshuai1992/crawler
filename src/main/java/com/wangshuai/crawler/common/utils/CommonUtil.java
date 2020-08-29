package com.wangshuai.crawler.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * CommonUtil
 *
 * @author wangshuai
 * @date 2020-07-21 15:56
 */
public class CommonUtil {

    public static String limitedString(String str, int lengthLimit) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        if (str.length() > lengthLimit) {
            str = str.substring(0, lengthLimit) + "...(data too long)";
        }
        return str;
    }

}
