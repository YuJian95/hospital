package cn.yujian95.little.mobile.modules.system.service.impl;

import cn.yujian95.little.mbg.modules.system.entity.SystemDictionary;
import cn.yujian95.little.mbg.modules.system.mapper.SystemDictionaryMapper;
import cn.yujian95.little.mobile.modules.system.service.ISystemDictionaryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统数据字典 接口实现类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
@Service
public class SystemDictionaryServiceImpl extends ServiceImpl<SystemDictionaryMapper, SystemDictionary> implements ISystemDictionaryService {

        private static final Logger LOGGER = LoggerFactory.getLogger(SystemDictionaryServiceImpl.class);

        /**
         * 判断是否，不存在该记录编号
         *
         * @param id 记录编号
         * @return 是否不存在
         */
        @Override
        public boolean isNotExist(Long id) {

            QueryWrapper<SystemDictionary> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SystemDictionary::getId, id);

            return count(wrapper) == 0;
        }
}
