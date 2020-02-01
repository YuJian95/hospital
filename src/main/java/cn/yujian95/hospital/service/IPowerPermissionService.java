package cn.yujian95.hospital.service;

import cn.yujian95.hospital.dto.PowerPermissionNodeDTO;
import cn.yujian95.hospital.dto.param.PowerPermissionParam;
import cn.yujian95.hospital.entity.PowerPermission;

import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/19
 */

public interface IPowerPermissionService {

    /**
     * 插入权限权值
     *
     * @param param 权限权值参数
     * @return 是否成功
     */
    boolean insert(PowerPermissionParam param);

    /**
     * 更新权限权值
     *
     * @param id    权值编号
     * @param param 权限权值参数
     * @return 是否成功
     */
    boolean update(Long id, PowerPermissionParam param);

    /**
     * 根据权限名称、类型，查找权限权值
     *
     * @param name     权限名称
     * @param type     权限类型 0->目录；1->菜单；2->按钮（接口绑定权限）
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 权限列表
     */
    List<PowerPermission> search(String name, Integer type, Integer pageNum, Integer pageSize);

    /**
     * 判断权限是否存在
     *
     * @param id 权限编号
     * @return 是否存在
     */
    boolean count(Long id);

    /**
     * 以层级结构返回所有权限
     *
     * @return 以层级结构返回所有权限
     */
    List<PowerPermissionNodeDTO> treeList();

    /**
     * 通过类型获取父级权限
     *
     * @param type 权限类型 0->目录；1->菜单；2->按钮（接口绑定权限）
     * @return 父权限列表
     */
    List<PowerPermission> listParent(Integer type);
}
