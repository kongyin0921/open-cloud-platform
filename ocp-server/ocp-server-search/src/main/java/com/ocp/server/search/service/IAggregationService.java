package com.ocp.server.search.service;

import java.io.IOException;
import java.util.Map;

/**
 * @author kong
 * @date 2021/08/29 11:45
 * blog: http://blog.kongyin.ltd
 */
public interface IAggregationService {

    /**
     * 访问统计聚合查询
     * @param indexName 索引名
     * @param routing es的路由
     * @return
     */
    Map<String, Object> requestStatAgg(String indexName, String routing) throws IOException;
}
