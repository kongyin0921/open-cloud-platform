package com.ocp.common.search.client.feign.fallback;

import cn.hutool.core.map.MapUtil;
import com.ocp.common.search.client.feign.AggregationService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kong
 * @date 2021/08/28 14:38
 * blog: http://blog.kongyin.ltd
 */
@Slf4j
public class AggregationServiceFallbackFactory implements FallbackFactory<AggregationService> {
    @Override
    public AggregationService create(Throwable throwable) {
        return (indexName, routing) -> {
            log.error("通过索引{}搜索异常:{}", indexName, throwable);
            return MapUtil.empty();
        };
    }
}
