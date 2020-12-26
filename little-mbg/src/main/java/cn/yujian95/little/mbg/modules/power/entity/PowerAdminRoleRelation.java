package cn.yujian95.little.mbg.modules.power.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限账号关联角色
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("power_admin_role_relation")
@ApiModel(value = "权限账号关联角色对象", description = "权限账号关联角色")
public class PowerAdminRoleRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号角色关系编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "管理员编号")
    private Long adminId;

    @ApiModelProperty(value = "角色编号")
    private Long roleId;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
