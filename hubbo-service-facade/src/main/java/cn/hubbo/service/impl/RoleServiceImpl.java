package cn.hubbo.service.impl;

import cn.hubbo.dao.RoleDao;
import cn.hubbo.dao.UserDao;
import cn.hubbo.domain.dos.Role;
import cn.hubbo.service.IRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.service.impl
 * @date 2023/10/22 12:17
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private RoleDao roleDao;

    private UserDao userDao;

    /**
     * @param role 角色信息
     * @return 保存的角色信息
     */
    @Override
    public Role save(Role role) {
        return roleDao.save(role);
    }

    /**
     * 根据编号查询角色信息
     *
     * @param id 角色id
     * @return role信息
     */
    @Override
    public Optional<Role> queryById(Integer id) {
        return roleDao.findById(id);
    }


}
