package com.ocp.common.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * mapper 父类，注意这个类不要让 mp 扫描到！！
 * @author kong
 * @date 2021/08/08 10:45
 * blog: http://blog.kongyin.ltd
 */
public interface IBaseMapper<T> extends BaseMapper<T> {
    // 这里可以放一些公共的方法
}
