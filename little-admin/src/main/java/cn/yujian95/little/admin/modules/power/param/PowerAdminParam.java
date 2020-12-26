package cn.yujian95.little.admin.modules.power.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/10/13
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="账号注册参数对象", description="账号信息表")
public class PowerAdminParam implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "登录账号 唯一")
    @NotEmpty
    private String username;

    @ApiModelProperty(value = "登录密码 使用md5加密")
    @NotEmpty
    private String password;

    @ApiModelProperty(value = "备注信息")
    private String note;

    @ApiModelProperty(value = "账号状态 1：正常，0：锁定")
    private Integer status;
}