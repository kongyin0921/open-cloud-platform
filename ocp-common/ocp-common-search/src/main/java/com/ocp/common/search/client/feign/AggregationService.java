package com.ocp.common.search.client.feign;

import com.ocp.common.constant.ServiceNameConstants;
import com.ocp.common.search.client.feign.fallback.AggregationServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author kong
 * @date 2021/08/28 14:35
 * blog: http://blog.kongyin.ltd
 */
@FeignClient(name = ServiceNameConstants.SEARCH_SERVICE, fallbackFactory = AggregationServiceFallbackFactory.class, decode404 = true)
public interface AggregationService {

    /**
     * 查询文档列表
     * @param indexName 索引名
     * @param routing es的路由
     */
    @GetMapping(value = "/agg/requestStat/{indexName}/{routing}")
    Map<String, Object> requestStatAgg(@PathVariable("indexName") String indexName, @PathVariable("routing") String routing);
}
