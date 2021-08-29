package com.ocp.server.search.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ocp.common.bean.PageInfo;
import com.ocp.common.search.entity.SearchDto;

import java.io.IOException;

/**
 * @author kong
 * @date 2021/08/29 11:45
 * blog: http://blog.kongyin.ltd
 */
public interface ISearchService {
    /**
     * StringQuery通用搜索
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     * @return
     */
    PageInfo<JsonNode> strQuery(String indexName, SearchDto searchDto) throws IOException;
}
