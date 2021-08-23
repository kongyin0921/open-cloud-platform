package com.ocp.common.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体 基础对象
 * @author kong
 * @date 2021/07/31 13:53
 * blog: http://blog.kongyin.ltd
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractEntity<T extends Model<?>> extends Model<T> {
    /**
     * 持久化对象id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人 系统默认自动维护
     */
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    /**
     * 创建时间 系统默认自动维护
     */
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改人 系统默认自动维护
     */
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    /**
     * 修改时间 系统默认自动维护
     */
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 时间戳 系统默认自动维护
     */
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastModify;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
