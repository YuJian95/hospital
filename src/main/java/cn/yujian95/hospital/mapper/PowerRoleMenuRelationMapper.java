package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.PowerRoleMenuRelation;
import cn.yujian95.hospital.entity.PowerRoleMenuRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PowerRoleMenuRelationMapper {
    long countByExample(PowerRoleMenuRelationExample example);

    int deleteByExample(PowerRoleMenuRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PowerRoleMenuRelation record);

    int insertSelective(PowerRoleMenuRelation record);

    List<PowerRoleMenuRelation> selectByExample(PowerRoleMenuRelationExample example);

    PowerRoleMenuRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PowerRoleMenuRelation record, @Param("example") PowerRoleMenuRelationExample example);

    int updateByExample(@Param("record") PowerRoleMenuRelation record, @Param("example") PowerRoleMenuRelationExample example);

    int updateByPrimaryKeySelective(PowerRoleMenuRelation record);

    int updateByPrimaryKey(PowerRoleMenuRelation record);
}