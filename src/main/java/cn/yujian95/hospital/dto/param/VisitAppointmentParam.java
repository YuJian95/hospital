package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/19
 */

@ApiModel(value = "VisitAppointmentParam", description = "出诊预约参数")
@Data
public class VisitAppointmentParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 出诊编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "出诊编号")
    private Long planId;

    /**
     * 就诊卡号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "就诊卡号")
    private Long cardId;

    /**
     * 账号编号
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "账号编号")
    private Long accountId;

}
