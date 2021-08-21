package com.ocp.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kong
 * @date 2021/08/21 18:15
 * blog: http://blog.kongyin.ltd
 */
@Controller
public class SecurityController {

    @RequestMapping("login")
    public String login() {
        return "login";
    }
}
