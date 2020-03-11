package cn.yujian95.hospital.service;

import cn.yujian95.hospital.dto.param.PowerRoleParam;
import cn.yujian95.hospital.dto.param.StatusParam;
import cn.yujian95.hospital.entity.PowerPermission;
import cn.yujian95.hospital.entity.PowerRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/19
 */

public interface IPowerRoleService {

    /**
     * 添加角色
     *
     * @param param 角色信息参数
     * @return 成功记录数
     */
    boolean insert(PowerRoleParam param);

    /**
     * 修改角色信息
     *
     * @param roleId 角色编号
     * @param param  角色信息参数
     * @return 成功记录数
     */
    boolean update(Long roleId, PowerRoleParam param);


    /**
     * 更新角色状态
     *
     * @param roleId 角色编号
     * @param param  状态参数： 0 关闭，1 开启
     * @return 是否成功
     */
    boolean updateStatus(Long roleId, StatusParam param);

    /**
     * 删除角色信息
     *
     * @param roleId 角色编号
     * @return 是否成功
     */
    boolean delete(Long roleId);

    /**
     * 获取角色所有权限
     *
     * @param roleId 角色编号
     * @return 权限列表
     */
    List<PowerPermission> listPermission(Long roleId);

    /**
     * 修改指定角色权限
     *
     * @param roleId         角色编号
     * @param permissionList 权限列表
     * @return 成功记录
     */
    @Transactional
    int updatePermission(Long roleId, List<Long> permissionList);

    /**
     * 获取角色列表
     *
     * @param chineseName 中文名
     * @param pageNum     第几页
     * @param pageSize    页大小
     * @return 角色列表
     */
    List<PowerRole> list(String chineseName, Integer pageNum, Integer pageSize);

    /**
     * 判断角色是否存在
     *
     * @param id 角色编号
     * @return 是否存在
     */
    boolean count(Long id);
}
