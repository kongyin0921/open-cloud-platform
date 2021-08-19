package com.ocp.common.bean;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.cglib.core.internal.Function;

import java.util.List;
import java.util.stream.Collectors;

/**
 * mybatis分页查询对象
 * @author kong
 * @date 2021/08/18 20:06
 * blog: http://blog.kongyin.ltd
 */
public class SimplePage extends Page implements IPage {

    public SimplePage() {
    }

    public SimplePage(long current, long size) {
        super(current, size);
    }

    public SimplePage(long current, long size, long total) {
        super(current, size, total);
    }

    public SimplePage(long current, long size, boolean isSearchCount) {
        super(current, size, isSearchCount);
    }

    public SimplePage(long current, long size, long total, boolean isSearchCount) {
        super(current, size, total, isSearchCount);
    }

    public SimplePage(AbstractPageQuery pageQuery) {
        super(pageQuery.getPageIndex(), pageQuery.getPageSize());
    }

    /**
     * 根据分页参数获取mybatis分页查询对象
     *
     * @param pageQuery 分页查询对象
     * @return mybatis plus 分页查询对象
     */
    public static Page build(AbstractPageQuery pageQuery) {
        return new SimplePage(pageQuery);
    }


    /**
     * mybatis plus 分页结果对象转为系统分页统一分页对象。
     * <p>结果对象只转换页码、页大小和总数，<b>没有分页的数据</b></p>
     *
     * @param page mybatis分页结果对象
     * @param <R>  分页结果类型
     * @return 系统分页统一分页对象
     */
    public static <R> PageInfo<R> toPageResult(IPage<R> page) {
        PageInfo<R> pageInfo = new PageInfo<>((int) page.getCurrent(), (int) page.getSize());
        pageInfo.setTotal(page.getTotal());
        pageInfo.setData(page.getRecords());
        return pageInfo;
    }

    /**
     * mybatis plus 分页结果对象转为系统分页统一分页对象。
     * 页面的数据通过传入的转换对象转换。
     *
     * @param page     mybatis分页结果对象
     * @param function 转换对象
     * @param <T>      源数据对象类型
     * @param <R>      转换结果对象类型
     * @return 分页结果对象
     */
    public static <T, R> PageInfo<R> toPageResult(IPage<T> page, Function<T, R> function) {
        toPageResult(page);
        PageInfo<R> pageInfo = new PageInfo<>((int) page.getCurrent(), (int) page.getSize());
        List<R> data = page.getRecords().stream().map(function::apply).collect(Collectors.toList());
        pageInfo.setData(data);
        pageInfo.setTotal(page.getTotal());

        return pageInfo;
    }
}
