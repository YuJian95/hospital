package cn.yujian95.little.admin.modules.power.dto;

import cn.yujian95.little.mbg.modules.power.entity.PowerMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author YuJian95  yujian95_cn@163.com
 * @date 2020/10/15
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class PowerMenuNode extends PowerMenu {

    @ApiModelProperty(value = "子级菜单")
    private List<PowerMenuNode> children;
}
