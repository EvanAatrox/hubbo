package cn.hubbo.application;

import cn.hubbo.domain.dos.Role;
import cn.hubbo.domain.dos.User;
import cn.hubbo.domain.enumeration.GenderEnum;
import cn.hubbo.domain.enumeration.AccountStatusEnum;
import cn.hubbo.service.IRoleService;
import cn.hubbo.service.IUserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.application
 * @date 2023/10/22 12:18
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
@SpringBootTest
public class UserAboutServiceTest {

    @Autowired
    IUserService userService;

    @Autowired
    ApplicationContext applicationContext;

    @Resource
    IRoleService roleService;

    @Resource
    PasswordEncoder passwordEncoder;

    @Test
    public void testSaveUser() {
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println("beanName " + name);
        }
        User user = new User().setUsername("user1").setPassword(new BCryptPasswordEncoder().encode("123456")).setPhone("1234567890").setAccountStatus(AccountStatusEnum.DEFAULT).setGender(GenderEnum.MALE).setEmail("wantfulai@163.com").setRemark("系统用户").setRegisterDate(new Date());
        user = userService.save(user);
        System.out.println("保存后的用户信息 " + user);
    }


    @Test
    public void testQueryUser() {
        User user = userService.queryUserByUsername("user1");
        System.out.println(user);
    }

    @Test
    public void testSaveRole() {
        Role role = new Role().setCreatorCode(1).setRoleName("开发者").setCreatTime(new Date()).setDeleteFlag(false).setRoleStatus(true).setPermissionCode("dev.*.*");
        role = roleService.save(role);
        System.out.println(role);
    }


    @Test
    public void testQueryRoleInfo() {
        Optional<Role> optionalRole = roleService.queryById(1);
        optionalRole.ifPresent(role -> {
            System.out.println("角色信息 " + role);
        });
    }

    @Test
    public void testUpdateUserRoleInfo() {
        User user = userService.updateUserRole(1, 1);
        System.out.println("更新后的用户信息 " + user);
    }

    @Test
    public void testGeneratePassword() {
        String rawPassword = "就不告诉你密码";
        String encodingPassword = passwordEncoder.encode(rawPassword);
        System.out.println(encodingPassword);
        System.out.println(passwordEncoder.matches(rawPassword, encodingPassword));
    }


}
