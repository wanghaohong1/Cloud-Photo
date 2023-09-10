package com.cloud.photo.user.mapper;

import com.cloud.photo.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linken
 * @since 2023-07-09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
