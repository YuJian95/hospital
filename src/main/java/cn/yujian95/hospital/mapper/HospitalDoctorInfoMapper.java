package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.HospitalDoctorInfo;
import cn.yujian95.hospital.entity.HospitalDoctorInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HospitalDoctorInfoMapper {
    long countByExample(HospitalDoctorInfoExample example);

    int deleteByExample(HospitalDoctorInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HospitalDoctorInfo record);

    int insertSelective(HospitalDoctorInfo record);

    List<HospitalDoctorInfo> selectByExample(HospitalDoctorInfoExample example);

    HospitalDoctorInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HospitalDoctorInfo record, @Param("example") HospitalDoctorInfoExample example);

    int updateByExample(@Param("record") HospitalDoctorInfo record, @Param("example") HospitalDoctorInfoExample example);

    int updateByPrimaryKeySelective(HospitalDoctorInfo record);

    int updateByPrimaryKey(HospitalDoctorInfo record);
}