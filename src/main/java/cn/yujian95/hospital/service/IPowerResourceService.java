package cn.yujian95.hospital.service;

import cn.yujian95.hospital.dto.param.PowerResourceParam;
import cn.yujian95.hospital.entity.PowerResource;

import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/12
 */

public interface IPowerResourceService {

    /**
     * 插入资源
     *
     * @param param 资源参数
     * @return 是否成功
     */
    boolean insert(PowerResourceParam param);

    /**
     * 更新资源
     *
     * @param id    资源编号
     * @param param 资源参数
     * @return 是否成功
     */
    boolean update(Long id, PowerResourceParam param);

    /**
     * 获取资源
     *
     * @param id 资源编号
     * @return 是否成功
     */
    Optional<PowerResource> get(Long id);

    /**
     * 删除资源
     *
     * @param id 资源编号
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 查找资源列表
     *
     * @param categoryId  资源分类编号
     * @param nameKeyword 名称关键词
     * @param urlKeyword  url关键词
     * @param pageNum     第几页
     * @param pageSize    页大小
     * @return 资源列表
     */
    List<PowerResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageNum, Integer pageSize);

    /**
     * 获取所有资源列表
     *
     * @return 资源列表
     */
    List<PowerResource> listAll();

    /**
     * 判断资源是否存在
     *
     * @param id 资源编号
     * @return 是否存在
     */
    boolean count(Long id);
}
