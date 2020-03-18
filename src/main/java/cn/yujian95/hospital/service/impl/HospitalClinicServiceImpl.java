package cn.yujian95.hospital.service.impl;

import cn.yujian95.hospital.dto.param.HospitalClinicParam;
import cn.yujian95.hospital.entity.HospitalClinic;
import cn.yujian95.hospital.entity.HospitalClinicExample;
import cn.yujian95.hospital.mapper.HospitalClinicMapper;
import cn.yujian95.hospital.service.IHospitalClinicService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/12
 */
@Service
public class HospitalClinicServiceImpl implements IHospitalClinicService {

    @Resource
    private HospitalClinicMapper clinicMapper;

    /**
     * 插入诊室信息
     *
     * @param param 诊室信息参数
     * @return 是否成功
     */
    @Override
    public boolean insert(HospitalClinicParam param) {

        HospitalClinic clinic = new HospitalClinic();

        BeanUtils.copyProperties(param, clinic);

        clinic.setGmtCreate(new Date());
        clinic.setGmtModified(new Date());

        return clinicMapper.insertSelective(clinic) > 0;
    }

    /**
     * 更新诊室信息
     *
     * @param id    诊室编号
     * @param param 诊室信息参数
     * @return 是否成功
     */
    @Override
    public boolean update(Long id, HospitalClinicParam param) {
        HospitalClinic clinic = new HospitalClinic();

        BeanUtils.copyProperties(param, clinic);

        clinic.setId(id);
        clinic.setGmtModified(new Date());

        return clinicMapper.updateByPrimaryKeySelective(clinic) > 0;
    }

    /**
     * 获取诊室名称
     *
     * @param id 诊室编号
     * @return 诊室地址，空则返回，未知
     */
    @Override
    public String getAddress(Long id) {
        return getOptional(id).map(HospitalClinic::getAddress).orElse("未知");
    }

    /**
     * 获取诊室信息
     *
     * @param id 诊室编号
     * @return 诊室信息
     */
    @Override
    public Optional<HospitalClinic> getOptional(Long id) {
        return Optional.ofNullable(clinicMapper.selectByPrimaryKey(id));
    }

    /**
     * 删除诊室信息
     *
     * @param id 诊室编号
     * @return 是否成功
     */
    @Override
    public boolean delete(Long id) {
        return clinicMapper.deleteByPrimaryKey(id) > 0;
    }

    /**
     * 获取门诊下属的诊室
     *
     * @param outpatientId 门诊编号
     * @param pageNum      第几页
     * @param pageSize     页大小
     * @return 诊室列表
     */
    @Override
    public List<HospitalClinic> list(Long outpatientId, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        HospitalClinicExample example = new HospitalClinicExample();

        example.createCriteria()
                .andOutpatientIdEqualTo(outpatientId);

        return clinicMapper.selectByExample(example);
    }

    /**
     * 判断诊室是否存在
     *
     * @param id 诊室编号
     * @return 是否存在
     */
    @Override
    public boolean count(Long id) {
        HospitalClinicExample example = new HospitalClinicExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return clinicMapper.countByExample(example) > 0;
    }
}
