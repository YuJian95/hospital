package cn.yujian95.hospital.service.impl;

import cn.yujian95.hospital.dto.param.HospitalDoctorInfoParam;
import cn.yujian95.hospital.entity.HospitalDoctorInfo;
import cn.yujian95.hospital.entity.HospitalDoctorInfoExample;
import cn.yujian95.hospital.mapper.HospitalDoctorInfoMapper;
import cn.yujian95.hospital.service.IHospitalDoctorService;
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
 * @date 2020/2/4
 */
@Service
public class HospitalDoctorInfoServiceImpl implements IHospitalDoctorService {

    @Resource
    private HospitalDoctorInfoMapper doctorInfoMapper;

    /**
     * 添加医生信息
     *
     * @param param 医生信息参数
     * @return 是否成功
     */
    @Override
    public boolean insert(HospitalDoctorInfoParam param) {
        HospitalDoctorInfo info = new HospitalDoctorInfo();

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
    public boolean update(Long id, HospitalDoctorInfoParam param) {
        HospitalDoctorInfo info = new HospitalDoctorInfo();

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
        HospitalDoctorInfoExample example = new HospitalDoctorInfoExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return doctorInfoMapper.countByExample(example) > 0;
    }

    /**
     * 获取医生信息
     *
     * @param id 医生编号
     * @return 医生编号
     */
    @Override
    public Optional<HospitalDoctorInfo> getOptional(Long id) {
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
    public List<HospitalDoctorInfo> list(String name, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        HospitalDoctorInfoExample example = new HospitalDoctorInfoExample();

        if (!StringUtils.isEmpty(name)) {
            example.createCriteria()
                    .andNameLike("%" + name + "%");
        }

        return doctorInfoMapper.selectByExample(example);
    }
}
