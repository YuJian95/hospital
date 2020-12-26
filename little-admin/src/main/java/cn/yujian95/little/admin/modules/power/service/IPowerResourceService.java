package cn.yujian95.little.admin.modules.power.service;

import cn.yujian95.little.mbg.modules.power.entity.PowerResource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 权限资源  接口类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
public interface IPowerResourceService extends IService<PowerResource> {

    /**
     * 判断是否，不存在该记录编号
     *
     * @param id 记录编号
     * @return 是否不存在
     */
     boolean isNotExist(Long id);

    /**
     * 分页查询资源
     *
     * @param categoryId  资源分类
     * @param nameKeyword 名称
     * @param urlKeyword  url
     * @param pageNum     第几页
     * @param pageSize    页大小
     * @return 资源列表
     */
    Page<PowerResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageNum, Integer pageSize);

}
