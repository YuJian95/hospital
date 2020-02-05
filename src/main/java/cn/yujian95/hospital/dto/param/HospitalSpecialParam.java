package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/5
 */
@ApiModel(value = "HospitalSpecialParam", description = "医院专科参数")
@Data
public class HospitalSpecialParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 专科名称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "专科名称")
    private String name;

    /**
     * 专科简介
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "专科简介")
    private String description;

}
