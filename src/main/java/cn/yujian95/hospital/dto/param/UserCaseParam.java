package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/2
 */

@ApiModel(value = "UserCaseParam",description = "用户病例参数")
@Data
public class UserCaseParam implements Serializable {

    /**
     * 就诊卡编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "就诊卡编号")
    private Long cardId;

    /**
     * 预约编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "预约编号")
    private Long appointmentId;

    /**
     * 医生编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "医生编号")
    private Long doctorId;

    /**
     * 病例详情
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "病例详情")
    private String content;
}
