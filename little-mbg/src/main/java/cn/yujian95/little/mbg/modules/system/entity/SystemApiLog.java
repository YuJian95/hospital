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
 * 系统接口日志
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("system_api_log")
@ApiModel(value = "系统接口日志 对象", description = "系统接口日志 ")
public class SystemApiLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "类型 微信端：wx，管理端：admin")
    private String type;

    @ApiModelProperty(value = "操作用户")
    private String username;

    @ApiModelProperty(value = "操作描述")
    private String description;

    @ApiModelProperty(value = "操作时间")
    private Date startTime;

    @ApiModelProperty(value = "消耗时间")
    private Double spendTime;

    @ApiModelProperty(value = "请求域名")
    private String basePath;

    @ApiModelProperty(value = "URI")
    private String uri;

    @ApiModelProperty(value = "URL")
    private String url;

    @ApiModelProperty(value = "请求类型 post、put、get、delete")
    private String method;

    @ApiModelProperty(value = "IP地址")
    private String ip;

    @ApiModelProperty(value = "请求参数")
    private String parameter;

    @ApiModelProperty(value = "返回结果")
    private String result;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
