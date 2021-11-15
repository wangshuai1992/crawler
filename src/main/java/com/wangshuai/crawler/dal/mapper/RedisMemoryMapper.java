package com.wangshuai.crawler.dal.mapper;

import com.wangshuai.crawler.dal.dataobject.RedisMemoryDO;
import com.wangshuai.crawler.dal.query.RedisMemoryQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * RedisMemoryMapper
 *
 * @author wangshuai
 * @date 2021-11-11 16:40
 */
@Mapper
public interface RedisMemoryMapper {

    /**
     * 新增
     *
     * @param record
     * @return
     */
    int insert(RedisMemoryDO record);

    /**
     * 批量新增
     *
     * @param records
     * @return
     */
    int batchInsert(List<RedisMemoryDO> records);

    /**
     * 根据条件更新
     *
     * @param record
     * @return
     */
    int update(RedisMemoryDO record);

    /**
     * 批量更新
     *
     * @param records
     * @return
     */
    void batchUpdateById(List<RedisMemoryDO> records);

    /**
     * 插入 id相同则更新
     *
     * @param record
     */
    void insertOrUpdate(RedisMemoryDO record);

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
    RedisMemoryDO findById(long id);

    /**
     * 查询总数
     *
     * @param query
     * @return
     */
    int countByQuery(RedisMemoryQuery<RedisMemoryDO> query);

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    List<RedisMemoryDO> pageQuery(RedisMemoryQuery<RedisMemoryDO> query);

    /**
     * 根据条件获取所有符合记录的数据
     *
     * @param query
     * @return
     */
    List<RedisMemoryDO> fullQuery(RedisMemoryQuery<RedisMemoryDO> query);

}
