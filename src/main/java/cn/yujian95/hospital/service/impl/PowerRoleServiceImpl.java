package cn.yujian95.hospital.service.impl;

import cn.yujian95.hospital.dto.param.PowerRoleParam;
import cn.yujian95.hospital.dto.param.StatusParam;
import cn.yujian95.hospital.entity.*;
import cn.yujian95.hospital.mapper.PowerRoleMapper;
import cn.yujian95.hospital.mapper.PowerRolePermissionRelationMapper;
import cn.yujian95.hospital.mapper.dao.PowerRolePermissionRelationDao;
import cn.yujian95.hospital.service.IPowerRoleService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private PowerRolePermissionRelationMapper rolePermissionRelationMapper;

    @Resource
    private PowerRolePermissionRelationDao rolePermissionRelationDao;

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
     * 获取角色所有权限
     *
     * @param roleId 角色编号
     * @return 权限列表
     */
    @Override
    public List<PowerPermission> listPermission(Long roleId) {
        return rolePermissionRelationDao.listPermission(roleId);
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
     * 修改指定角色权限
     *
     * @param roleId         角色编号
     * @param permissionList 权限列表
     * @return 成功记录
     */
    @Override
    public int updatePermission(Long roleId, List<Long> permissionList) {
        // 删除原有关系
        PowerRolePermissionRelationExample example = new PowerRolePermissionRelationExample();

        example.createCriteria()
                .andRoleIdEqualTo(roleId);

        rolePermissionRelationMapper.deleteByExample(example);

        // 批量添加新关系
        List<PowerRolePermissionRelation> relationList = new ArrayList<>();

        for (Long permissionId : permissionList) {
            PowerRolePermissionRelation relation = new PowerRolePermissionRelation();

            relation.setRoleId(roleId);
            relation.setPermissionId(permissionId);

            Date date = new Date();

            relation.setGmtCreate(date);
            relation.setGmtModified(date);

            relationList.add(relation);
        }

        return rolePermissionRelationDao.insertList(relationList);
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
