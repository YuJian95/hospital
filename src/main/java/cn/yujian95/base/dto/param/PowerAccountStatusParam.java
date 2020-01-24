package cn.yujian95.base.dto.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/24
 */

@Data
public class PowerAccountStatusParam {
    @ApiModelProperty(value = "账号编号")
    private Long accountId;

    @ApiModelProperty(value = "账号状态（1：开启，0：关闭）")
    private Integer status;
}
