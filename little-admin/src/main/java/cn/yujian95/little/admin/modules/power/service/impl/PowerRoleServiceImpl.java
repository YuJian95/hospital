package cn.yujian95.little.admin.modules.power.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.yujian95.little.admin.modules.power.service.IPowerRoleMenuRelationService;
import cn.yujian95.little.admin.modules.power.service.IPowerRoleResourceRelationService;
import cn.yujian95.little.admin.modules.power.service.IPowerRoleService;
import cn.yujian95.little.mbg.modules.power.entity.*;
import cn.yujian95.little.mbg.modules.power.mapper.PowerMenuMapper;
import cn.yujian95.little.mbg.modules.power.mapper.PowerResourceMapper;
import cn.yujian95.little.mbg.modules.power.mapper.PowerRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限角色 接口实现类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
@Service
public class PowerRoleServiceImpl extends ServiceImpl<PowerRoleMapper, PowerRole> implements IPowerRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PowerRoleServiceImpl.class);

    @Resource
    private IPowerRoleMenuRelationService roleMenuRelationService;

    @Resource
    private IPowerRoleResourceRelationService roleResourceRelationService;

    @Resource
    private PowerMenuMapper menuMapper;

    @Resource
    private PowerResourceMapper resourceMapper;

    /**
     * 分页获取角色列表
     *
     * @param keyword  关键词
     * @param pageNum  第几页
     * @param pageSize 页大小
     */
    @Override
    public Page<PowerRole> search(String keyword, Integer pageNum, Integer pageSize) {
        Page<PowerRole> page = new Page<>(pageNum, pageSize);
        QueryWrapper<PowerRole> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<PowerRole> lambda = wrapper.lambda();
        if (StrUtil.isNotEmpty(keyword)) {
            lambda.like(PowerRole::getName, keyword);
        }
        return page(page, wrapper);
    }

    /**
     * 根据管理员ID获取对应菜单
     *
     * @param adminId 管理员 ID
     * @return 管理员所含菜单列表
     */
    @Override
    public List<PowerMenu> listMenuByAdminId(Long adminId) {
        return menuMapper.listMenuByAdminId(adminId);
    }

    /**
     * 获取角色相关菜单
     *
     * @param roleId 角色 ID
     * @return 角色所含菜单列表
     */
    @Override
    public List<PowerMenu> listMenuByRoleId(Long roleId) {
        return menuMapper.listMenuByRoleId(roleId);
    }

    /**
     * 获取角色相关资源
     *
     * @param roleId 角色 ID
     * @return 角色所含资源列表
     */
    @Override
    public List<PowerResource> listResource(Long roleId) {
        return resourceMapper.listResourceByRoleId(roleId);
    }

    /**
     * 给角色分配菜单
     *
     * @param roleId     角色 ID
     * @param menuIdList 菜单编号列表
     * @return 是否成功
     */
    @Override
    public boolean allocMenu(Long roleId, List<Long> menuIdList) {
        // 先删除原有关系
        QueryWrapper<PowerRoleMenuRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PowerRoleMenuRelation::getRoleId, roleId);
        roleMenuRelationService.remove(wrapper);

        // 批量插入新关系
        List<PowerRoleMenuRelation> relationList = new ArrayList<>();

        for (Long menuId : menuIdList) {
            PowerRoleMenuRelation relation = new PowerRoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            relationList.add(relation);
        }

        return roleMenuRelationService.saveBatch(relationList);
    }

    /**
     * 给角色分配资源
     *
     * @param roleId         角色 ID
     * @param resourceIdList 资源编号列表
     * @return 是否成功
     */
    @Override
    public boolean allocResource(Long roleId, List<Long> resourceIdList) {
        // 先删除原有关系
        QueryWrapper<PowerRoleResourceRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PowerRoleResourceRelation::getRoleId, roleId);
        roleResourceRelationService.remove(wrapper);

        //  批量插入新关系
        List<PowerRoleResourceRelation> relationList = new ArrayList<>();

        for (Long resourceId : resourceIdList) {
            PowerRoleResourceRelation relation = new PowerRoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            relationList.add(relation);
        }

        return roleResourceRelationService.saveBatch(relationList);
    }

    /**
     * 判断是否，不存在该记录编号
     *
     * @param id 记录编号
     * @return 是否不存在
     */
    @Override
    public boolean isNotExist(Long id) {

        QueryWrapper<PowerRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PowerRole::getId, id);

        return count(wrapper) == 0;
    }
}
