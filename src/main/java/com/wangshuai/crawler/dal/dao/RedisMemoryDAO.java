package com.wangshuai.crawler.dal.dao;

import com.wangshuai.crawler.dal.dataobject.RedisMemoryDO;
import com.wangshuai.crawler.dal.mapper.RedisMemoryMapper;
import com.wangshuai.crawler.dal.query.RedisMemoryQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * RedisMemoryDAO
 *
 * @author wangshuai
 * @date 2021-11-11 18:05
 */
@Service
@Slf4j
public class RedisMemoryDAO {

    @Resource
    private RedisMemoryMapper mapper;

    public RedisMemoryQuery<RedisMemoryDO> pageQuery(RedisMemoryQuery<RedisMemoryDO> query) {
        int count = mapper.countByQuery(query);
        if (count != 0) {
            query.setTotalCount(count);
            List<RedisMemoryDO> list = mapper.pageQuery(query);
            query.setDataList(list);
        }
        return query;
    }

    public int countByQuery(RedisMemoryQuery<RedisMemoryDO> query) {
        return mapper.countByQuery(query);
    }

    public List<RedisMemoryDO> fullQuery(RedisMemoryQuery<RedisMemoryDO> query) {
        return mapper.fullQuery(query);
    }

    public void insert(RedisMemoryDO record) {
        if (record != null) {
            mapper.insert(record);
        }
    }

    public void batchInsert(List<RedisMemoryDO> records) {
        if (!CollectionUtils.isEmpty(records)) {
            mapper.batchInsert(records);
        }
    }

    public RedisMemoryDO findById(long id) {
        return mapper.findById(id);
    }

    public void update(RedisMemoryDO record) {
        if (record != null) {
            mapper.update(record);
        }
    }

    public void batchUpdateById(List<RedisMemoryDO> records) {
        if (!CollectionUtils.isEmpty(records)) {
            mapper.batchUpdateById(records);
        }
    }

    public void insertOrUpdate(RedisMemoryDO record) {
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
