package com.wangshuai.crawler.dal.mapper;

import com.wangshuai.crawler.dal.dataobject.NewStockHkJisiluDO;
import com.wangshuai.crawler.dal.query.NewStockHkJisiluQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * NewStockHkJisiluMapper
 *
 * @author wangshuai
 * @date 2021-01-05 14:55
 */
@Mapper
public interface NewStockHkJisiluMapper {

    /**
     * 新增
     *
     * @param record
     * @return
     */
    int insert(NewStockHkJisiluDO record);

    /**
     * 批量新增
     *
     * @param records
     * @return
     */
    int batchInsert(List<NewStockHkJisiluDO> records);

    /**
     * 根据条件更新
     *
     * @param record
     * @return
     */
    int update(NewStockHkJisiluDO record);

    /**
     * 批量更新
     *
     * @param records
     * @return
     */
    void batchUpdateById(List<NewStockHkJisiluDO> records);

    /**
     * 插入 id相同则更新
     *
     * @param record
     */
    void insertOrUpdate(NewStockHkJisiluDO record);

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
    NewStockHkJisiluDO findById(long id);

    /**
     * 查询总数
     *
     * @param query
     * @return
     */
    int countByQuery(NewStockHkJisiluQuery<NewStockHkJisiluDO> query);

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    List<NewStockHkJisiluDO> pageQuery(NewStockHkJisiluQuery<NewStockHkJisiluDO> query);

    /**
     * 根据条件获取所有符合记录的数据
     *
     * @param query
     * @return
     */
    List<NewStockHkJisiluDO> fullQuery(NewStockHkJisiluQuery<NewStockHkJisiluDO> query);

}
