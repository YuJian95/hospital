package cn.yujian95.hospital.dto;

import cn.yujian95.hospital.entity.VisitPlan;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/19
 */

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "VisitPlanResiduesDTO", description = "出诊计划含余额数")
@Data
public class VisitPlanResiduesDTO extends VisitPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("对应时间段，剩余号数")
    private List<Integer> residues;

}
