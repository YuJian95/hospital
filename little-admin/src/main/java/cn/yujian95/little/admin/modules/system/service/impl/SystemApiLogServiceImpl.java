package cn.yujian95.little.admin.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.yujian95.little.admin.modules.system.service.ISystemApiLogService;
import cn.yujian95.little.mbg.modules.system.entity.SystemApiLog;
import cn.yujian95.little.mbg.modules.system.mapper.SystemApiLogMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统接口日志  接口实现类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
@Service
public class SystemApiLogServiceImpl extends ServiceImpl<SystemApiLogMapper, SystemApiLog> implements ISystemApiLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemApiLogServiceImpl.class);

    /**
     * 通过用户名、url，获取API日志
     *
     * @param keyword  用户名
     * @param uri      uri
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return API日志列表
     */
    @Override
    public Page<SystemApiLog> search(String keyword, String uri, Integer pageNum, Integer pageSize) {
        Page<SystemApiLog> page = new Page<>(pageNum, pageSize);

        QueryWrapper<SystemApiLog> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");

        if (StrUtil.isNotEmpty(keyword)) {
            LambdaQueryWrapper<SystemApiLog> lambdaQueryWrapper = wrapper.lambda();

            lambdaQueryWrapper.like(SystemApiLog::getUsername, keyword);
        }

        if (StrUtil.isNotEmpty(uri)) {
            LambdaQueryWrapper<SystemApiLog> lambdaQueryWrapper = wrapper.lambda();
            lambdaQueryWrapper.like(SystemApiLog::getUri, uri);
        }

        return page(page, wrapper);
    }

    /**
     * 判断是否，不存在该记录编号
     *
     * @param id 记录编号
     * @return 是否不存在
     */
    @Override
    public boolean isNotExist(Long id) {

        QueryWrapper<SystemApiLog> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SystemApiLog::getId, id);

        return count(wrapper) == 0;
    }
}
