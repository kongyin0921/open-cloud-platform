package com.ocp.auth.entity.dto;

import com.ocp.auth.entity.Client;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author kong
 * @date 2021/08/12 22:32
 * blog: http://blog.kongyin.ltd
 */
@Data
public class ClientDto extends Client {
    private static final long serialVersionUID = 1475637288060027265L;

    private List<Long> permissionIds;

    private Set<Long> serviceIds;
}
