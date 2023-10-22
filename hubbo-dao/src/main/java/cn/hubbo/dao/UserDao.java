package cn.hubbo.dao;

import cn.hubbo.domain.dos.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.dao
 * @date 2023/10/21 0:00
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */


public interface UserDao extends JpaRepository<User, Integer> {


    User queryByUsernameEquals(String username);


}
