package com.cloud.photo.user.controller;

import com.cloud.photo.common.bo.UserBo;
import com.cloud.photo.common.common.CommonEnum;
import com.cloud.photo.common.common.ResultBody;
import com.cloud.photo.common.constant.CommonConstant;
import com.cloud.photo.user.entity.User;
import com.cloud.photo.user.service.impl.UserServiceImpl;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author linken
 * @since 2023-07-09
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserServiceImpl userService;

    @GetMapping("/getUserInfo")
    public ResultBody getUserInfo(@RequestParam(value = "phone") String phone) {
        log.info("getUserInfo()-phone=" + phone + ",start!");
        User user = userService.getOne(new QueryWrapper<User>().eq("phone", phone));
        log.info("getUserInfo()-phone=" + phone + ",user=" + user);
        ResultBody resultBody = (user == null ? ResultBody.error(CommonEnum.USER_IS_NULL) : ResultBody.success(user));
        log.info("getUserInfo()-phone=" + phone + ",resultBody=" + resultBody);
        return resultBody;
    }

    @GetMapping("/checkPhone")
    public ResultBody checkPhone(@RequestParam(value = "phone") String phone) {
        User userEntity = userService.getOne(new QueryWrapper<User>().eq("phone", phone));
        return userEntity == null ? ResultBody.error(CommonEnum.USER_IS_NULL) : ResultBody.success();
    }

    @GetMapping("/checkAdmin")
    public ResultBody checkAdmin(@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password) {
        User admin = userService.getOne(new QueryWrapper<User>()
                .eq("user_name", userName)
                .eq("password", password)
                .eq("role", CommonConstant.ADMIN));
        return admin == null ? ResultBody.error(CommonEnum.USERNAME_PASSWORD_ERROR) : ResultBody.success();
    }

    @PostMapping("/login")
    public ResultBody login(@RequestBody UserBo bo) {
        String phone = bo.getPhone();
        //看看有没有这个用户
        User user = userService.getOne(new QueryWrapper<User>().eq("phone", phone));
        if (user == null) {
            //没有就新增这个用户信息
            user = new User();
            //复制
            BeanUtils.copyProperties(bo, user);
            //自定义一个用户ID
            user.setUserId(RandomUtil.randomString(9));
            user.setCreateTime(DateUtil.date());
            user.setUpdateTime(DateUtil.date());
            user.setLoginCount(0);
            //普通用户
            user.setRole("user");

        } else {
            //有这个用户就更新下登录信息
            user.setLoginCount(user.getLoginCount() + 1);
            user.setUpdateTime(DateUtil.date());
        }
        //更新信息入库
        boolean saveOrUpdate = userService.saveOrUpdate(user);
        return saveOrUpdate ? ResultBody.success() : ResultBody.error(CommonEnum.LOGIN_FAIL);
    }

}
