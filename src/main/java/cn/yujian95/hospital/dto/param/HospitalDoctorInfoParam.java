package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/4
 */
@ApiModel(value = "HospitalDoctorInfoParam", description = "医生信息参数")
@Data
public class HospitalDoctorInfoParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 医生姓名
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医生姓名")
    private String name;

    /**
     * 医生相片
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医生相片")
    private String avatarUrl;

    /**
     * 医生职称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医生职称")
    private String jobTitle;

    /**
     * 医生专长
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医生专长")
    private String specialty;
}
