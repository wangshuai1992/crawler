package com.wangshuai.crawler.dal.query;

import lombok.Data;
import xin.allonsy.common.PageQuery;

import java.util.Date;

/**
 * RedisMemoryQuery
 *
 * @author wangshuai
 * @date 2021-11-11 18:05
 */
@Data
public class RedisMemoryQuery<T> extends PageQuery<T> {

    private static final long serialVersionUID = -262546883187711173L;

    /**
     * key在Redis的db
     */
    private Long redisDatabase;

    /**
     * key类型
     */
    private String redisType;

    /**
     * key值
     */
    private String redisKey;

    /**
     * key的内存大小
     */
    private Long sizeInBytes;

    /**
     * value的存储编码形式
     */
    private String encoding;

    /**
     * key中的value的个数
     */
    private Long numElements;

    /**
     * key中的value的长度
     */
    private String lenLargestElement;

    /**
     * 过期时间
     */
    private Date expiry;

}
