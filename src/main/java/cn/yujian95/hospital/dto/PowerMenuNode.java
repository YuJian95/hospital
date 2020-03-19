package cn.yujian95.hospital.dto;

import cn.yujian95.hospital.entity.PowerMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/12
 */

@ApiModel(value = "PowerMenuNode", description = "权限菜单节点封装对象")
@Getter
@Setter
public class PowerMenuNode extends PowerMenu {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("子菜单列表")
    private List<PowerMenuNode> children;
}
