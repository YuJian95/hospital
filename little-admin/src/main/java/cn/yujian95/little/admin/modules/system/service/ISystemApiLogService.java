package cn.yujian95.little.admin.modules.system.service;

import cn.yujian95.little.mbg.modules.system.entity.SystemApiLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统接口日志  接口类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
public interface ISystemApiLogService extends IService<SystemApiLog> {

    /**
     * 判断是否，不存在该记录编号
     *
     * @param id 记录编号
     * @return 是否不存在
     */
    boolean isNotExist(Long id);

    /**
     * 通过用户名、url，获取API日志
     *
     * @param keyword  用户名
     * @param uri      uri
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return API日志列表
     */
    Page<SystemApiLog> search(String keyword, String uri, Integer pageNum, Integer pageSize);
}
