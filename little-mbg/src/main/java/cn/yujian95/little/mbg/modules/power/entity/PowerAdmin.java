package cn.yujian95.little.mbg.modules.power.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限账号
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-11-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("power_admin")
@ApiModel(value = "权限账号对象", description = "权限账号")
public class PowerAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "登录账号 唯一")
    private String username;

    @ApiModelProperty(value = "登录密码 使用md5加密")
    private String password;

    @ApiModelProperty(value = "备注信息")
    private String note;

    @ApiModelProperty(value = "账号状态 1：正常，0：锁定")
    private Integer status;

    @ApiModelProperty(value = "第一次登陆时间")
    private Date firstLogin;

    @ApiModelProperty(value = "最后登录时间")
    private Date loginTime;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
