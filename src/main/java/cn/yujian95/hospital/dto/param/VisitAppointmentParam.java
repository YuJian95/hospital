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

    /**
     * 1： 8点半~9点，2： 9点~9点半，3： 9点半~10点，4： 10点~10点半，5： 11点~11点半，6： 11点半~12点，7：2点~2点半，8： 2点半~3点，9： 3点~3点半，10： 3点半~4点，11： 4点~4点半，12： 4点半~5点，13： 5点~5点半，14：5点半~6点
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "1： 8点半~9点，2： 9点~9点半，3： 9点半~10点，4： 10点~10点半，5： 11点~11点半，6： 11点半~12点，7：2点~2点半，8： 2点半~3点，9： 3点~3点半，10： 3点半~4点，11： 4点~4点半，12： 4点半~5点，13： 5点~5点半，14：5点半~6点")
    private Integer timePeriod;

}
