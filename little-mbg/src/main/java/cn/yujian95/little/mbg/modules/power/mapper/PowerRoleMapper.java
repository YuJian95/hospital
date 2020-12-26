package cn.yujian95.little.mbg.modules.power.mapper;

import cn.yujian95.little.mbg.modules.power.entity.PowerRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限角色 Mapper 接口
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
public interface PowerRoleMapper extends BaseMapper<PowerRole> {

    /**
     * 获取用户所有角色
     *
     * @param adminId 管理员编号
     * @return 所有角色列表
     */
    List<PowerRole> listRoleByAdminId(Long adminId);
}
