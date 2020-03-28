package cn.yujian95.hospital.service.impl;

import cn.yujian95.hospital.dto.PowerMenuNode;
import cn.yujian95.hospital.dto.param.PowerMenuParam;
import cn.yujian95.hospital.entity.PowerMenu;
import cn.yujian95.hospital.entity.PowerMenuExample;
import cn.yujian95.hospital.mapper.PowerMenuMapper;
import cn.yujian95.hospital.service.IPowerMenuService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/12
 */
@Service
public class PowerMenuServiceImpl implements IPowerMenuService {

    private static final long ROOT_MENU = 0L;

    @Resource
    private PowerMenuMapper menuMapper;

    /**
     * 插入菜单
     *
     * @param param 菜单参数
     * @return 是否成功
     */
    @Override
    public boolean insert(PowerMenuParam param) {

        PowerMenu menu = new PowerMenu();

        BeanUtils.copyProperties(param, menu);

        updateLevel(menu);

        menu.setGmtCreate(new Date());
        menu.setGmtModified(new Date());

        return menuMapper.insertSelective(menu) > 0;
    }

    /**
     * 更新菜单
     *
     * @param id    菜单编号
     * @param param 菜单参数
     * @return 是否成功
     */
    @Override
    public boolean update(Long id, PowerMenuParam param) {
        PowerMenu menu = new PowerMenu();

        BeanUtils.copyProperties(param, menu);

        menu.setId(id);
        updateLevel(menu);
        menu.setGmtModified(new Date());

        return menuMapper.updateByPrimaryKeySelective(menu) > 0;
    }

    /**
     * 更新菜单隐藏情况
     *
     * @param id     菜单编号
     * @param hidden 隐藏状态：0 隐藏，1 显示
     * @return 是否成功
     */
    @Override
    public boolean updateHidden(Long id, Integer hidden) {
        PowerMenu menu = new PowerMenu();

        menu.setId(id);
        menu.setHidden(hidden);

        return menuMapper.updateByPrimaryKeySelective(menu) > 0;

    }

    /**
     * 判断是否存在
     *
     * @param id 菜单编号
     * @return 是否存在
     */
    @Override
    public boolean count(Long id) {
        PowerMenuExample example = new PowerMenuExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return menuMapper.countByExample(example) > 0;
    }

    /**
     * 删除菜单
     *
     * @param id 菜单编号
     * @return 是否成功
     */
    @Override
    public boolean delete(Long id) {
        return menuMapper.deleteByPrimaryKey(id) > 0;
    }

    /**
     * 获取菜单信息
     *
     * @param id 菜单编号
     * @return 菜单信息
     */
    @Override
    public Optional<PowerMenu> get(Long id) {
        return Optional.ofNullable(menuMapper.selectByPrimaryKey(id));
    }

    /**
     * 查找菜单列表
     *
     * @param parentId 父菜单编号
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 菜单列表
     */
    @Override
    public List<PowerMenu> list(Long parentId, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        PowerMenuExample example = new PowerMenuExample();

        example.setOrderByClause("sort desc");
        example.createCriteria()
                .andParentIdEqualTo(parentId);

        return menuMapper.selectByExample(example);

    }

    /**
     * 获取所有菜单以树状返回
     *
     * @return 菜单节点
     */
    @Override
    public List<PowerMenuNode> treeList() {
        List<PowerMenu> menuList = menuMapper.selectByExample(new PowerMenuExample());
        return menuList.stream()
                .filter(menu -> menu.getParentId().equals(ROOT_MENU))
                .map(menu -> covertMenuNode(menu, menuList))
                .collect(Collectors.toList());
    }


    /**
     * 修改菜单层级
     *
     * @param menu 菜单信息
     */
    private void updateLevel(PowerMenu menu) {
        if (menu.getParentId() == 0) {
            //没有父菜单时为一级菜单
            menu.setLevel(0);
        } else {
            //有父菜单时选择根据父菜单level设置
            PowerMenu parentMenu = menuMapper.selectByPrimaryKey(menu.getParentId());
            if (parentMenu != null) {
                menu.setLevel(parentMenu.getLevel() + 1);
            } else {
                menu.setLevel(0);
            }
        }
    }

    /**
     * 将 PowerMenu 转化为 PowerMenuNode并设置children属性
     *
     * @param menu     菜单信息
     * @param menuList 子菜单列表
     * @return 树状菜单列表
     */
    private PowerMenuNode covertMenuNode(PowerMenu menu, List<PowerMenu> menuList) {
        PowerMenuNode node = new PowerMenuNode();
        BeanUtils.copyProperties(menu, node);
        List<PowerMenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

}
