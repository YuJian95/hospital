package cn.yujian95.hospital.service.impl;

import cn.yujian95.hospital.dto.HospitalDoctorDTO;
import cn.yujian95.hospital.dto.param.HospitalDoctorParam;
import cn.yujian95.hospital.entity.HospitalDoctor;
import cn.yujian95.hospital.entity.HospitalDoctorExample;
import cn.yujian95.hospital.mapper.HospitalDoctorMapper;
import cn.yujian95.hospital.service.IHospitalDoctorService;
import cn.yujian95.hospital.service.IHospitalOutpatientService;
import cn.yujian95.hospital.service.IHospitalSpecialService;
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
 * @date 2020/2/4
 */
@Service
public class HospitalDoctorInfoServiceImpl implements IHospitalDoctorService {

    @Resource
    private HospitalDoctorMapper doctorInfoMapper;

    @Resource
    private IHospitalSpecialService specialService;

    @Resource
    private IHospitalOutpatientService outpatientService;

    /**
     * 添加医生信息
     *
     * @param param 医生信息参数
     * @return 是否成功
     */
    @Override
    public boolean insert(HospitalDoctorParam param) {
        HospitalDoctor info = new HospitalDoctor();

        BeanUtils.copyProperties(param, info);

        info.setGmtCreate(new Date());
        info.setGmtModified(new Date());

        return doctorInfoMapper.insertSelective(info) > 0;
    }

    /**
     * 更新医生信息
     *
     * @param id    医生编号
     * @param param 医生信息参数
     * @return 是否成功
     */
    @Override
    public boolean update(Long id, HospitalDoctorParam param) {
        HospitalDoctor info = new HospitalDoctor();

        BeanUtils.copyProperties(param, info);

        info.setId(id);
        info.setGmtModified(new Date());

        return doctorInfoMapper.updateByPrimaryKeySelective(info) > 0;
    }

    /**
     * 是否存在医生信息
     *
     * @param id 医生编号
     * @return 是否存在
     */
    @Override
    public boolean count(Long id) {
        HospitalDoctorExample example = new HospitalDoctorExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return doctorInfoMapper.countByExample(example) > 0;
    }

    /**
     * 获取转换后的对象信息
     *
     * @param id 医生编号
     * @return 转换后的对象
     */
    @Override
    public Optional<HospitalDoctorDTO> getConvert(Long id) {
        return Optional.ofNullable(convert(doctorInfoMapper.selectByPrimaryKey(id)));
    }

    /**
     * 获取医生名称
     *
     * @param id 医生编号
     * @return 医生名称，空则，返回未知
     */
    @Override
    public String getName(Long id) {
        return getOptional(id).map(HospitalDoctor::getName).orElse("未知");
    }

    /**
     * 获取医生信息
     *
     * @param id 医生编号
     * @return 医生编号
     */
    @Override
    public Optional<HospitalDoctor> getOptional(Long id) {
        return Optional.ofNullable(doctorInfoMapper.selectByPrimaryKey(id));
    }

    /**
     * 删除医生信息
     *
     * @param id 医生编号
     * @return 是否成功
     */
    @Override
    public boolean delete(Long id) {
        return doctorInfoMapper.deleteByPrimaryKey(id) > 0;
    }

    /**
     * 查找医生信息
     *
     * @param name     医生名称
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 医生信息列表
     */
    @Override
    public List<HospitalDoctorDTO> list(String name, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        HospitalDoctorExample example = new HospitalDoctorExample();

        if (!StringUtils.isEmpty(name)) {
            example.createCriteria()
                    .andNameLike("%" + name + "%");
        }

        return doctorInfoMapper.selectByExample(example).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    /**
     * 查找医生信息列表
     *
     * @param name         医生名称
     * @param specialId    专科编号
     * @param outpatientId 门诊编号
     * @param pageNum      第几页
     * @param pageSize     页大小
     * @return 医生信息列表
     */
    @Override
    public List<HospitalDoctorDTO> list(String name, Long specialId, Long outpatientId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        HospitalDoctorExample example = new HospitalDoctorExample();

        HospitalDoctorExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (specialId != null) {
            criteria.andSpecialIdEqualTo(specialId);
        }

        if (outpatientId != null) {
            criteria.andOutpatientIdEqualTo(outpatientId);
        }

        return doctorInfoMapper.selectByExample(example).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    /**
     * 转换医生信息
     * 增加专科名称，门诊名称
     *
     * @param doctor 医生信息
     * @return 医生信息封装对象
     */
    private HospitalDoctorDTO convert(HospitalDoctor doctor) {

        HospitalDoctorDTO dto = new HospitalDoctorDTO();

        BeanUtils.copyProperties(doctor, dto);

        // 设置专科名称
        dto.setSpecialName(specialService.getName(doctor.getSpecialId()));

        // 设置门诊名称
        dto.setOutpatientName(outpatientService.getName(doctor.getOutpatientId()));

        return dto;
    }
}
