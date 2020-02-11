package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.HospitalOutpatientClinicRelation;
import cn.yujian95.hospital.entity.HospitalOutpatientClinicRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HospitalOutpatientClinicRelationMapper {
    long countByExample(HospitalOutpatientClinicRelationExample example);

    int deleteByExample(HospitalOutpatientClinicRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HospitalOutpatientClinicRelation record);

    int insertSelective(HospitalOutpatientClinicRelation record);

    List<HospitalOutpatientClinicRelation> selectByExample(HospitalOutpatientClinicRelationExample example);

    HospitalOutpatientClinicRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HospitalOutpatientClinicRelation record, @Param("example") HospitalOutpatientClinicRelationExample example);

    int updateByExample(@Param("record") HospitalOutpatientClinicRelation record, @Param("example") HospitalOutpatientClinicRelationExample example);

    int updateByPrimaryKeySelective(HospitalOutpatientClinicRelation record);

    int updateByPrimaryKey(HospitalOutpatientClinicRelation record);
}