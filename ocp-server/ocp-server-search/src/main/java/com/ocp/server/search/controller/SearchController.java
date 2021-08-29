package com.ocp.server.search.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.ocp.common.bean.PageInfo;
import com.ocp.common.search.entity.SearchDto;
import com.ocp.server.search.service.ISearchService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 通用搜索
 * @author kong
 * @date 2021/08/29 11:54
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
@RestController
@Api(tags = "搜索模块api")
@RequestMapping("/search")
public class SearchController {

    private final ISearchService searchService;

    public SearchController(ISearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * 查询文档列表
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     */
    @PostMapping("/{indexName}")
    public PageInfo<JsonNode> strQuery(@PathVariable String indexName, @RequestBody(required = false) SearchDto searchDto) throws IOException {
        if (searchDto == null) {
            searchDto = new SearchDto();
        }
        return searchService.strQuery(indexName, searchDto);
    }
}
