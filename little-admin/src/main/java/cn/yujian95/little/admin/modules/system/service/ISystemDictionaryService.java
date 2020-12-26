package cn.yujian95.little.admin.modules.system.service;

import cn.yujian95.little.mbg.modules.system.entity.SystemDictionary;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统数据字典 接口类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
public interface ISystemDictionaryService extends IService<SystemDictionary> {

    /**
     * 判断是否，不存在该记录编号
     *
     * @param id 记录编号
     * @return 是否不存在
     */
    boolean isNotExist(Long id);

    /**
     * 通过显示值 / 字典码、类型，获取字典列表
     *
     * @param keyword  显示值 / 字典码 关键词
     * @param type     类型
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 字典列表
     */
    Page<SystemDictionary> search(String keyword, String type, Integer pageNum, Integer pageSize);

    /**
     * 修改字典状态
     *
     * @param id     字典编号
     * @param status 状态 0：禁用，1：启用
     * @return 是否成功
     */
    boolean updateStatus(Long id, Integer status);
}
