package cn.yujian95.base.mapper.dao;

import cn.yujian95.base.entity.PowerPermission;
import cn.yujian95.base.entity.PowerRolePermissionRelation;

import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/20
 */

public interface PowerRolePermissionRelationDao {
    /**
     * 批量插入用户角色关系
     *
     * @param relationList 用户角色关系列表
     * @return 成功记录数
     */
    int insertList(List<PowerRolePermissionRelation> relationList);

    /**
     * 获取角色所有权限(包括+-权限)
     *
     * @param roleId 账号编号
     * @return 角色所有权限
     */
    List<PowerPermission> listPermission(Long roleId);

}
