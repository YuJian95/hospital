package cn.yujian95.base.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/20
 */

@ApiModel(value = "PowerPermissionParam", description = "权限权值参数")
@Data
public class PowerPermissionParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限类型 0->目录；1->菜单；2->按钮（接口绑定权限）
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "权限类型 0->目录；1->菜单；2->按钮（接口绑定权限）")
    private Integer type;

    /**
     * 权限名称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "权限名称")
    private String name;

    /**
     * 权限值
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "权限值")
    private String value;

    /**
     * 前端图标
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "前端图标")
    private String icon;

    /**
     * 前端路径
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "前端路径")
    private String url;

    /**
     * 父权限编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "父权限编号")
    private Long pid;

    /**
     * 权限状态 1：开启，0：禁用
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "权限状态 1：开启，0：禁用")
    private Integer status;
}
