package cn.yujian95.little.admin.modules.power.service;

import cn.yujian95.little.mbg.modules.power.entity.PowerRoleMenuRelation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 权限角色关联菜单  接口类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
public interface IPowerRoleMenuRelationService extends IService<PowerRoleMenuRelation> {

    /**
     * 判断是否，不存在该记录编号
     *
     * @param id 记录编号
     * @return 是否不存在
     */
     boolean isNotExist(Long id);
}
