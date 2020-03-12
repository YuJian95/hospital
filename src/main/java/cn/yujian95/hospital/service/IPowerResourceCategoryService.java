package cn.yujian95.hospital.service;

import cn.yujian95.hospital.dto.param.PowerResourceCategoryParam;
import cn.yujian95.hospital.entity.PowerResourceCategory;

import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/12
 */

public interface IPowerResourceCategoryService {

    /**
     * 添加资源分类
     *
     * @param param 资源分类参数
     * @return 是否成功
     */
    boolean insert(PowerResourceCategoryParam param);

    /**
     * 更新资源分类
     *
     * @param id    资源分类编号
     * @param param 资源分类参数
     * @return 是否成功
     */
    boolean update(Long id, PowerResourceCategoryParam param);

    /**
     * 删除资源分类
     *
     * @param id 资源分类编号
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 获取所有资源分类列表
     *
     * @return 资源分类列表
     */
    List<PowerResourceCategory> listAll();

    /**
     * 判断是否存在资源分类
     *
     * @param id 分类编号
     * @return 是否存在
     */
    boolean count(Long id);
}
