package com.ocp.common.bean;

import lombok.Builder;

import java.io.Serializable;
import java.util.List;

/**
 * 分页信息
 * @author kong
 * @date 2021/07/23 20:48
 * blog: http://blog.kongyin.ltd
 */
@Builder
public class PageInfo<T> implements IBasePageResult<T>, Serializable {

    private static final long serialVersionUID = -275582248840137389L;
    /**
     * 当前页
     */
    private int pageIndex;
    /**
     * 每页显示数量
     */
    private int pageSize;
    /**
     * 总数
     */
    private long total;

    /**
     * 分页数据
     */
    private List<T> data;

    public PageInfo() {
    }

    public PageInfo(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public PageInfo(int pageIndex, int pageSize, long total, List<T> data) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.total = total;
        this.data = data;
    }


    @Override
    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
