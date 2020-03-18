package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/5
 */
@ApiModel(value = "HospitalOutpatientParam", description = "门诊信息参数")
@Data
public class HospitalOutpatientParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 门诊名称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "门诊名称")
    private String name;

    /**
     * 所属专科
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "所属专科")
    private Long specialId;
}
