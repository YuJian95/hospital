package cn.yujian95.little.mbg.modules.power.mapper;

import cn.yujian95.little.mbg.modules.power.entity.PowerResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限资源  Mapper 接口
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
public interface PowerResourceMapper extends BaseMapper<PowerResource> {

    /**
     * 获取用户所有可访问资源
     *
     * @param adminId 管理员编号
     * @return 权限资源
     */
    List<PowerResource> listResourceByAdminId(Long adminId);

    /**
     * 根据角色ID获取资源
     *
     * @param roleId 角色 ID
     * @return 权限资源
     */
    List<PowerResource> listResourceByRoleId(Long roleId);
}
