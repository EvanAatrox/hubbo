package cn.hubbo.application;

import cn.hubbo.domain.dos.Role;
import cn.hubbo.domain.dos.User;
import cn.hubbo.domain.enumeration.AccountStatusEnum;
import cn.hubbo.domain.enumeration.GenderEnum;
import cn.hubbo.service.IRoleService;
import cn.hubbo.service.IUserService;
import cn.hubbo.utils.annotation.test.TestCase;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    @TestCase("保存用户测试")
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
    @TestCase("查询测试")
    public void testQueryUser() {
        User user = userService.queryUserByUsername("user1");
        System.out.println(user);
    }

    @Test
    @TestCase("角色保存测试")
    public void testSaveRole() {
        Role role = new Role().setCreatorCode(1).setRoleName("dev").setCreatTime(new Date()).setDeleteFlag(false).setRoleStatus(true).setPermissionCode("dev.*.*");
        role = roleService.save(role);
        System.out.println(role);
    }


    @Test
    @TestCase("查询角色信息测试")
    public void testQueryRoleInfo() {
        Optional<Role> optionalRole = roleService.queryById(1);
        optionalRole.ifPresent(role -> {
            System.out.println("角色信息 " + role);
        });
    }

    @Test
    @TestCase("更新角色信息测试")
    public void testUpdateUserRoleInfo() {
        User user = userService.updateUserRole(1, 1);
        System.out.println("更新后的用户信息 " + user);
    }

    @Test
    @TestCase("Spring Security密码生成测试")
    public void testGeneratePassword() {
        String rawPassword = "就不告诉你密码";
        String encodingPassword = passwordEncoder.encode(rawPassword);
        System.out.println(encodingPassword);
        System.out.println(passwordEncoder.matches(rawPassword, encodingPassword));
    }


    @Test
    @TestCase("更新用户角色")
    public void testUpdateUserRole() {
        Integer uid = 1;
        Integer rid = 1;
        userService.queryById(uid)
                .ifPresent(user -> {
                    List<Role> roles = user.getRoles();
                    if (CollectionUtils.isEmpty(user.getRoles())) {
                        roles = new ArrayList<>();
                    }
                    roleService.queryById(rid).ifPresent(roles::add);
                    user.setRoles(roles);
                    userService.updateUserInfo(user);
                });
    }


}
