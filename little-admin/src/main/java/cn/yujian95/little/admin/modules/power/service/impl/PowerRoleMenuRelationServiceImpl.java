package cn.yujian95.little.admin.modules.power.service.impl;

import cn.yujian95.little.admin.modules.power.service.IPowerRoleMenuRelationService;
import cn.yujian95.little.mbg.modules.power.entity.PowerRoleMenuRelation;
import cn.yujian95.little.mbg.modules.power.mapper.PowerRoleMenuRelationMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限角色关联菜单  接口实现类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
@Service
public class PowerRoleMenuRelationServiceImpl extends ServiceImpl<PowerRoleMenuRelationMapper, PowerRoleMenuRelation> implements IPowerRoleMenuRelationService {

        private static final Logger LOGGER = LoggerFactory.getLogger(PowerRoleMenuRelationServiceImpl.class);

        /**
         * 判断是否，不存在该记录编号
         *
         * @param id 记录编号
         * @return 是否不存在
         */
        @Override
        public boolean isNotExist(Long id) {

            QueryWrapper<PowerRoleMenuRelation> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(PowerRoleMenuRelation::getId, id);

            return count(wrapper) == 0;
        }
}
