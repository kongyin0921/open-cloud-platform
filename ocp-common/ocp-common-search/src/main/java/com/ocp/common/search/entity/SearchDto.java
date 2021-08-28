package com.ocp.common.search.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 搜索参数
 * @author kong
 * @date 2021/08/28 14:21
 * blog: http://blog.kongyin.ltd
 */
@Data
public class SearchDto implements Serializable {
    private static final long serialVersionUID = -2084416068307485742L;
    /**
     * 搜索关键字
     */
    private String queryStr;
    /**
     * 当前页数
     */
    private Integer page;
    /**
     * 每页显示数
     */
    private Integer limit;
    /**
     * 排序字段
     */
    private String sortCol;
    /**
     * 是否显示高亮
     */
    private Boolean isHighlighter;
    /**
     * es的路由
     */
    private String routing;
}
