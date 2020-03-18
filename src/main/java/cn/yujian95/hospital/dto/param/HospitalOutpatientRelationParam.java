package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/18
 */

@ApiModel(value = "HospitalOutpatientRelationParam", description = "医院门诊关系参数")
@Data
public class HospitalOutpatientRelationParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 医院编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医院编号")
    private Long hospitalId;

    /**
     * 门诊编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "门诊编号")
    private Long outpatientId;

}
