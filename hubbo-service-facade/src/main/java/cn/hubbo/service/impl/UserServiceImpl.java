package cn.hubbo.service.impl;

import cn.hubbo.dao.RoleDao;
import cn.hubbo.dao.UserDao;
import cn.hubbo.domain.dos.Role;
import cn.hubbo.domain.dos.User;
import cn.hubbo.domain.enumeration.AccountStatusEnum;
import cn.hubbo.service.IRoleService;
import cn.hubbo.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
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
@Slf4j
public class UserServiceImpl implements IUserService {


    private UserDao userDao;

    private IRoleService roleService;

    private RoleDao roleDao;


    /**
     * @param id 用户id
     *
     * @return 用户信息
     */
    @Override
    public Optional<User> queryById(Integer id) {
        return userDao.findById(id);
    }

    /**
     * @param user 保存的user对象
     *
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
     *
     * @return 用户信息
     */
    @Override
    public User queryUserByUsername(String username) {
        User user = userDao.queryByUsernameEquals(username.trim());
        if (Objects.isNull(user)) {
            return null;
        }
        if (!Objects.isNull(user.getUpdateTime())) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(user.getUpdateTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            if (user.getAccountStatus().equals(AccountStatusEnum.LOCKED) && calendar.getTime().before(new Date())) {
                user.setAccountStatus(AccountStatusEnum.DEFAULT);
                user.setUpdateTime(new Date());
                log.info("解锁当前账户{}", user);
                return this.userDao.save(user);
            }
        }
        return user;
    }

    /**
     * 更新用户的角色信息
     *
     * @param uid 用户id
     * @param rid 角色id
     *
     * @return 为用户设置角色
     */
    @Override
    public User updateUserRole(Integer uid, Integer rid) {
        AtomicReference<User> reference = new AtomicReference<>(null);
        queryById(uid).ifPresent(user -> roleService.queryById(rid).ifPresent(role -> {
            if (!user.getRoles().stream().map(Role::getRoleId).toList().contains(role.getRoleId())) {
                user.getRoles().add(role);
                user.setUpdateTime(new Date());
                save(user);
            }
            reference.set(user);
        }));
        return reference.get();
    }


    /**
     * 锁定账户
     *
     * @param user 用户信息
     *
     * @return 修改后的用户信息
     */
    @Override
    public User lockAccount(User user) {
        user.setAccountStatus(AccountStatusEnum.LOCKED);
        user.setUpdateTime(new Date());
        return userDao.save(user);
    }


    /**
     * @param user 需要更新的用户信息
     *
     * @return 更新后的用户信息
     */
    @Override
    public User updateUserInfo(User user) {
        Assert.notNull(user, "更新的对象不允许为空");
        Assert.notNull(user.getUserId(), "需要更新的对象的ID不允许为空" + user);
        return save(user);
    }
    

}
