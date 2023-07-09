package com.zsycx.user.service;

import com.zsycx.user.domain.User;

/**
 * @ClassName: UserService
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
public interface UserService {
    int login(User user);

    int registry(User user);
}
