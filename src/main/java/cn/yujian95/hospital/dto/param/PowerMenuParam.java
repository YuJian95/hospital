package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/12
 */

@ApiModel(value = "PowerMenuParam", description = "权限菜单参数")
@Data
public class PowerMenuParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 父级菜单
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "父级菜单")
    private Long parentid;

    /**
     * 菜单名称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "菜单名称")
    private String title;

    /**
     * 菜单级数
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "菜单级数")
    private Integer level;

    /**
     * 菜单排序
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "菜单排序")
    private Integer sort;

    /**
     * 前端路径
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "前端路径")
    private String name;

    /**
     * 前端图标
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "前端图标")
    private String icon;

    /**
     * 前端隐藏 0：隐藏，1：显示
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "前端隐藏 0：隐藏，1：显示")
    private Integer hidden;

}
