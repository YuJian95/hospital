package cn.yujian95.little.admin.modules.power.service;

import cn.yujian95.little.mbg.modules.power.entity.PowerMenu;
import cn.yujian95.little.mbg.modules.power.entity.PowerResource;
import cn.yujian95.little.mbg.modules.power.entity.PowerRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 权限角色 接口类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
public interface IPowerRoleService extends IService<PowerRole> {

    /**
     * 判断是否，不存在该记录编号
     *
     * @param id 记录编号
     * @return 是否不存在
     */
    boolean isNotExist(Long id);

    /**
     * 分页获取角色列表
     *
     * @param keyword  关键词
     * @param pageNum  第几页
     * @param pageSize 页大小
     */
    Page<PowerRole> search(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 根据管理员ID获取对应菜单
     *
     * @param adminId 管理员 ID
     * @return 管理员所含菜单列表
     */
    List<PowerMenu> listMenuByAdminId(Long adminId);

    /**
     * 获取角色相关菜单
     *
     * @param roleId 角色 ID
     * @return 角色所含菜单列表
     */
    List<PowerMenu> listMenuByRoleId(Long roleId);

    /**
     * 获取角色相关资源
     *
     * @param roleId 角色 ID
     * @return 角色所含资源列表
     */
    List<PowerResource> listResource(Long roleId);

    /**
     * 给角色分配菜单
     *
     * @param roleId     角色 ID
     * @param menuIdList 菜单编号列表
     * @return 是否成功
     */
    @Transactional
    boolean allocMenu(Long roleId, List<Long> menuIdList);

    /**
     * 给角色分配资源
     *
     * @param roleId         角色 ID
     * @param resourceIdList 资源编号列表
     * @return 是否成功
     */
    @Transactional
    boolean allocResource(Long roleId, List<Long> resourceIdList);
}
