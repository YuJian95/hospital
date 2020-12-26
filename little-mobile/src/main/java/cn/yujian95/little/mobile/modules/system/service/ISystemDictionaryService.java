package cn.yujian95.little.mobile.modules.system.service;

import cn.yujian95.little.mbg.modules.system.entity.SystemDictionary;
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
}
