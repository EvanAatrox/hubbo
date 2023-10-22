package cn.hubbo.service.impl;

import cn.hubbo.dao.RoleDao;
import cn.hubbo.dao.UserDao;
import cn.hubbo.domain.dos.Role;
import cn.hubbo.domain.dos.User;
import cn.hubbo.service.IRoleService;
import cn.hubbo.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.service.impl
 * @date 2023/10/22 12:09
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {


    private UserDao userDao;

    private IRoleService roleService;

    private RoleDao roleDao;


    /**
     * @param id 用户id
     * @return 用户信息
     */
    @Override
    public Optional<User> queryById(Integer id) {
        return userDao.findById(id);
    }

    /**
     * @param user 保存的user对象
     * @return 持久化后的user对象
     */
    @Override
    public User save(User user) {
        user.setProfileUrl("http://img.zhaogexing.com/2020/01/30/1580360931159906.jpg");
        return userDao.save(user);
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public User queryUserByUsername(String username) {
        return userDao.queryByUsernameEquals(username);
    }

    /**
     * 更新用户的角色信息
     *
     * @param uid 用户id
     * @param rid 角色id
     * @return 为用户设置角色
     */
    @Override
    public User updateUserRole(Integer uid, Integer rid) {
        AtomicReference<User> reference = new AtomicReference<>(null);
        queryById(uid).ifPresentOrElse(user -> roleService.queryById(rid).ifPresent(role -> {
            if (!user.getRoles().stream().map(Role::getRoleId).toList().contains(role.getRoleId())) {
                user.getRoles().add(role);
                user.setUpdateTime(new Date());
                save(user);
            }
            reference.set(user);
        }), () -> System.out.println("nothing to do"));
        return reference.get();
    }


}
