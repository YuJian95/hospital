package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.HospitalInfo;
import cn.yujian95.hospital.entity.HospitalInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HospitalInfoMapper {
    long countByExample(HospitalInfoExample example);

    int deleteByExample(HospitalInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HospitalInfo record);

    int insertSelective(HospitalInfo record);

    List<HospitalInfo> selectByExample(HospitalInfoExample example);

    HospitalInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HospitalInfo record, @Param("example") HospitalInfoExample example);

    int updateByExample(@Param("record") HospitalInfo record, @Param("example") HospitalInfoExample example);

    int updateByPrimaryKeySelective(HospitalInfo record);

    int updateByPrimaryKey(HospitalInfo record);
}