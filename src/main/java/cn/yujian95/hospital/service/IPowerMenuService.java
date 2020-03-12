package cn.yujian95.hospital.service;

import cn.yujian95.hospital.dto.PowerMenuNode;
import cn.yujian95.hospital.dto.param.PowerMenuParam;
import cn.yujian95.hospital.entity.PowerMenu;

import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/12
 */

public interface IPowerMenuService {

    /**
     * 插入菜单
     *
     * @param param 菜单参数
     * @return 是否成功
     */
    boolean insert(PowerMenuParam param);

    /**
     * 更新菜单
     *
     * @param id    菜单编号
     * @param param 菜单参数
     * @return 是否成功
     */
    boolean update(Long id, PowerMenuParam param);

    /**
     * 更新菜单隐藏情况
     *
     * @param id     菜单编号
     * @param hidden 隐藏状态：0 隐藏，1 显示
     * @return 是否成功
     */
    boolean updateHidden(Long id, Integer hidden);

    /**
     * 删除菜单
     *
     * @param id 菜单编号
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 判断是否存在
     *
     * @param id 菜单编号
     * @return 是否存在
     */
    boolean count(Long id);

    /**
     * 获取菜单信息
     *
     * @param id 菜单编号
     * @return 菜单信息
     */
    Optional<PowerMenu> get(Long id);

    /**
     * 查找菜单列表
     *
     * @param parentId 父菜单编号
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 菜单列表
     */
    List<PowerMenu> list(Long parentId, Integer pageNum, Integer pageSize);

    /**
     * 获取所有菜单以树状返回
     *
     * @return 菜单节点
     */
    List<PowerMenuNode> treeList();
}
