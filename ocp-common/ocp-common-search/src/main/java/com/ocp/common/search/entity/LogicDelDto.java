package com.ocp.common.search.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 逻辑删除条件对象
 * @author kong
 * @date 2021/08/28 14:18
 * blog: http://blog.kongyin.ltd
 */
@Data
@AllArgsConstructor
public class LogicDelDto {
    /**
     * 逻辑删除字段名
     */
    private String logicDelField;
    /**
     * 逻辑删除字段未删除的值
     */
    private String logicNotDelValue;

}
