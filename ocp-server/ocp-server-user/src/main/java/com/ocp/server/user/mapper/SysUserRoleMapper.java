package com.ocp.server.user.mapper;


import com.ocp.common.entity.SysRole;
import com.ocp.common.mybatis.mapper.IBaseMapper;
import com.ocp.server.user.entity.SysRoleUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zlt
 * 用户角色关系
 */
@Mapper
public interface SysUserRoleMapper extends IBaseMapper<SysRoleUser> {
    int deleteUserRole(@Param("userId") String userId, @Param("roleId") String roleId);

    @Insert("insert into sys_role_user(user_id, role_id) values(#{userId}, #{roleId})")
    int saveUserRoles(@Param("userId") String userId, @Param("roleId") String roleId);

    /**
     * 根据用户id获取角色
     *
     * @param userId
     * @return
     */
    List<SysRole> findRolesByUserId(@Param("userId") String userId);

    /**
     * 根据用户ids 获取
     *
     * @param userIds
     * @return
     */
    @Select("<script>select r.*,ru.user_id from sys_role_user ru inner join sys_role r on r.id = ru.role_id where ru.user_id IN " +
            " <foreach item='item' index='index' collection='list' open='(' separator=',' close=')'> " +
            " #{item} " +
            " </foreach>" +
            "</script>")
    List<SysRole> findRolesByUserIds(List<Long> userIds);


}
