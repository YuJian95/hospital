package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/24
 */

@ApiModel(value = "PowerAccountStatusParam",description = "账号状态修改参数")
@Data
public class PowerAccountStatusParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号编号")
    private Long accountId;

    @ApiModelProperty(value = "账号状态（1：开启，0：关闭）")
    private Integer status;
}
