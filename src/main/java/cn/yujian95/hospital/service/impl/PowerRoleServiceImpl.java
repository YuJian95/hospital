package cn.yujian95.hospital.service.impl;

import cn.yujian95.hospital.dto.param.PowerRoleParam;
import cn.yujian95.hospital.dto.param.StatusParam;
import cn.yujian95.hospital.entity.*;
import cn.yujian95.hospital.mapper.PowerRoleMapper;
import cn.yujian95.hospital.mapper.PowerRoleMenuRelationMapper;
import cn.yujian95.hospital.mapper.PowerRoleResourceRelationMapper;
import cn.yujian95.hospital.mapper.dao.PowerRoleDao;
import cn.yujian95.hospital.service.IPowerRoleService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/25
 */

@Service
public class PowerRoleServiceImpl implements IPowerRoleService {

    @Resource
    private PowerRoleMapper roleMapper;


    @Resource
    private PowerRoleDao roleDao;

    @Resource
    private PowerRoleMenuRelationMapper roleMenuRelationMapper;

    @Resource
    private PowerRoleResourceRelationMapper roleResourceRelationMapper;

    /**
     * 添加角色
     *
     * @param param 角色信息参数
     * @return 成功记录数
     */
    @Override
    public boolean insert(PowerRoleParam param) {
        PowerRole role = new PowerRole();

        BeanUtils.copyProperties(param, role);

        Date date = new Date();
        role.setGmtCreate(date);
        role.setGmtModified(date);

        return roleMapper.insertSelective(role) > 0;
    }


    /**
     * 更新角色状态
     *
     * @param roleId 角色编号
     * @param param  状态参数： 0 关闭，1 开启
     * @return 是否成功
     */
    @Override
    public boolean updateStatus(Long roleId, StatusParam param) {
        PowerRole role = new PowerRole();

        role.setId(roleId);
        role.setStatus(param.getStatus());

        Date date = new Date();
        role.setGmtModified(date);

        return roleMapper.updateByPrimaryKeySelective(role) > 0;
    }

    /**
     * 修改角色信息
     *
     * @param roleId 角色编号
     * @param param  角色信息参数
     * @return 成功记录数
     */
    @Override
    public boolean update(Long roleId, PowerRoleParam param) {
        PowerRole role = new PowerRole();

        BeanUtils.copyProperties(param, role);

        role.setId(roleId);

        Date date = new Date();
        role.setGmtModified(date);

        return roleMapper.updateByPrimaryKeySelective(role) > 0;
    }

    /**
     * 批量删除角色
     *
     * @param idList 角色编号
     * @return 成功记录数
     */
    @Override
    public int delete(List<Long> idList) {

        PowerRoleExample example = new PowerRoleExample();

        example.createCriteria()
                .andIdIn(idList);

        return roleMapper.deleteByExample(example);
    }

    /**
     * 删除角色信息
     *
     * @param roleId 角色编号
     * @return 是否成功
     */
    @Override
    public boolean delete(Long roleId) {
        return roleMapper.deleteByPrimaryKey(roleId) > 0;
    }

    /**
     * 获取角色列表
     *
     * @param chineseName 中文名
     * @param pageNum     第几页
     * @param pageSize    页大小
     * @return 角色列表
     */
    @Override
    public List<PowerRole> list(String chineseName, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        PowerRoleExample example = new PowerRoleExample();

        if (!StringUtils.isEmpty(chineseName)) {
            example.createCriteria()
                    .andChineseNameLike("%" + chineseName + "%");
        }

        return roleMapper.selectByExample(example);
    }

    /**
     * 通过账号编号，获取菜单列表
     *
     * @param accountId 账号编号
     * @return 菜单列表
     */
    @Override
    public List<PowerMenu> listMenu(Long accountId) {
        return roleDao.listMenu(accountId);
    }

    /**
     * 通过角色编号，获取资源列表
     *
     * @param roleId 角色编号
     * @return 资源列表
     */
    @Override
    public List<PowerResource> listMenuResource(Long roleId) {
        return roleDao.listResourceByRoleId(roleId);
    }

    /**
     * 通过角色编号，获取菜单列表
     *
     * @param roleId 角色编号
     * @return 菜单列表
     */
    @Override
    public List<PowerMenu> listMenuByRoleId(Long roleId) {
        return roleDao.listMenuByRoleId(roleId);
    }

    /**
     * 更新角色菜单列表
     *
     * @param roleId     角色编号
     * @param menuIdList 菜单列表
     * @return 是否成功
     */
    @Override
    public boolean allocMenu(Long roleId, List<Long> menuIdList) {

        //先删除原有关系
        PowerRoleMenuRelationExample example = new PowerRoleMenuRelationExample();

        example.createCriteria()
                .andRoleIdEqualTo(roleId);

        roleMenuRelationMapper.deleteByExample(example);

        //批量插入新关系
        for (Long menuId : menuIdList) {
            PowerRoleMenuRelation relation = new PowerRoleMenuRelation();

            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            relation.setGmtCreate(new Date());
            relation.setGmtModified(new Date());

            roleMenuRelationMapper.insert(relation);
        }

        return true;
    }

    /**
     * 更新角色资源列表
     *
     * @param roleId         角色编号
     * @param resourceIdList 资源列表
     * @return 是否成功
     */
    @Override
    public boolean allocResource(Long roleId, List<Long> resourceIdList) {
        // 先删除原有关系
        PowerRoleResourceRelationExample example = new PowerRoleResourceRelationExample();

        example.createCriteria()
                .andRoleIdEqualTo(roleId);

        roleResourceRelationMapper.deleteByExample(example);

        // 批量插入新关系
        for (Long resourceId : resourceIdList) {
            PowerRoleResourceRelation relation = new PowerRoleResourceRelation();

            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);

            relation.setGmtCreate(new Date());
            relation.setGmtModified(new Date());

            roleResourceRelationMapper.insert(relation);
        }

        return true;
    }

    /**
     * 判断角色是否存在
     *
     * @param id 角色编号
     * @return 是否存在
     */
    @Override
    public boolean count(Long id) {
        PowerRoleExample example = new PowerRoleExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return roleMapper.countByExample(example) > 0;
    }

}
