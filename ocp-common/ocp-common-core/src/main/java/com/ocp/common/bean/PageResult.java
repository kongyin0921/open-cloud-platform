package com.ocp.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页实体类
 * @author kong
 * @date 2021/07/23 20:48
 * blog: http://blog.kongyin.ltd
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = -275582248840137389L;
    /**
     * 总数
     */
    private Long count;
    /**
     * 是否成功：0 成功、1 失败
     */
    private int code;
    /**
     * 当前页结果集
     */
    private List<T> data;

}
