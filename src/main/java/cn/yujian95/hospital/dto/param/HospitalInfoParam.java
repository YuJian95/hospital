package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/3
 */

@ApiModel(value = "HospitalInfoParam", description = "医院信息参数")
@Data
public class HospitalInfoParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 医院名称
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医院名称")
    private String name;

    /**
     * 医院电话
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医院电话")
    private String phone;

    /**
     * 医院地址
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医院地址")
    private String address;

    /**
     * 医院简介
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医院简介")
    private String description;

    /**
     * 医院图片
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医院图片")
    private String picture;
}
