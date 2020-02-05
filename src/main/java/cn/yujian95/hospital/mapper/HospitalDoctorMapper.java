package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.HospitalDoctor;
import cn.yujian95.hospital.entity.HospitalDoctorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HospitalDoctorMapper {
    long countByExample(HospitalDoctorExample example);

    int deleteByExample(HospitalDoctorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HospitalDoctor record);

    int insertSelective(HospitalDoctor record);

    List<HospitalDoctor> selectByExample(HospitalDoctorExample example);

    HospitalDoctor selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HospitalDoctor record, @Param("example") HospitalDoctorExample example);

    int updateByExample(@Param("record") HospitalDoctor record, @Param("example") HospitalDoctorExample example);

    int updateByPrimaryKeySelective(HospitalDoctor record);

    int updateByPrimaryKey(HospitalDoctor record);
}