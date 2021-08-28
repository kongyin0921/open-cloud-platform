package com.ocp.common.search.client.service.Impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.ocp.common.bean.PageInfo;
import com.ocp.common.search.client.feign.AggregationService;
import com.ocp.common.search.client.feign.SearchService;
import com.ocp.common.search.client.service.IQueryService;
import com.ocp.common.search.entity.LogicDelDto;
import com.ocp.common.search.entity.SearchDto;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 搜索客户端Service
 * @author kong
 * @date 2021/08/28 14:28
 * blog: http://blog.kongyin.ltd
 */
public class QueryServiceImpl implements IQueryService {
    @Resource
    private SearchService searchService;

    @Resource
    private AggregationService aggregationService;

    @Override
    public PageInfo<JsonNode> strQuery(String indexName, SearchDto searchDto) {
        return null;
    }

    @Override
    public PageInfo<JsonNode> strQuery(String indexName, SearchDto searchDto, LogicDelDto logicDelDto) {
        return null;
    }

    @Override
    public Map<String, Object> requestStatAgg(String indexName, String routing) {
        return null;
    }
}
