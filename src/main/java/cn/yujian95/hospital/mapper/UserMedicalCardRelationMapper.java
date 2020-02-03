package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.UserMedicalCardRelation;
import cn.yujian95.hospital.entity.UserMedicalCardRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMedicalCardRelationMapper {
    long countByExample(UserMedicalCardRelationExample example);

    int deleteByExample(UserMedicalCardRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserMedicalCardRelation record);

    int insertSelective(UserMedicalCardRelation record);

    List<UserMedicalCardRelation> selectByExample(UserMedicalCardRelationExample example);

    UserMedicalCardRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserMedicalCardRelation record, @Param("example") UserMedicalCardRelationExample example);

    int updateByExample(@Param("record") UserMedicalCardRelation record, @Param("example") UserMedicalCardRelationExample example);

    int updateByPrimaryKeySelective(UserMedicalCardRelation record);

    int updateByPrimaryKey(UserMedicalCardRelation record);
}