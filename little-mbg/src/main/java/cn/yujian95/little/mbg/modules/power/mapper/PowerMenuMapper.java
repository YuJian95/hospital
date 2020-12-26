package cn.yujian95.little.mbg.modules.power.mapper;

import cn.yujian95.little.mbg.modules.power.entity.PowerMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限菜单  Mapper 接口
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
public interface PowerMenuMapper extends BaseMapper<PowerMenu> {

    /**
     * 根据后台用户ID获取菜单
     *
     * @param adminId 管理员 ID
     * @return 管理员菜单列表
     */
    List<PowerMenu> listMenuByAdminId(Long adminId);

    /**
     * 根据角色ID获取菜单
     *
     * @param roleId 角色 ID
     * @return 角色菜单列表
     */
    List<PowerMenu> listMenuByRoleId(Long roleId);

}
