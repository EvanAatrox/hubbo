package cn.hubbo.service;

import cn.hubbo.domain.dos.Role;

import java.util.Optional;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.service
 * @date 2023/10/22 12:16
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */


public interface IRoleService {

    /**
     * @param role 角色信息
     * @return 保存的角色信息
     */

    Role save(Role role);


    /**
     * 根据编号查询角色信息
     *
     * @param id 角色id
     * @return role信息
     */
    Optional<Role> queryById(Integer id);


}
