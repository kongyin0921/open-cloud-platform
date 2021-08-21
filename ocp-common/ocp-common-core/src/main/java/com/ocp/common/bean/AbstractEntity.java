package com.ocp.common.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体父类
 * @author kong
 * @date 2021/07/31 13:53
 * blog: http://blog.kongyin.ltd
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractEntity<T extends Model<?>> extends Model<T> {
    /**
     * 主键ID
     */
    @TableId
    private Long id;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
