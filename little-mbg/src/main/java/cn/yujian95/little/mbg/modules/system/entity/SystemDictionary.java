package cn.yujian95.little.mbg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统数据字典
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("system_dictionary")
@ApiModel(value = "系统数据字典对象", description = "系统数据字典")
public class SystemDictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "父对象")
    private String parentId;

    @ApiModelProperty(value = "字典码")
    private String code;

    @ApiModelProperty(value = "显示值")
    private String showValue;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "排序（从1开始排序）")
    private Integer sort;

    @ApiModelProperty(value = "状态(-1 ： 全部，0：正常，1：锁定)")
    private Integer status;

    @ApiModelProperty(value = "备注说明(小于120字)")
    private String remark;

    @ApiModelProperty(value = "逻辑删除 0：正常，1：删除")
    @TableLogic
    private Integer logicDelete;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新人")
    private Long modifiedBy;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
