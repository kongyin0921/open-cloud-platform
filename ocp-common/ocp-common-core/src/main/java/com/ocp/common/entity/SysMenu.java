package com.ocp.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ocp.common.bean.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

/**
 * 菜单
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_menu")
public class SysMenu extends AbstractEntity<SysMenu> {
	private static final long serialVersionUID = 749360940290141180L;

	private Long parentId;
	private String name;
	private String css;
	private String url;
	private String path;
	private Integer sort;
	private Integer type;
	private Boolean hidden;
	/**
	 * 请求的类型
	 */
	private String pathMethod;

	@TableField(exist = false)
	private List<SysMenu> subMenus;
	@TableField(exist = false)
	private Long roleId;
	@TableField(exist = false)
	private Set<Long> menuIds;
}
