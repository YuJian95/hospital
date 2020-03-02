package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/2
 */

@ApiModel(value = "UserCaseUpdateParam", description = "用户病例更新参数")
@Data
public class UserCaseUpdateParam implements Serializable {

    /**
     * 病例详情
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "病例详情")
    private String content;
}
