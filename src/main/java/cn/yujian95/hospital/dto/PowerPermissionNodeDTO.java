package cn.yujian95.hospital.dto;

import cn.yujian95.hospital.entity.PowerPermission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/25
 */

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PowerPermissionNodeDTO", description = "权限及其子权限列表")
@Data
public class PowerPermissionNodeDTO extends PowerPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "子权限列表")
    private List<PowerPermissionNodeDTO> children;
}
