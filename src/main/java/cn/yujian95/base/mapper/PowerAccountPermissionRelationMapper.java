package cn.yujian95.base.mapper;

import cn.yujian95.base.entity.PowerAccountPermissionRelation;
import cn.yujian95.base.entity.PowerAccountPermissionRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PowerAccountPermissionRelationMapper {
    long countByExample(PowerAccountPermissionRelationExample example);

    int deleteByExample(PowerAccountPermissionRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PowerAccountPermissionRelation record);

    int insertSelective(PowerAccountPermissionRelation record);

    List<PowerAccountPermissionRelation> selectByExample(PowerAccountPermissionRelationExample example);

    PowerAccountPermissionRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PowerAccountPermissionRelation record, @Param("example") PowerAccountPermissionRelationExample example);

    int updateByExample(@Param("record") PowerAccountPermissionRelation record, @Param("example") PowerAccountPermissionRelationExample example);

    int updateByPrimaryKeySelective(PowerAccountPermissionRelation record);

    int updateByPrimaryKey(PowerAccountPermissionRelation record);
}