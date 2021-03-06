package com.ocp.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ocp.common.bean.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色
 * @author kong
 * @date 2021/07/31 13:57
 * blog: http://blog.kongyin.ltd
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_role")
public class SysRole extends AbstractEntity<SysRole> {
    private static final long serialVersionUID = 4497149010220586111L;
    private String code;
    private String name;
    @TableField(exist = false)
    private Long userId;
}
