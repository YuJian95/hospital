package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.PowerAccountRoleRelation;
import cn.yujian95.hospital.entity.PowerAccountRoleRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PowerAccountRoleRelationMapper {
    long countByExample(PowerAccountRoleRelationExample example);

    int deleteByExample(PowerAccountRoleRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PowerAccountRoleRelation record);

    int insertSelective(PowerAccountRoleRelation record);

    List<PowerAccountRoleRelation> selectByExample(PowerAccountRoleRelationExample example);

    PowerAccountRoleRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PowerAccountRoleRelation record, @Param("example") PowerAccountRoleRelationExample example);

    int updateByExample(@Param("record") PowerAccountRoleRelation record, @Param("example") PowerAccountRoleRelationExample example);

    int updateByPrimaryKeySelective(PowerAccountRoleRelation record);

    int updateByPrimaryKey(PowerAccountRoleRelation record);
}