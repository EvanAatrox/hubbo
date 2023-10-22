package cn.hubbo.domain.dos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Comment;

import java.util.Date;

/**
 * @author 张晓华
 * @date 2023-10-20 09:18
 * @usage 当前类的用途描述
 */
@Data
@Entity(name = "t_system_code")
@Table(indexes = {@Index(name = "code_index", columnList = "code", unique = true)})
public class SystemResponseCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "smallint")
    @Comment("主键")
    private Integer codeId;

    @Column(columnDefinition = "smallint", nullable = false, unique = true)
    @Comment("系统响应的状态码")
    private Integer code;

    @Column(columnDefinition = "varchar(100)")
    @Comment("响应的描述信息")
    private String msg;


    @Column(columnDefinition = "integer", nullable = false)
    @Comment("创建人")
    private Integer creatorCode;

    @Column(columnDefinition = "timestamp(6)", nullable = false, updatable = false)
    @Comment("创建时间")
    private Date createTime;


    @Column(columnDefinition = "timestamp(6)", nullable = false)
    @Comment("最近一次的更新时间")
    private Date updateTime;


    @Column(columnDefinition = "bit(1) default 0", nullable = false)
    @Comment("逻辑删除标志位")
    private boolean deleteFlag;


    @Column(columnDefinition = "bit(1) default 0", nullable = false)
    @Comment("是否启用")
    private boolean enabled;

}
