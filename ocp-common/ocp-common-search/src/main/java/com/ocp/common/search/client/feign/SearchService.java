package com.ocp.common.search.client.feign;

import com.fasterxml.jackson.databind.JsonNode;
import com.ocp.common.bean.PageInfo;
import com.ocp.common.constant.ServiceNameConstants;
import com.ocp.common.search.client.feign.fallback.SearchServiceFallbackFactory;
import com.ocp.common.search.entity.SearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author kong
 * @date 2021/08/28 14:33
 * blog: http://blog.kongyin.ltd
 */
@FeignClient(name = ServiceNameConstants.SEARCH_SERVICE, fallbackFactory = SearchServiceFallbackFactory.class, decode404 = true)
public interface SearchService {
    /**
     * 查询文档列表
     * @param indexName 索引名
     * @param searchDto 搜索Dto
     */
    @PostMapping(value = "/search/{indexName}")
    PageInfo<JsonNode> strQuery(@PathVariable("indexName") String indexName, @RequestBody SearchDto searchDto);
}
