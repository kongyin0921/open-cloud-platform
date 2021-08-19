package com.ocp.common.bean;

import java.util.List;

/**
 * 分页基础接口
 * @author kong
 * @date 2021/08/18 19:59
 * blog: http://blog.kongyin.ltd
 */
public interface IBasePageResult<T> extends IPage{
    /**
     * 获取页码
     *
     * @return 页码
     */
    int getPageIndex();

    /**
     * 获取每页显示数量
     *
     * @return 每页显示数量
     */
    int getPageSize();

    /**
     * 获取总条数
     *
     * @return 总条数
     */
    long getTotal();

    /**
     * 获取分页数据
     *
     * @return 分页数据
     */
    List<T> getData();

}
