package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/12
 */

@ApiModel(value = "PowerResourceParam", description = "权限资源参数")
@Data
public class PowerResourceParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源分类编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "资源分类编号")
    private Long categoryId;

    /**
     * 资源名称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "资源名称")
    private String name;

    /**
     * 资源URL
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "资源URL")
    private String url;

    /**
     * 资源描述
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "资源描述")
    private String description;
}
