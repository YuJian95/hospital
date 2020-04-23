package cn.yujian95.hospital.dto;

import cn.yujian95.hospital.entity.VisitAppointment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/4/11
 */

@ApiModel(value = "VisitAppointmentDTO", description = "预约记录情况")
@Data
public class VisitAppointmentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("预约记录编号")
    private Long appointmentId;

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;

    @ApiModelProperty(value = "专科名称")
    private String specialName;

    @ApiModelProperty(value = "门诊名称")
    private String outpatientName;

    @ApiModelProperty(value = "诊室名称")
    private String clinicName;

    @ApiModelProperty(value = "医生名称")
    private String doctorName;

    @ApiModelProperty(value = "患者名称")
    private String name;

    @ApiModelProperty(value = "时间段 1：上午，2：下午")
    private Integer time;

    @ApiModelProperty(value = "出诊日期")
    private Date day;

    @ApiModelProperty(value = "预约状态 0：未开始，1：未按时就诊，2：取消预约挂号，3：已完成")
    private Integer status;
}
