package cn.hubbo.domain.entity;

import cn.hubbo.domain.enumeration.GenderEnum;
import cn.hubbo.domain.enumeration.UserStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;

import java.util.Date;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.domain.entity
 * @date 2023/10/18 23:07
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @Column(columnDefinition = "integer primary key auto_increment", updatable = false)
    @Comment("用户ID,不对外暴露")
    private Integer userId;

    @Column(columnDefinition = "varchar(60) ", nullable = false, unique = true)
    @Comment("用户名,允许用户使用用户名进行登录")
    private String username;

    @Column(columnDefinition = "char(11)", unique = true)
    @Comment("用户手机号,允许使用手机号进行登录操作")
    private String phone;

    @Column(columnDefinition = "char(60)", nullable = false)
    @Comment("用户密码,固定60位")
    private String password;


    @Column(columnDefinition = "varchar(30)", unique = true)
    @Comment("用户邮箱")
    private String email;

    @Column(columnDefinition = "timestamp(6)", nullable = false)
    @Comment("注册日期")
    private Date registerDate;

    @Column(columnDefinition = "smallint default 1")
    @Comment("性别,1男,0女")
    private GenderEnum gender;

    @Column(columnDefinition = "varchar(255) default 'http://img.zhaogexing.com/2020/01/30/1580360931159906.jpg'")
    @Comment("头像地址")
    private String profileUrl;

    @Column(columnDefinition = "varchar(255)")
    @Comment("对用户的备注信息")
    private String remark;

    // Spring Security相关的状态位

    @Column(columnDefinition = "smallint default 0")
    @Comment("用户账户状态,0正常,1锁定")
    private UserStatusEnum accountStatus;


}
