package cn.yujian95.base.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 普通用户基础信息参数
 *
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/28
 */

@ApiModel("普通用户基础信息参数")
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
     * 性别 1男，2女
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "性别 1男，2女")
    private Integer sex;

    /**
     * 出生日期
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "出生日期")
    private Date birthDate;
}
