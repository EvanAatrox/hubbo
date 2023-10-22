package cn.hubbo.dao;

import cn.hubbo.domain.dos.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.dao
 * @date 2023/10/22 12:15
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {


}
