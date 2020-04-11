package cn.yujian95.hospital.service.impl;

import cn.yujian95.hospital.dto.param.HospitalInfoParam;
import cn.yujian95.hospital.dto.param.HospitalOutpatientRelationParam;
import cn.yujian95.hospital.dto.param.HospitalSpecialRelationParam;
import cn.yujian95.hospital.entity.*;
import cn.yujian95.hospital.mapper.HospitalInfoMapper;
import cn.yujian95.hospital.mapper.HospitalOutpatientRelationMapper;
import cn.yujian95.hospital.mapper.HospitalSpecialRelationMapper;
import cn.yujian95.hospital.service.IHospitalInfoService;
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
 * @date 2020/2/3
 */

@Service
public class
HospitalInfoServiceImpl implements IHospitalInfoService {

    @Resource
    private HospitalInfoMapper infoMapper;

    @Resource
    private HospitalSpecialRelationMapper specialRelationMapper;

    @Resource
    private HospitalOutpatientRelationMapper outpatientRelationMapper;

    /**
     * 添加医院信息
     *
     * @param param 医院信息参数
     * @return 是否成功
     */
    @Override
    public boolean insert(HospitalInfoParam param) {
        HospitalInfo info = new HospitalInfo();

        BeanUtils.copyProperties(param, info);

        info.setGmtCreate(new Date());
        info.setGmtModified(new Date());

        return infoMapper.insertSelective(info) > 0;
    }

    /**
     * 更新医院信息
     *
     * @param id    医院编号
     * @param param 医院信息参数
     * @return 是否成功
     */
    @Override
    public boolean update(Long id, HospitalInfoParam param) {
        HospitalInfo info = new HospitalInfo();

        BeanUtils.copyProperties(param, info);

        info.setId(id);
        info.setGmtModified(new Date());

        return infoMapper.updateByPrimaryKeySelective(info) > 0;
    }

    /**
     * 获取医院名称
     *
     * @param id 医院编号
     * @return 医院名称，否则返回未知
     */
    @Override
    public String getName(Long id) {
        return getOptional(id).map(HospitalInfo::getName).orElse("未知");
    }

    /**
     * 获取医院信息
     *
     * @param id 医院编号
     * @return 医院信息
     */
    @Override
    public Optional<HospitalInfo> getOptional(Long id) {

        return Optional.ofNullable(infoMapper.selectByPrimaryKey(id));
    }

    /**
     * 删除医院信息
     *
     * @param id 医院编号
     * @return 是否成功
     */
    @Override
    public boolean delete(Long id) {
        return infoMapper.deleteByPrimaryKey(id) > 0;
    }

    /**
     * 判断电话是否存在
     *
     * @param phone 电话
     * @return 是否存在
     */
    @Override
    public boolean count(String phone) {
        HospitalInfoExample example = new HospitalInfoExample();

        example.createCriteria()
                .andPhoneEqualTo(phone);

        return infoMapper.countByExample(example) > 0;
    }

    /**
     * 判断医院信息是否存在
     *
     * @param id 医院编号
     * @return 是否存在
     */
    @Override
    public boolean count(Long id) {
        HospitalInfoExample example = new HospitalInfoExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return infoMapper.countByExample(example) > 0;
    }

    /**
     * 查找医院列表
     *
     * @param name     医院名称
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 医院列表
     */
    @Override
    public List<HospitalInfo> list(String name, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        HospitalInfoExample example = new HospitalInfoExample();

        if (!StringUtils.isEmpty(name)) {
            example.createCriteria()
                    .andNameLike("%" + name + "%");

        }

        return infoMapper.selectByExample(example);
    }

    /**
     * 插入专科到医院中去
     *
     * @param param 医院专科关系参数
     * @return 是否成功
     */
    @Override
    public boolean insertSpecialRelation(HospitalSpecialRelationParam param) {
        HospitalSpecialRelation relation = new HospitalSpecialRelation();

        BeanUtils.copyProperties(param, relation);

        relation.setGmtCreate(new Date());
        relation.setGmtModified(new Date());

        return specialRelationMapper.insertSelective(relation) > 0;
    }

    /**
     * 删除从医院中移除专科
     *
     * @param hospitalId 医院编号
     * @param specialId  专科编号
     * @return 是否成功
     */
    @Override
    public boolean deleteSpecialRelation(Long hospitalId, Long specialId) {

        HospitalSpecialRelationExample example = new HospitalSpecialRelationExample();

        example.createCriteria()
                .andHospitalIdEqualTo(hospitalId)
                .andSpecialIdEqualTo(specialId);

        return specialRelationMapper.deleteByExample(example) > 0;
    }

    /**
     * 删除从医院中移除门诊
     *
     * @param hospitalId   医院编号
     * @param outpatientId 门诊编号
     * @return 是否成功
     */
    @Override
    public boolean deleteOutpatientRelation(Long hospitalId, Long outpatientId) {

        HospitalOutpatientRelationExample example = new HospitalOutpatientRelationExample();

        example.createCriteria()
                .andHospitalIdEqualTo(hospitalId)
                .andOutpatientIdEqualTo(outpatientId);

        return outpatientRelationMapper.deleteByExample(example) > 0;
    }

    /**
     * 插入门诊到医院中去
     *
     * @param param 医院门诊关系参数
     * @return 是否成功
     */
    @Override
    public boolean insertOutpatientRelation(HospitalOutpatientRelationParam param) {

        HospitalOutpatientRelation relation = new HospitalOutpatientRelation();

        BeanUtils.copyProperties(param, relation);

        relation.setGmtCreate(new Date());
        relation.setGmtModified(new Date());

        return outpatientRelationMapper.insertSelective(relation) > 0;
    }

    /**
     * 判断关系是否存在
     *
     * @param id 关系编号
     * @return 是否存在
     */
    @Override
    public boolean countSpecialRelation(Long id) {
        HospitalSpecialRelationExample example = new HospitalSpecialRelationExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return specialRelationMapper.countByExample(example) > 0;
    }

    /**
     * 判断关系是否存在
     *
     * @param id 关系编号
     * @return 是否存在
     */
    @Override
    public boolean countOutpatientRelation(Long id) {
        HospitalOutpatientRelationExample example = new HospitalOutpatientRelationExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return outpatientRelationMapper.countByExample(example) > 0;
    }

    /**
     * 判断医院是否存在该专科
     *
     * @param param 医院专科关系参数
     * @return 是否存在
     */
    @Override
    public boolean countSpecialRelation(HospitalSpecialRelationParam param) {
        HospitalSpecialRelationExample example = new HospitalSpecialRelationExample();

        example.createCriteria()
                .andHospitalIdEqualTo(param.getHospitalId())
                .andSpecialIdEqualTo(param.getSpecialId());

        return specialRelationMapper.countByExample(example) > 0;
    }

    /**
     * 判断医院是否存在该门诊
     *
     * @param param 医院门诊关系参数
     * @return 是否存在
     */
    @Override
    public boolean countOutpatientRelation(HospitalOutpatientRelationParam param) {
        HospitalOutpatientRelationExample example = new HospitalOutpatientRelationExample();

        example.createCriteria()
                .andHospitalIdEqualTo(param.getHospitalId())
                .andOutpatientIdEqualTo(param.getOutpatientId());

        return outpatientRelationMapper.countByExample(example) > 0;
    }
}
