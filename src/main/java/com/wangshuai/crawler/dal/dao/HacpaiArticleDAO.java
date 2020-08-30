package com.wangshuai.crawler.dal.dao;

import com.wangshuai.crawler.dal.dataobject.HacpaiArticleDO;
import com.wangshuai.crawler.dal.mapper.HacpaiArticleMapper;
import com.wangshuai.crawler.dal.query.HacpaiArticleQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * HacpaiArticleDAO
 *
 * @author wangshuai
 * @date 2020-08-30 22:38
 */
@Service
@Slf4j
public class HacpaiArticleDAO {

    @Resource
    private HacpaiArticleMapper mapper;

    public HacpaiArticleQuery<HacpaiArticleDO> pageQuery(HacpaiArticleQuery<HacpaiArticleDO> query) {
        int count = mapper.countByQuery(query);
        if (count != 0) {
            query.setTotalCount(count);
            List<HacpaiArticleDO> list = mapper.pageQuery(query);
            query.setDataList(list);
        }
        return query;
    }

    public int countByQuery(HacpaiArticleQuery<HacpaiArticleDO> query) {
        return mapper.countByQuery(query);
    }

    public List<HacpaiArticleDO> fullQuery(HacpaiArticleQuery<HacpaiArticleDO> query) {
        return mapper.fullQuery(query);
    }

    public void insert(HacpaiArticleDO record) {
        if (record != null) {
            mapper.insert(record);
        }
    }

    public void batchInsert(List<HacpaiArticleDO> records) {
        if (!CollectionUtils.isEmpty(records)) {
            mapper.batchInsert(records);
        }
    }

    public HacpaiArticleDO findById(long id) {
        return mapper.findById(id);
    }

    public void update(HacpaiArticleDO record) {
        if (record != null) {
            mapper.update(record);
        }
    }

    public void batchUpdateById(List<HacpaiArticleDO> records) {
        if (!CollectionUtils.isEmpty(records)) {
            mapper.batchUpdateById(records);
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
