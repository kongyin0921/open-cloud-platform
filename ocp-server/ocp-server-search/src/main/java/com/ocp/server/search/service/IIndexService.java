package com.ocp.server.search.service;

import com.ocp.common.bean.PageInfo;
import com.ocp.server.search.entity.IndexDto;

import java.io.IOException;
import java.util.Map;

/**
 * 索引
 * @author kong
 * @date 2021/08/28 15:42
 * blog: http://blog.kongyin.ltd
 */
public interface IIndexService {
    /**
     * 创建索引
     */
    boolean create(IndexDto indexDto) throws IOException;

    /**
     * 删除索引
     * @param indexName 索引名
     */
    boolean delete(String indexName) throws IOException;

    /**
     * 索引列表
     * @param queryStr 搜索字符串
     * @param indices 默认显示的索引名
     */
    PageInfo<Map<String, String>> list(String queryStr, String indices) throws IOException;

    /**
     * 显示索引明细
     * @param indexName 索引名
     */
    Map<String, Object> show(String indexName) throws IOException;
}
