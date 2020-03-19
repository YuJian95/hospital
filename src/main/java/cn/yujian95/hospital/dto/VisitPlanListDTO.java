package cn.yujian95.hospital.dto;

import cn.yujian95.hospital.entity.HospitalInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/19
 */

@Data
@ApiModel(value = "VisitPlanListDTO", description = "医生出诊信息列表")
public class VisitPlanListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("医院信息")
    private HospitalInfo info;

    @ApiModelProperty("整合剩余挂号数的出诊计划列表")
    private List<VisitPlanResiduesDTO> planResiduesDTOList;
}
