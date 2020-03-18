package cn.yujian95.hospital.service.impl;

import cn.yujian95.hospital.dto.param.HospitalOutpatientParam;
import cn.yujian95.hospital.entity.HospitalOutpatient;
import cn.yujian95.hospital.entity.HospitalOutpatientExample;
import cn.yujian95.hospital.mapper.HospitalOutpatientMapper;
import cn.yujian95.hospital.service.IHospitalOutpatientService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/5
 */
@Service
public class HospitalOutpatientServiceImpl implements IHospitalOutpatientService {

    @Resource
    private HospitalOutpatientMapper outpatientMapper;

    /**
     * 添加门诊信息
     *
     * @param param 门诊信息参数
     * @return 是否成功
     */
    @Override
    public boolean insert(HospitalOutpatientParam param) {
        HospitalOutpatient special = new HospitalOutpatient();

        BeanUtils.copyProperties(param, special);

        special.setGmtCreate(new Date());
        special.setGmtModified(new Date());

        return outpatientMapper.insertSelective(special) > 0;
    }

    /**
     * 更新门诊信息
     *
     * @param id    门诊编号
     * @param param 门诊信息参数
     * @return 是否成功
     */
    @Override
    public boolean update(Long id, HospitalOutpatientParam param) {
        HospitalOutpatient special = new HospitalOutpatient();

        BeanUtils.copyProperties(param, special);

        special.setId(id);
        special.setGmtModified(new Date());

        return outpatientMapper.updateByPrimaryKeySelective(special) > 0;
    }

    /**
     * 删除门诊信息
     *
     * @param id 门诊编号
     * @return 是否成功
     */
    @Override
    public boolean delete(Long id) {
        return outpatientMapper.deleteByPrimaryKey(id) > 0;
    }

    /**
     * 获取门诊信息
     *
     * @param id 门诊编号
     * @return 门诊信息
     */
    @Override
    public Optional<HospitalOutpatient> getOptional(Long id) {
        return Optional.ofNullable(outpatientMapper.selectByPrimaryKey(id));
    }

    /**
     * 判断门诊信息是否存在
     *
     * @param id 门诊信息
     * @return 是否存在
     */
    @Override
    public boolean count(Long id) {
        HospitalOutpatientExample example = new HospitalOutpatientExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return outpatientMapper.countByExample(example) > 0;
    }

    /**
     * 查找门诊信息
     *
     * @param name     门诊名称
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 门诊列表
     */
    @Override
    public List<HospitalOutpatient> list(String name, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        HospitalOutpatientExample example = new HospitalOutpatientExample();

        if (!StringUtils.isEmpty(name)) {
            example.createCriteria()
                    .andNameLike("%" + name + "%");
        }

        return outpatientMapper.selectByExample(example);
    }

    /**
     * 查找门诊列表
     *
     * @param specialId  门诊编号
     * @param pageNum    第几页
     * @param pageSize   页大小
     * @return 门诊列表
     */
    @Override
    public List<HospitalOutpatient> list(Long specialId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        HospitalOutpatientExample example = new HospitalOutpatientExample();

        HospitalOutpatientExample.Criteria criteria = example.createCriteria();

        if (specialId != 0) {
            criteria.andSpecialIdEqualTo(specialId);
        }

        return outpatientMapper.selectByExample(example);
    }
}
