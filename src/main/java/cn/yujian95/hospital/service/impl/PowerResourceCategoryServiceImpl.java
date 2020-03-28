package cn.yujian95.hospital.service.impl;

import cn.yujian95.hospital.dto.param.PowerResourceCategoryParam;
import cn.yujian95.hospital.entity.PowerResourceCategory;
import cn.yujian95.hospital.entity.PowerResourceCategoryExample;
import cn.yujian95.hospital.mapper.PowerResourceCategoryMapper;
import cn.yujian95.hospital.service.IPowerResourceCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/12
 */

@Service
public class PowerResourceCategoryServiceImpl implements IPowerResourceCategoryService {

    @Resource
    private PowerResourceCategoryMapper resourceCategoryMapper;

    /**
     * 添加资源分类
     *
     * @param param 资源分类参数
     * @return 是否成功
     */
    @Override
    public boolean insert(PowerResourceCategoryParam param) {

        PowerResourceCategory category = new PowerResourceCategory();

        BeanUtils.copyProperties(param, category);

        category.setGmtCreate(new Date());
        category.setGmtModified(new Date());

        return resourceCategoryMapper.insertSelective(category) > 0;
    }

    /**
     * 更新资源分类
     *
     * @param id    资源分类编号
     * @param param 资源分类参数
     * @return 是否成功
     */
    @Override
    public boolean update(Long id, PowerResourceCategoryParam param) {
        PowerResourceCategory category = new PowerResourceCategory();

        BeanUtils.copyProperties(param, category);

        category.setId(id);
        category.setGmtModified(new Date());

        return resourceCategoryMapper.updateByPrimaryKey(category) > 0;
    }

    /**
     * 删除资源分类
     *
     * @param id 资源分类编号
     * @return 是否成功
     */
    @Override
    public boolean delete(Long id) {
        return resourceCategoryMapper.deleteByPrimaryKey(id) > 0;

    }

    /**
     * 判断是否存在资源分类
     *
     * @param id 分类编号
     * @return 是否存在
     */
    @Override
    public boolean count(Long id) {
        PowerResourceCategoryExample example = new PowerResourceCategoryExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return resourceCategoryMapper.countByExample(example) > 0;
    }

    /**
     * 获取所有资源分类列表
     *
     * @return 资源分类列表
     */
    @Override
    public List<PowerResourceCategory> listAll() {
        PowerResourceCategoryExample example = new PowerResourceCategoryExample();

        example.setOrderByClause("sort desc");

        return resourceCategoryMapper.selectByExample(example);

    }
}
