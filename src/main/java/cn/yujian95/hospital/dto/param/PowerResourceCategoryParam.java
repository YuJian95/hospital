package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/12
 */
@ApiModel(value = "PowerResourceCategoryParam", description = "权限资源分类参数")
@Data
public class PowerResourceCategoryParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "分类名称")
    private String name;

    /**
     * 分类排序 数值越小，越靠前
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "分类排序 数值越小，越靠前")
    private Integer sort;
}
