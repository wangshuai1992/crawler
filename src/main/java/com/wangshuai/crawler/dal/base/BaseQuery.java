package com.wangshuai.crawler.dal.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * BaseQuery
 *
 * @author wangshuai
 */
@Data
public class BaseQuery implements Serializable {

	private static final long serialVersionUID = -5481471699688831881L;

	/**
	 *  记录 id
	 */
	protected Long id;

	/**
	 *  创建时间
	 */
	protected Date createTime;

	/**
	 *  修改时间
	 */
	protected Date updateTime;

}
