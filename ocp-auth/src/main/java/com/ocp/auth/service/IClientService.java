package com.ocp.auth.service;

import com.ocp.auth.entity.Client;
import com.ocp.common.bean.ISuperService;
import com.ocp.common.bean.PageResult;

import java.util.Map;

/**
 * @author kong
 * @date 2021/08/12 21:49
 * blog: http://blog.kongyin.ltd
 */
public interface IClientService extends ISuperService<Client> {
    /**
     * 查询应用列表
     * @param params
     * @param isPage 是否分页
     */
    PageResult<Client> listClient(Map<String, Object> params, boolean isPage);

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
}
