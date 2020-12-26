package cn.yujian95.little.admin.modules.power.service.impl;

import cn.yujian95.little.admin.modules.power.dto.PowerMenuNode;
import cn.yujian95.little.admin.modules.power.service.IPowerMenuService;
import cn.yujian95.little.mbg.modules.power.entity.PowerMenu;
import cn.yujian95.little.mbg.modules.power.mapper.PowerMenuMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限菜单  接口实现类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
@Service
public class PowerMenuServiceImpl extends ServiceImpl<PowerMenuMapper, PowerMenu> implements IPowerMenuService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PowerMenuServiceImpl.class);

    /**
     * 根节点
     */
    private static final int ROOT_LEVEL = 0;

    /**
     * 判断是否，不存在该记录编号
     *
     * @param id 记录编号
     * @return 是否不存在
     */
    @Override
    public boolean isNotExist(Long id) {

        QueryWrapper<PowerMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PowerMenu::getId, id);

        return count(wrapper) == ROOT_LEVEL;
    }

    /**
     * 创建后台菜单
     *
     * @param menu 菜单信息
     * @return 是否成功
     */
    @Override
    public boolean create(PowerMenu menu) {

        menu.setGmtCreate(new Date());
        menu.setGmtModified(new Date());

        updateLevel(menu);

        return save(menu);
    }

    /**
     * 修改后台菜单
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    @Override
    public boolean update(PowerMenu menu) {
        menu.setGmtModified(new Date());
        updateLevel(menu);

        return updateById(menu);
    }

    /**
     * 分页查询后台菜单
     *
     * @param parentId 父菜单编号
     * @param pageSize 页大小
     * @param pageNum  第几页
     * @return 菜单列表
     */
    @Override
    public Page<PowerMenu> search(Long parentId, Integer pageSize, Integer pageNum) {

        Page<PowerMenu> page = new Page<>(pageNum, pageSize);

        QueryWrapper<PowerMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PowerMenu::getParentId, parentId)
                .orderByDesc(PowerMenu::getSort);

        return page(page, wrapper);
    }

    /**
     * 获取菜单列表（树状返回）
     *
     * @return 菜单列表
     */
    @Override
    public List<PowerMenuNode> treeList() {

        List<PowerMenu> menuList = list();

        return menuList.stream()
                // 根权限编号为 0 L
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, menuList))
                .collect(Collectors.toList());
    }

    /**
     * 修改菜单显示状态
     *
     * @param id     菜单编号
     * @param hidden 是否显示
     * @return 是否成功
     */
    @Override
    public boolean updateHidden(Long id, Integer hidden) {

        PowerMenu menu = new PowerMenu();

        menu.setId(id);
        menu.setHidden(hidden);
        menu.setGmtModified(new Date());

        return updateById(menu);
    }

    /**
     * 修改菜单层级
     */
    private void updateLevel(PowerMenu menu) {

        if (menu.getParentId() == ROOT_LEVEL) {
            // 没有父菜单时为一级菜单
            menu.setLevel(ROOT_LEVEL);
        } else {

            // 有父菜单时选择根据父菜单level设置
            PowerMenu parentMenu = getById(menu.getParentId());
            if (parentMenu != null) {
                menu.setLevel(parentMenu.getLevel() + 1);
            } else {
                menu.setLevel(ROOT_LEVEL);
            }
        }
    }

    /**
     * 将 PowerMenu -> PowerMenuNode，并设置 子节点属性
     *
     * @param menu     菜单
     * @param menuList 子菜单列表
     * @return 树状列表
     */
    private PowerMenuNode covertMenuNode(PowerMenu menu, List<PowerMenu> menuList) {
        PowerMenuNode node = new PowerMenuNode();
        BeanUtils.copyProperties(menu, node);

        List<PowerMenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList))
                .collect(Collectors.toList());

        node.setChildren(children);
        return node;
    }
}
