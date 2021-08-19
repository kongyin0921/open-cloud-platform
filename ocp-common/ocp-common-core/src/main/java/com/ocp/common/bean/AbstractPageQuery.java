package com.ocp.common.bean;

/**
 * 分页查询对象
 * @author kong
 * @date 2021/08/18 20:02
 * blog: http://blog.kongyin.ltd
 */
public abstract class AbstractPageQuery  implements IPage{

    /**
     * 当前页
     */
    private int pageIndex = 1;

    /**
     * 每页显示数量
     */
    private int pageSize = 20;

    /**
     * 排序+升序-降序</br>
     * 例如：order=+price按照商品价格升序排; </br>
     * order=-price 按照商品价格降序排。
     */
    private String order;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
