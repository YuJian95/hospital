package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 普通用户信息参数
 *
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/1
 */

@ApiModel(value = "UserBasicInfoParam", description = "普通用户信息参数")
@Data
public class UserBasicInfoParam implements Serializable {
    /**
     * 姓名
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 用户头像
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "用户头像")
    private String avatarUrl;
}
