package cn.yujian95.little.admin.modules.power.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.yujian95.little.admin.modules.power.service.IPowerResourceService;
import cn.yujian95.little.mbg.modules.power.entity.PowerResource;
import cn.yujian95.little.mbg.modules.power.mapper.PowerResourceMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限资源  接口实现类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
@Service
public class PowerResourceServiceImpl extends ServiceImpl<PowerResourceMapper, PowerResource> implements IPowerResourceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PowerResourceServiceImpl.class);

    /**
     * 判断是否，不存在该记录编号
     *
     * @param id 记录编号
     * @return 是否不存在
     */
    @Override
    public boolean isNotExist(Long id) {

        QueryWrapper<PowerResource> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PowerResource::getId, id);

        return count(wrapper) == 0;
    }

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
    @Override
    public Page<PowerResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageNum, Integer pageSize) {

        Page<PowerResource> page = new Page<>(pageNum, pageSize);
        QueryWrapper<PowerResource> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<PowerResource> lambda = wrapper.lambda();

        if (categoryId != null) {
            lambda.eq(PowerResource::getCategoryId, categoryId);
        }

        if (StrUtil.isNotEmpty(nameKeyword)) {
            lambda.like(PowerResource::getName, nameKeyword);
        }

        if (StrUtil.isNotEmpty(urlKeyword)) {
            lambda.like(PowerResource::getUrl, urlKeyword);
        }

        return page(page, wrapper);
    }
}
