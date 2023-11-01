package cn.hubbo.domain.dos;

import cn.hubbo.domain.enumeration.AccountStatusEnum;
import cn.hubbo.domain.enumeration.GenderEnum;
import cn.hubbo.utils.common.annotation.json.Ignore;
import cn.hubbo.utils.common.json.GsonEntity;
import com.google.common.collect.Sets;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.domain.entity
 * @date 2023/10/18 23:07
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
@Data
@Accessors(chain = true)
@Entity(name = "t_user")
@Table(indexes = {@Index(name = "user_name_index", columnList = "user_name", unique = true),
        @Index(name = "phone_index", columnList = "phone", unique = true)})
public class User extends GsonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", columnDefinition = "integer", updatable = false)
    @Comment("用户ID,不对外暴露")
    @Ignore
    private Integer userId;

    @Column(name = "user_name", columnDefinition = "varchar(60) ", nullable = false)
    @Comment("用户名,允许用户使用用户名进行登录")
    private String username;

    @Column(columnDefinition = "char(11)", nullable = false)
    @Comment("用户手机号,允许使用手机号进行登录操作")
    private String phone;

    @Column(columnDefinition = "char(60)", nullable = false)
    @Comment("用户密码,固定60位")
    @Ignore
    private String password;


    @Column(columnDefinition = "varchar(30)", nullable = false, unique = true)
    @Comment("用户邮箱")
    private String email;

    @Column(name = "register_date", columnDefinition = "timestamp default current_timestamp()", nullable = false)
    @Comment("注册日期")
    private Date registerDate;

    @Column(columnDefinition = "bit(1) default 1")
    @Comment("性别,1男,0女")
    private GenderEnum gender;

    @Column(name = "profile_url", columnDefinition = "varchar(255) default 'http://img.zhaogexing.com/2020/01/30/1580360931159906.jpg'")
    @Comment("头像地址")
    private String profileUrl;

    @Column(columnDefinition = "varchar(255)")
    @Comment("对用户的备注信息")
    private String remark;

    // Spring Security相关的状态位

    @Column(name = "account_status", columnDefinition = "bit(1) default 1")
    @Comment("用户账户状态,0锁定,1正常")
    private AccountStatusEnum accountStatus;


    @Column(name = "update_time", columnDefinition = "timestamp")
    @Comment("最近一次的更新时间")
    private Date updateTime;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_user_role",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "role_id", nullable = false)}
    )
    @Ignore
    private List<Role> roles;


    @Transient
    private Set<String> permissionCodes;


    public void setRoles(List<Role> roles) {
        getPermissionCodes();
        this.roles = roles;
    }

    public Set<String> getPermissionCodes() {
        if (!Objects.isNull(this.permissionCodes)) {
            return this.permissionCodes;
        }
        if (CollectionUtils.isEmpty(roles)) {
            return Sets.newHashSet();
        }
        Set<String> permissionCodes = roles.stream().map(Role::getPermissionCode).collect(Collectors.toSet());
        this.permissionCodes = permissionCodes;
        return permissionCodes;
    }


}
