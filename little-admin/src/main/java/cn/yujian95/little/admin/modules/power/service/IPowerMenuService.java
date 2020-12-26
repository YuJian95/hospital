package cn.yujian95.little.admin.modules.power.service;

import cn.yujian95.little.admin.modules.power.dto.PowerMenuNode;
import cn.yujian95.little.mbg.modules.power.entity.PowerMenu;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限菜单  接口类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
public interface IPowerMenuService extends IService<PowerMenu> {

    /**
     * 判断是否，不存在该记录编号
     *
     * @param id 记录编号
     * @return 是否不存在
     */
    boolean isNotExist(Long id);


    /**
     * 创建后台菜单
     *
     * @param menu 菜单信息
     * @return 是否成功
     */
    boolean create(PowerMenu menu);

    /**
     * 修改后台菜单
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    boolean update(PowerMenu menu);

    /**
     * 分页查询后台菜单
     *
     * @param parentId 父菜单编号
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 菜单列表
     */
    Page<PowerMenu> search(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 获取菜单列表（树状返回）
     *
     * @return 菜单列表
     */
    List<PowerMenuNode> treeList();

    /**
     * 修改菜单显示状态
     *
     * @param id     菜单编号
     * @param hidden 是否显示
     * @return 是否成功
     */
    boolean updateHidden(Long id, Integer hidden);
}
