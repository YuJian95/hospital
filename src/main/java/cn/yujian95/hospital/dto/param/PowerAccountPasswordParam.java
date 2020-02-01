package cn.yujian95.hospital.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/1
 */

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PowerAccountPasswordParam", description = "账号修改密码对象参数")
@Data
public class PowerAccountPasswordParam extends PowerAccountRegisterParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "短信验证码")
    private String code;
}
