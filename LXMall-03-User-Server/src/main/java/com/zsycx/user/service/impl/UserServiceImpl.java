package com.zsycx.user.service.impl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.zsycx.commons.Code;
import com.zsycx.commons.JsonResult;
import com.zsycx.user.domain.User;
import com.zsycx.user.mapper.UserMapper;
import com.zsycx.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

/**
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int login(User user) {
        // 通过 Mapper 查询用户信息
        User loginUser = userMapper.selectByPhone(user.getPhone());

        System.out.println("UserService-loginUser:" + loginUser);

        /**
         * 判断一：判断用户是否为空，为空则表示用户不存在。返回 1
         * 判断二：判断用户密码是否与前端发送的信息相同，不相同表示密码错误。返回 2
         * 登录成功。返回 0
         */
        if (loginUser == null) {
            return 1;
        } else if (!loginUser.getPassword().equals(user.getPassword())) {
            return 2;
        }

        // 通过 BeanUtils 类中的方法将从数据库中获取到的完整信息，复制到 user 对象中，以便将用户信息存入 Redis
        BeanUtils.copyProperties(loginUser, user);
        return 0;
    }

    @Override
    public int registry(User user) {
        System.out.println("UserServiceImpl-registry1:" + user);
        // 将插入语句放入 try catch 语句块中，如果出现手机号码唯一索引存在的情况则会返回 1，表示用户已经存在
        try {
            userMapper.insertSelective(user);
        } catch (DuplicateKeyException e) {
            System.out.println(e.getMessage());
            return 1;
        }
        System.out.println("UserServiceImpl-registry2:" + user);
        // 注册成功，返回 0
        return 0;
    }
}
