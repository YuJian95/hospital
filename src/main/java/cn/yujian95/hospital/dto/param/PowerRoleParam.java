package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/19
 */

@ApiModel(value = "PowerRoleParam", description = "权限角色参数")
@Data
public class PowerRoleParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 英文名称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "英文名称")
    private String name;

    /**
     * 中文名称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "中文名称")
    private String chineseName;

    /**
     * 角色状态 1：启用，0：禁用
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "角色状态 1：启用，0：禁用")
    private Integer status;
}
