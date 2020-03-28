package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/1
 */

@ApiModel(value = "VisitPlanParam", description = "出诊计划参数")
@Data
public class VisitPlanParam {

    /**
     * 医院编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医院编号")
    private Long hospitalId;

    /**
     * 专科编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "专科编号")
    private Long specialId;

    /**
     * 门诊编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "门诊编号")
    private Long outpatientId;

    /**
     * 诊室编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "诊室编号")
    private Long clinicId;

    /**
     * 医生编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医生编号")
    private Long doctorId;

    /**
     * 时间段 1：上午，2：下午
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "时间段 1：上午，2：下午")
    private Integer time;

    /**
     * 出诊日期
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "出诊日期")
    private Date day;

}
