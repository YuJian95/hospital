package cn.yujian95.hospital.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/4/13
 */

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "VisitAppointmentQueueDTO", description = "候诊队列")
@Data
public class VisitAppointmentQueueDTO extends VisitAppointmentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("前方候诊人数")
    private Integer waitPeopleNum;

    @ApiModelProperty("就诊号")
    private Integer queueNum;

    @ApiModelProperty("还需等待时间长")
    private Integer waitTime;

    @ApiModelProperty("预约时间段")
    private Integer timePeriod;
}
