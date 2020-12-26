package cn.yujian95.little.admin.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.yujian95.little.admin.modules.system.service.ISystemDictionaryService;
import cn.yujian95.little.mbg.modules.system.entity.SystemDictionary;
import cn.yujian95.little.mbg.modules.system.mapper.SystemDictionaryMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

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
     * 修改字典状态
     *
     * @param id     字典编号
     * @param status 状态 0：禁用，1：启用
     * @return 是否成功
     */
    @Override
    public boolean updateStatus(Long id, Integer status) {

        SystemDictionary dictionary = new SystemDictionary();

        dictionary.setId(id);
        dictionary.setStatus(status);
        dictionary.setGmtModified(new Date());

        return updateById(dictionary);
    }

    /**
     * 通过显示值 / 字典码、类型，获取字典列表
     *
     * @param keyword  显示值 / 字典码 关键词
     * @param type     类型
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 字典列表
     */
    @Override
    public Page<SystemDictionary> search(String keyword, String type, Integer pageNum, Integer pageSize) {
        Page<SystemDictionary> page = new Page<>(pageNum, pageSize);

        QueryWrapper<SystemDictionary> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");

        if (StrUtil.isNotEmpty(keyword)) {
            LambdaQueryWrapper<SystemDictionary> lambdaQueryWrapper = wrapper.lambda();

            lambdaQueryWrapper.like(SystemDictionary::getShowValue, keyword);
            lambdaQueryWrapper.or().like(SystemDictionary::getCode, keyword);
        }

        if (StrUtil.isNotEmpty(type)) {
            LambdaQueryWrapper<SystemDictionary> lambdaQueryWrapper = wrapper.lambda();
            lambdaQueryWrapper.like(SystemDictionary::getType, type);
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

        QueryWrapper<SystemDictionary> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SystemDictionary::getId, id);

        return count(wrapper) == 0;
    }
}
