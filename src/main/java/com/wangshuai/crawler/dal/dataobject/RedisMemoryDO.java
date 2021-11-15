package com.wangshuai.crawler.dal.dataobject;

import lombok.Data;
import xin.allonsy.common.BaseDO;

import java.util.Date;

/**
 * RedisMemoryDO
 *
 * @author wangshuai
 * @date 2021-11-11 18:05
 */
@Data
public class RedisMemoryDO extends BaseDO {

    private static final long serialVersionUID = -628329161111358121L;

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
