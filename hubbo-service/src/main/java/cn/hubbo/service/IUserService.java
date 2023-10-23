package cn.hubbo.service;

import cn.hubbo.domain.dos.User;

import java.util.Optional;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.service
 * @date 2023/10/22 12:05
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */


public interface IUserService {


    /**
     * @param id 用户id
     *
     * @return 用户信息
     */
    Optional<User> queryById(Integer id);


    /**
     * @param user 保存的user对象
     *
     * @return 持久化后的user对象
     */
    User save(User user);


    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     *
     * @return 用户信息
     */
    User queryUserByUsername(String username);

    /**
     * 更新用户的角色信息
     *
     * @param uid 用户id
     * @param rid 角色id
     *
     * @return 为用户设置角色
     */
    User updateUserRole(Integer uid, Integer rid);


    /**
     * 锁定账户
     *
     * @param user 用户信息
     *
     * @return 修改后的用户信息
     */
    User lockAccount(User user);

}
