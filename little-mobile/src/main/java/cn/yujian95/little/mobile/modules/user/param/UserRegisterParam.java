package cn.yujian95.little.mobile.modules.user.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author YuJian95  yujian95_cn@163.com
 * @date 2020/10/20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="用户注册参数对象", description="用户信息表")
public class UserRegisterParam implements Serializable {

    private static final long serialVersionUID=1L;

    @NotEmpty
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotEmpty
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "OpenID")
    private String openId;

    @ApiModelProperty(value = "所属机构")
    private Long agencyId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @NotEmpty
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别")
    private String sex;
}
