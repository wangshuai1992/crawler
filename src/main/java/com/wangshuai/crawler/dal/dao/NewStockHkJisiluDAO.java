package com.wangshuai.crawler.dal.dao;

import com.wangshuai.crawler.dal.dataobject.NewStockHkJisiluDO;
import com.wangshuai.crawler.dal.mapper.NewStockHkJisiluMapper;
import com.wangshuai.crawler.dal.query.NewStockHkJisiluQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * NewStockHkJisiluDAO
 *
 * @author wangshuai
 * @date 2021-01-05 14:55
 */
@Service
@Slf4j
public class NewStockHkJisiluDAO {

    @Resource
    private NewStockHkJisiluMapper mapper;

    public NewStockHkJisiluQuery<NewStockHkJisiluDO> pageQuery(NewStockHkJisiluQuery<NewStockHkJisiluDO> query) {
        int count = mapper.countByQuery(query);
        if (count != 0) {
            query.setTotalCount(count);
            List<NewStockHkJisiluDO> list = mapper.pageQuery(query);
            query.setDataList(list);
        }
        return query;
    }

    public int countByQuery(NewStockHkJisiluQuery<NewStockHkJisiluDO> query) {
        return mapper.countByQuery(query);
    }

    public List<NewStockHkJisiluDO> fullQuery(NewStockHkJisiluQuery<NewStockHkJisiluDO> query) {
        return mapper.fullQuery(query);
    }

    public void insert(NewStockHkJisiluDO record) {
        if (record != null) {
            mapper.insert(record);
        }
    }

    public void batchInsert(List<NewStockHkJisiluDO> records) {
        if (!CollectionUtils.isEmpty(records)) {
            mapper.batchInsert(records);
        }
    }

    public NewStockHkJisiluDO findById(long id) {
        return mapper.findById(id);
    }

    public void update(NewStockHkJisiluDO record) {
        if (record != null) {
            mapper.update(record);
        }
    }

    public void batchUpdateById(List<NewStockHkJisiluDO> records) {
        if (!CollectionUtils.isEmpty(records)) {
            mapper.batchUpdateById(records);
        }
    }

    public void insertOrUpdate(NewStockHkJisiluDO record) {
        if (record != null) {
            mapper.insertOrUpdate(record);
        }
    }

    public void deleteById(long id) {
        mapper.deleteById(id);
    }

    public void deleteByIds(List<Long> idList) {
        if (!CollectionUtils.isEmpty(idList)) {
            mapper.deleteByIds(idList);
        }
    }

}
