package cn.yujian95.hospital.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.yujian95.hospital.dto.param.PowerResourceParam;
import cn.yujian95.hospital.entity.PowerResource;
import cn.yujian95.hospital.entity.PowerResourceExample;
import cn.yujian95.hospital.mapper.PowerResourceMapper;
import cn.yujian95.hospital.service.IPowerResourceService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/12
 */

@Service
public class PowerResourceServiceImpl implements IPowerResourceService {

    @Resource
    private PowerResourceMapper resourceMapper;

    /**
     * 插入资源
     *
     * @param param 资源参数
     * @return 是否成功
     */
    @Override
    public boolean insert(PowerResourceParam param) {

        PowerResource resource = new PowerResource();

        BeanUtils.copyProperties(param, resource);

        resource.setGmtCreate(new Date());
        resource.setGmtModified(new Date());

        return resourceMapper.insertSelective(resource) > 0;
    }

    /**
     * 更新资源
     *
     * @param id    资源编号
     * @param param 资源参数
     * @return 是否成功
     */
    @Override
    public boolean update(Long id, PowerResourceParam param) {
        PowerResource resource = new PowerResource();

        BeanUtils.copyProperties(param, resource);

        resource.setId(id);
        resource.setGmtModified(new Date());

        return resourceMapper.updateByPrimaryKeySelective(resource) > 0;
    }

    /**
     * 获取资源
     *
     * @param id 资源编号
     * @return 是否成功
     */
    @Override
    public Optional<PowerResource> get(Long id) {
        return Optional.ofNullable(resourceMapper.selectByPrimaryKey(id));
    }

    /**
     * 删除资源
     *
     * @param id 资源编号
     * @return 是否成功
     */
    @Override
    public boolean delete(Long id) {
        return resourceMapper.deleteByPrimaryKey(id) > 0;
    }

    /**
     * 查找资源列表
     *
     * @param categoryId  资源分类编号
     * @param nameKeyword 名称关键词
     * @param urlKeyword  url关键词
     * @param pageNum     第几页
     * @param pageSize    页大小
     * @return 资源列表
     */
    @Override
    public List<PowerResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        PowerResourceExample example = new PowerResourceExample();

        PowerResourceExample.Criteria criteria = example.createCriteria();

        if (categoryId != null) {
            criteria.andCategoryIdEqualTo(categoryId);
        }

        if (StrUtil.isNotEmpty(nameKeyword)) {
            criteria.andNameLike('%' + nameKeyword + '%');
        }

        if (StrUtil.isNotEmpty(urlKeyword)) {
            criteria.andUrlLike('%' + urlKeyword + '%');
        }

        return resourceMapper.selectByExample(example);

    }

    /**
     * 判断资源是否存在
     *
     * @param id 资源编号
     * @return 是否存在
     */
    @Override
    public boolean count(Long id) {
        PowerResourceExample example = new PowerResourceExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return resourceMapper.countByExample(example) > 0;
    }

    /**
     * 获取所有资源列表
     *
     * @return 资源列表
     */
    @Override
    public List<PowerResource> listAll() {

        PowerResourceExample example = new PowerResourceExample();

        return resourceMapper.selectByExample(example);
    }
}
