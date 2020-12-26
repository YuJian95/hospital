package cn.yujian95.little.admin.modules.power.service.impl;

import cn.yujian95.little.admin.modules.power.service.IPowerResourceCategoryService;
import cn.yujian95.little.mbg.modules.power.entity.PowerResourceCategory;
import cn.yujian95.little.mbg.modules.power.mapper.PowerResourceCategoryMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限资源分类  接口实现类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
@Service
public class PowerResourceCategoryServiceImpl extends ServiceImpl<PowerResourceCategoryMapper, PowerResourceCategory> implements IPowerResourceCategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PowerResourceCategoryServiceImpl.class);

    /**
     * 判断是否，不存在该记录编号
     *
     * @param id 记录编号
     * @return 是否不存在
     */
    @Override
    public boolean isNotExist(Long id) {

        QueryWrapper<PowerResourceCategory> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PowerResourceCategory::getId, id);

        return count(wrapper) == 0;
    }

    /**
     * 获取全部资源分类排序
     *
     * @return 资源分类
     */
    @Override
    public List<PowerResourceCategory> listAll() {
        QueryWrapper<PowerResourceCategory> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByDesc(PowerResourceCategory::getSort);
        return list(wrapper);
    }
}
