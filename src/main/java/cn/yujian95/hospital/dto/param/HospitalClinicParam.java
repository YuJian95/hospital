package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/12
 */
@ApiModel(value = "HospitalClinicParam",description = "诊室信息参数")
@Data
public class HospitalClinicParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 所属门诊
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "所属门诊")
    private Long outpatientId;

    /**
     * 诊室地址
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "诊室地址")
    private String address;
}
