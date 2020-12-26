package cn.yujian95.little.admin.modules.power.service.impl;

import cn.yujian95.little.admin.modules.power.service.IPowerAdminRoleRelationService;
import cn.yujian95.little.mbg.modules.power.entity.PowerAdminRoleRelation;
import cn.yujian95.little.mbg.modules.power.mapper.PowerAdminRoleRelationMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限账号关联角色 接口实现类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
@Service
public class PowerAdminRoleRelationServiceImpl extends ServiceImpl<PowerAdminRoleRelationMapper, PowerAdminRoleRelation> implements IPowerAdminRoleRelationService {

        private static final Logger LOGGER = LoggerFactory.getLogger(PowerAdminRoleRelationServiceImpl.class);

        /**
         * 判断是否，不存在该记录编号
         *
         * @param id 记录编号
         * @return 是否不存在
         */
        @Override
        public boolean isNotExist(Long id) {

            QueryWrapper<PowerAdminRoleRelation> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(PowerAdminRoleRelation::getId, id);

            return count(wrapper) == 0;
        }
}
