package com.ocp.auth.service;

import com.ocp.auth.entity.Client;
import com.ocp.auth.entity.dto.ClientQueryPageDto;
import com.ocp.common.bean.ISuperService;
import com.ocp.common.bean.PageInfo;

/**
 * @author kong
 * @date 2021/08/12 21:49
 * blog: http://blog.kongyin.ltd
 */
public interface IClientService extends ISuperService<Client> {
    /**
     * 查询应用列表
     * @param pageDto
     */
    PageInfo<Client> listClient(ClientQueryPageDto pageDto);

    /**
     * 保存应用
     * @param client
     */
    void saveClient(Client client) throws Exception;

    /**
     * 按客户端 ID 加载客户端
     * @param clientId
     * @return
     */
    Client loadClientByClientId(String clientId);

    /**
     * 删除客户端
     * @param id
     */
    void delClient(Long id);
}
