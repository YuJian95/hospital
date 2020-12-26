package cn.yujian95.little.mobile.modules.system.service.impl;

import cn.yujian95.little.mbg.modules.system.entity.SystemLoginLog;
import cn.yujian95.little.mbg.modules.system.mapper.SystemLoginLogMapper;
import cn.yujian95.little.mobile.modules.system.service.ISystemLoginLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户登录日志 接口实现类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
@Service
public class SystemLoginLogServiceImpl extends ServiceImpl<SystemLoginLogMapper, SystemLoginLog> implements ISystemLoginLogService {

        private static final Logger LOGGER = LoggerFactory.getLogger(SystemLoginLogServiceImpl.class);

        /**
         * 判断是否，不存在该记录编号
         *
         * @param id 记录编号
         * @return 是否不存在
         */
        @Override
        public boolean isNotExist(Long id) {

            QueryWrapper<SystemLoginLog> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SystemLoginLog::getId, id);

            return count(wrapper) == 0;
        }
}
