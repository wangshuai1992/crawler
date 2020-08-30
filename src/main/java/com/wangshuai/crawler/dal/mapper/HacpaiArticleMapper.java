package com.wangshuai.crawler.dal.mapper;

import com.wangshuai.crawler.dal.dataobject.HacpaiArticleDO;
import com.wangshuai.crawler.dal.query.HacpaiArticleQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * HacpaiArticleMapper
 *
 * @author wangshuai
 * @date 2020-08-30 22:38
 */
@Mapper
public interface HacpaiArticleMapper {

    /**
     * 新增
     *
     * @param record
     * @return
     */
    int insert(HacpaiArticleDO record);

    /**
     * 批量新增
     *
     * @param records
     * @return
     */
    int batchInsert(List<HacpaiArticleDO> records);

    /**
     * 根据条件更新
     *
     * @param record
     * @return
     */
    int update(HacpaiArticleDO record);

    /**
     * 批量更新
     *
     * @param records
     * @return
     */
    void batchUpdateById(List<HacpaiArticleDO> records);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    int deleteById(long id);

    /**
     * 根据ids批量删除
     *
     * @param ids
     * @return
     */
    int deleteByIds(List<Long> ids);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    HacpaiArticleDO findById(long id);

    /**
     * 查询总数
     *
     * @param query
     * @return
     */
    int countByQuery(HacpaiArticleQuery<HacpaiArticleDO> query);

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    List<HacpaiArticleDO> pageQuery(HacpaiArticleQuery<HacpaiArticleDO> query);

    /**
     * 根据条件获取所有符合记录的数据
     *
     * @param query
     * @return
     */
    List<HacpaiArticleDO> fullQuery(HacpaiArticleQuery<HacpaiArticleDO> query);

}
