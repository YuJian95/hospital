package cn.yujian95.little.mbg.modules.system.entity;

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
 * 系统用户登录日志
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("system_login_log")
@ApiModel(value="系统用户登录日志对象", description="系统用户登录日志")
public class SystemLoginLog implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "记录编号")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "类型0：微信端1：管理端")
    private Integer type;

    @ApiModelProperty(value = "小程序用户编号/管理员编号")
    private Long accountId;

    @ApiModelProperty(value = "账号名称")
    private String name;

    @ApiModelProperty(value = "ip地址")
    private String ipAddress;

    @ApiModelProperty(value = "创建日期")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新日期")
    private Date gmtModified;


}
