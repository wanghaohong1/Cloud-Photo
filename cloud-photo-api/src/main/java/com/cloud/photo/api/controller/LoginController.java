package com.cloud.photo.api.controller;

import com.cloud.photo.api.service.LoginService;
import com.cloud.photo.common.bo.UserBo;
import com.cloud.photo.common.common.ResultBody;
import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * api控制器
 *
 * @author 11446
 */
@RestController
@Slf4j
@RequestMapping("/api")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public ResultBody login(@RequestBody UserBo bo) {
        return loginService.login(bo);
    }

    @SaCheckLogin
    @PostMapping("/logout")
    public ResultBody logout() {
        return loginService.logout();
    }

    @SaCheckLogin
    @GetMapping("/getUserInfo")
    public ResultBody getUserInfo(){
        return loginService.getUserInfo();
    }

}
