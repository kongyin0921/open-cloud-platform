package com.ocp.common.security.token;

import lombok.Getter;

import java.io.Serializable;

/**
 * @author kong
 * @date 2021/07/31 16:38
 * blog: http://blog.kongyin.ltd
 */
@Getter
public class CustomWebAuthenticationDetails implements Serializable {
    private static final long serialVersionUID = - 1;

    private final String accountType;
    private final String remoteAddress;
    private final String sessionId;

    public CustomWebAuthenticationDetails(String remoteAddress, String sessionId, String accountType) {
        this.remoteAddress = remoteAddress;
        this.sessionId = sessionId;
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("; accountType: ").append(this.getAccountType());
        return sb.toString();
    }
}
