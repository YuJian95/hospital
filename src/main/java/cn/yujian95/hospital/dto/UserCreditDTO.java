package cn.yujian95.hospital.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/4/10
 */

@ApiModel(value = "UserCreditDTO", description = "用户当月信用封装对象")
@Data
public class UserCreditDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("取消")
    private Integer cancel;

    @ApiModelProperty("完成")
    private Integer finish;

    @ApiModelProperty("失约")
    private Integer miss;
}
