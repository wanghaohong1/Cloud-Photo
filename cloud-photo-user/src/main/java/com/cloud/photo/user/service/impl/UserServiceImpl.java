package com.cloud.photo.user.service.impl;

import com.cloud.photo.user.entity.User;
import com.cloud.photo.user.mapper.UserMapper;
import com.cloud.photo.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linken
 * @since 2023-07-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
