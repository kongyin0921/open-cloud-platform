package com.ocp.server.search.entity;

import lombok.Data;

/**
 * @author kong
 * @date 2021/08/28 15:39
 * blog: http://blog.kongyin.ltd
 */
@Data
public class IndexDto {
    /**
     * 索引名
     */
    private String indexName;
    /**
     * 分片数 number_of_shards
     */
    private Integer numberOfShards;
    /**
     * 副本数 number_of_replicas
     */
    private Integer numberOfReplicas;
    /**
     * 类型
     */
    private String type;
    /**
     * mappings内容
     */
    private String mappingsSource;
}
