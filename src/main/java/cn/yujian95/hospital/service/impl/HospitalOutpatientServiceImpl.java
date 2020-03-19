package cn.yujian95.hospital.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.yujian95.hospital.dto.param.HospitalOutpatientParam;
import cn.yujian95.hospital.entity.*;
import cn.yujian95.hospital.mapper.HospitalOutpatientMapper;
import cn.yujian95.hospital.mapper.HospitalOutpatientRelationMapper;
import cn.yujian95.hospital.service.IHospitalOutpatientService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/5
 */
@Service
public class HospitalOutpatientServiceImpl implements IHospitalOutpatientService {

    @Resource
    private HospitalOutpatientMapper outpatientMapper;

    @Resource
    private HospitalOutpatientRelationMapper outpatientRelationMapper;

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
     * 获取门诊名称
     *
     * @param id 门诊编号
     * @return 门诊名称
     */
    @Override
    public String getName(Long id) {

        Optional<HospitalOutpatient> outpatient = getOptional(id);

        return outpatient.map(HospitalOutpatient::getName).orElse("未知");

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
     * 通过医院编号，专科编号，查找门诊列表
     *
     * @param hospitalId 医院编号
     * @param specialId  专科编号
     * @param pageNum    第几页
     * @param pageSize   页大小
     * @return 门诊列表
     */
    @Override
    public List<HospitalOutpatient> list(Long hospitalId, Long specialId, Integer pageNum, Integer pageSize) {

        // 获取医院含有的门诊列表
        HospitalOutpatientRelationExample outpatientRelationExample = new HospitalOutpatientRelationExample();

        outpatientRelationExample.createCriteria()
                .andHospitalIdEqualTo(hospitalId);

        // 门诊编号列表
        List<Long> outpatientIdList = getOutpatientIdList(outpatientRelationExample);

        // 即医院没有添加的门诊
        if (CollectionUtil.isEmpty(outpatientIdList)) {
            return null;
        }

        // 设置分页
        PageHelper.startPage(pageNum, pageSize);

        // 筛选门诊中符合的专科
        HospitalOutpatientExample example = new HospitalOutpatientExample();

        example.createCriteria()
                .andSpecialIdEqualTo(specialId)
                .andIdIn(outpatientIdList);

        return outpatientMapper.selectByExample(example);
    }

    /**
     * 查找门诊列表
     *
     * @param specialId 门诊编号
     * @param pageNum   第几页
     * @param pageSize  页大小
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

    /**
     * 查找未添加到医院的门诊编号
     *
     * @param specialId 专科编号
     * @param pageNum   第几页
     * @param pageSize  页大小
     * @return 门诊列表
     */
    @Override
    public List<HospitalOutpatient> listAlone(Long specialId, Integer pageNum, Integer pageSize) {

        // 获取医院含有的门诊列表
        HospitalOutpatientRelationExample outpatientRelationExample = new HospitalOutpatientRelationExample();

        outpatientRelationExample.createCriteria()
                .andHospitalIdIsNotNull();

        // 门诊编号列表
        List<Long> outpatientIdList = getOutpatientIdList(outpatientRelationExample);

        // 设置分页
        PageHelper.startPage(pageNum, pageSize);

        // 筛选门诊中符合的专科
        HospitalOutpatientExample example = new HospitalOutpatientExample();

        HospitalOutpatientExample.Criteria criteria = example.createCriteria();

        // 当存在已添加的记录时
        if (CollectionUtil.isNotEmpty(outpatientIdList)) {
            criteria.andIdNotIn(outpatientIdList);
        }

        if (specialId != null) {
            criteria.andSpecialIdEqualTo(specialId);
        }

        return outpatientMapper.selectByExample(example);
    }

    /**
     * 输入筛选条件，获取门诊编号列表
     *
     * @param example 筛选条件
     * @return 门诊编号列表
     */
    private List<Long> getOutpatientIdList(HospitalOutpatientRelationExample example) {

        // 门诊编号列表
        return outpatientRelationMapper.selectByExample(example).stream()
                .map(HospitalOutpatientRelation::getOutpatientId)
                // 去除重复项
                .distinct()
                .collect(Collectors.toList());
    }
}
