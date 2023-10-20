package cn.hubbo.domain.dos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;

import java.util.Date;
import java.util.List;

/**
 * @author 张晓华
 * @date 2023-10-20 15:58
 * @usage RBAC模型基础类
 */
@Entity(name = "t_role")
@Data
@Accessors(chain = true)
public class Role {

    @Id
    @Column(name = "role_id", columnDefinition = "smallint primary key auto_increment")
    @Comment("角色ID")
    private Integer roleId;


    @Column(name = "role_name", columnDefinition = "varchar(60)", nullable = false, unique = true)
    @Comment("角色名")
    private String roleName;


    @Column(name = "permission_code", columnDefinition = "varchar(60)", nullable = false, unique = true)
    @Comment("角色拥有的权限字符串，系统的权限字符由三部分组成，一级资源分类.具体资源名.要进行的操作 三部分组成")
    private String permissionCode;

    @Column(name = "role_status", columnDefinition = "bit(1) default 0")
    @Comment("角色状态")
    private boolean roleStatus;


    @Column(name = "delete_flag", columnDefinition = "bit(1) default 0", nullable = false)
    @Comment("逻辑删除的标志位")
    private boolean deleteFlag;


    @Column(name = "creator_code", columnDefinition = "integer", nullable = false, updatable = false)
    @Comment("创建者编号")
    private Integer creatorCode;

    @Column(name = "create_time", columnDefinition = "timestamp(6) default systimestamp()", updatable = false)
    @Comment("角色的创建时间")
    private Date creatTime;


    @Column(name = "update_time", columnDefinition = "timestamp(6)")
    @Comment("最近一次的更新时间")
    private Date updateTime;


    @Column(name = "delete_time", columnDefinition = "timestamp(6)", updatable = false)
    @Comment("删除时间")
    private Date deleteTime;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany(targetEntity = Menu.class)
    @JoinTable(name = "t_role_menu",
            joinColumns = {@JoinColumn(name = "role_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "menu_id", nullable = false)}
    )
    private List<Menu> menus;

}
