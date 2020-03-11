package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/11
 */

@ApiModel(value = "StatusParam", description = "状态参数")
@Data
public class StatusParam implements Serializable {

    @ApiModelProperty(value = "状态 1：启用，0：禁用")
    private Integer status;
}
