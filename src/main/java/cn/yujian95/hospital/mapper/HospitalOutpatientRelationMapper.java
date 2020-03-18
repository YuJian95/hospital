package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.HospitalOutpatientRelation;
import cn.yujian95.hospital.entity.HospitalOutpatientRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HospitalOutpatientRelationMapper {
    long countByExample(HospitalOutpatientRelationExample example);

    int deleteByExample(HospitalOutpatientRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HospitalOutpatientRelation record);

    int insertSelective(HospitalOutpatientRelation record);

    List<HospitalOutpatientRelation> selectByExample(HospitalOutpatientRelationExample example);

    HospitalOutpatientRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HospitalOutpatientRelation record, @Param("example") HospitalOutpatientRelationExample example);

    int updateByExample(@Param("record") HospitalOutpatientRelation record, @Param("example") HospitalOutpatientRelationExample example);

    int updateByPrimaryKeySelective(HospitalOutpatientRelation record);

    int updateByPrimaryKey(HospitalOutpatientRelation record);
}