package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.HospitalOutpatient;
import cn.yujian95.hospital.entity.HospitalOutpatientExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HospitalOutpatientMapper {
    long countByExample(HospitalOutpatientExample example);

    int deleteByExample(HospitalOutpatientExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HospitalOutpatient record);

    int insertSelective(HospitalOutpatient record);

    List<HospitalOutpatient> selectByExample(HospitalOutpatientExample example);

    HospitalOutpatient selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HospitalOutpatient record, @Param("example") HospitalOutpatientExample example);

    int updateByExample(@Param("record") HospitalOutpatient record, @Param("example") HospitalOutpatientExample example);

    int updateByPrimaryKeySelective(HospitalOutpatient record);

    int updateByPrimaryKey(HospitalOutpatient record);
}