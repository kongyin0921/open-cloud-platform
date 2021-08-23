package com.ocp.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ocp.common.bean.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户实体
 * @author kong
 * @date 2021/07/31 13:55
 * blog: http://blog.kongyin.ltd
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_user")
public class SysUser extends AbstractEntity<SysUser> {
    private static final long serialVersionUID = -5886012896705137070L;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 图片地址
     */
    private String headImgUrl;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 是否启用
     */
    private Boolean enabled;
    /**
     * 类型
     */
    private String type;
    /**
     * 第三方 id
     */
    private String openId;
    /**
     * 是否删除
     */
    @TableLogic
    private boolean isDel;

    @TableField(exist = false)
    private List<SysRole> roles;
    @TableField(exist = false)
    private String roleId;
    @TableField(exist = false)
    private String oldPassword;
    @TableField(exist = false)
    private String newPassword;
}
