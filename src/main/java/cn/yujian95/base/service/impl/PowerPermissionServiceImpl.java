package cn.yujian95.base.service.impl;

import cn.yujian95.base.dto.PowerPermissionNodeDTO;
import cn.yujian95.base.dto.param.PowerPermissionParam;
import cn.yujian95.base.entity.PowerPermission;
import cn.yujian95.base.entity.PowerPermissionExample;
import cn.yujian95.base.mapper.PowerPermissionMapper;
import cn.yujian95.base.service.IPowerPermissionService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/25
 */

@Service
public class PowerPermissionServiceImpl implements IPowerPermissionService {

    @Resource
    private PowerPermissionMapper permissionMapper;

    /**
     * 插入权限权值
     *
     * @param param 权限权值参数
     * @return 是否成功
     */
    @Override
    public boolean insert(PowerPermissionParam param) {
        PowerPermission permission = new PowerPermission();
        BeanUtils.copyProperties(param, permission);
        permission.setGmtCreate(new Date());
        permission.setGmtModified(new Date());
        return permissionMapper.insertSelective(permission) > 0;
    }

    /**
     * 更新权限权值
     *
     * @param id    权值编号
     * @param param 权限权值参数
     * @return 是否成功
     */
    @Override
    public boolean update(Long id, PowerPermissionParam param) {
        PowerPermission permission = new PowerPermission();
        BeanUtils.copyProperties(param, permission);
        permission.setId(id);
        permission.setGmtModified(new Date());
        return permissionMapper.updateByPrimaryKeySelective(permission) > 0;
    }

    /**
     * 根据权限名称、类型，查找权限权值
     *
     * @param name     权限名称
     * @param type     权限类型 0->目录；1->菜单；2->按钮（接口绑定权限）
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 权限列表
     */
    @Override
    public List<PowerPermission> search(String name, Integer type, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        PowerPermissionExample example = new PowerPermissionExample();
        PowerPermissionExample.Criteria criteria = example.createCriteria();


        if (name != null) {
            criteria.andNameLike("%" + name + "%");
        }

        if (type != -1) {
            criteria.andTypeEqualTo(type);
        }

        return permissionMapper.selectByExample(example);
    }

    /**
     * 判断权限是否存在
     *
     * @param id 权限编号
     * @return 是否存在
     */
    @Override
    public boolean count(Long id) {
        PowerPermissionExample example = new PowerPermissionExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return permissionMapper.countByExample(example) > 0;
    }

    /**
     * 以层级结构返回所有权限
     *
     * @return 以层级结构返回所有权限
     */
    @Override
    public List<PowerPermissionNodeDTO> treeList() {
        List<PowerPermission> permissionList = permissionMapper.selectByExample(new PowerPermissionExample());
        return permissionList.stream()
                .filter(permission -> 0 == permission.getPid())
                .map(permission -> covert(permission, permissionList))
                .collect(Collectors.toList());

    }

    /**
     * 通过类型获取父级权限
     *
     * @param type 权限类型 0->目录；1->菜单；2->按钮（接口绑定权限）
     * @return 父权限列表
     */
    @Override
    public List<PowerPermission> listParent(Integer type) {
        // 顶级权限,无父权限，父权限为0
        if (type == 0) {
            List<PowerPermission> list = new ArrayList<>();

            PowerPermission permission = new PowerPermission();
            permission.setName("无父权权限");
            permission.setId((long) 0);

            list.add(permission);
            return list;
        }

        // 查找父权限
        Integer pType = type - 1;
        PowerPermissionExample example = new PowerPermissionExample();
        example.createCriteria()
                .andTypeEqualTo(pType);

        return permissionMapper.selectByExample(example);
    }

    /**
     * 将权限转换为带有子级的权限对象
     * 当找不到子级权限的时候map操作不会再递归调用 covert
     *
     * @param permission     权限
     * @param permissionList 所有权限列表
     * @return 父权限以及子权限列表
     */
    private PowerPermissionNodeDTO covert(PowerPermission permission, List<PowerPermission> permissionList) {
        PowerPermissionNodeDTO node = new PowerPermissionNodeDTO();

        BeanUtils.copyProperties(permission, node);

        List<PowerPermissionNodeDTO> children = permissionList.stream()
                .filter(subPermission -> subPermission.getPid().equals(permission.getId()))
                .map(subPermission -> covert(subPermission, permissionList))
                .collect(Collectors.toList());

        node.setChildren(children);

        return node;
    }
}
