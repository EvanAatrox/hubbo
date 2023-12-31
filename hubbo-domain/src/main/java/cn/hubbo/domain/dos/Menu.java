package cn.hubbo.domain.dos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;

import java.util.Date;
import java.util.List;

/**
 * @author 张晓华
 * @date 2023-10-20 16:25
 * @usage 当前类的用途描述
 */

@Data
@Accessors(chain = true)
@Entity(name = "t_menu")
@Table(indexes = {@Index(name = "menu_name_index", columnList = "menu_name", unique = true)})
public class Menu {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id", columnDefinition = "smallint")
    @Comment("菜单编号")
    private Integer menuId;


    @Column(name = "menu_name", columnDefinition = "varchar(60)", nullable = false)
    @Comment("菜单名")
    private String menuName;


    @Column(columnDefinition = "varchar(128)", nullable = false, unique = true)
    @Comment("路由地址")
    private String path;


    @Column(columnDefinition = "varchar(128)", nullable = false)
    @Comment("组件地址")
    private String component;


    @Column(name = "permission_code", columnDefinition = "varchar(60)", nullable = false)
    @Comment("菜单对应的权限字符")
    private String permissionCode;


    @Column(columnDefinition = "smallint default 1", nullable = false)
    @Comment("菜单层级")
    private Integer level;


    @Column(columnDefinition = "bit(1) default 0")
    @Comment("菜单的启用状态")
    private boolean enabled;


    @Column(name = "creator_code", columnDefinition = "integer", nullable = false, updatable = false)
    @Comment("创建者编号")
    private Integer creatorCode;


    @Column(name = "delete_flag", columnDefinition = "bit(1) default 0", nullable = false)
    @Comment("逻辑删除的标志位")
    private boolean deleteFlag;


    @Column(name = "create_time", columnDefinition = "timestamp default current_timestamp()", updatable = false)
    @Comment("角色的创建时间")
    private Date creatTime;


    @Column(name = "update_time", columnDefinition = "timestamp")
    @Comment("最近一次的更新时间")
    private Date updateTime;


    @Column(name = "delete_time", columnDefinition = "timestamp", updatable = false)
    @Comment("删除时间")
    private Date deleteTime;


    @ManyToMany(mappedBy = "menus", fetch = FetchType.EAGER)
    private List<Role> roles;

}
