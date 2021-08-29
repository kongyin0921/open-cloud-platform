package com.ocp.server.search.service.Impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.ocp.common.bean.PageInfo;
import com.ocp.common.es.utils.SearchBuilder;
import com.ocp.common.search.entity.SearchDto;
import com.ocp.server.search.service.ISearchService;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 通用搜索
 * @author kong
 * @date 2021/08/29 11:46
 * blog: http://blog.kongyin.ltd
 */
@Service
public class SearchServiceImpl implements ISearchService {

    private final RestHighLevelClient client;

    public SearchServiceImpl(RestHighLevelClient client) {
        this.client = client;
    }

    /**
     * StringQuery通用搜索
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     * @return
     */
    @Override
    public PageInfo<JsonNode> strQuery(String indexName, SearchDto searchDto) throws IOException {
        return SearchBuilder.builder(client, indexName)
                .setStringQuery(searchDto.getQueryStr())
                .addSort(searchDto.getSortCol(), SortOrder.DESC)
                .setIsHighlight(searchDto.getIsHighlighter())
                .getPage(searchDto.getPage(), searchDto.getLimit());
    }
}
