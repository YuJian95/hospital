package cn.yujian95.base.mapper.dao;

import cn.yujian95.base.entity.PowerAccountRoleRelation;
import cn.yujian95.base.entity.PowerPermission;
import cn.yujian95.base.entity.PowerRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/20
 */

public interface PowerAccountRoleRelationDao {
    /**
     * 批量插入用户角色关系
     *
     * @param accountRoleRelationList 账号角色关系列表
     * @return 成功记录数
     */
    int insertList(List<PowerAccountRoleRelation> accountRoleRelationList);

    /**
     * 获取用于所有角色
     *
     * @param accountId 账号编号
     * @return 账号拥有角色列表
     */
    List<PowerRole> getRoleList(Long accountId);

    /**
     * 获取用户所有角色权限
     *
     * @param accountId 账号编号
     * @return 账号所有权限
     */
    List<PowerPermission> getRolePermissionList(Long accountId);

    /**
     * 获取用户所有权限(包括+-权限)
     *
     * @param accountId 账号编号
     * @return 所有权限
     */
    List<PowerPermission> getPermissionList(Long accountId);
}
