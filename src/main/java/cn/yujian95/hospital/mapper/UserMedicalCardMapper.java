package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.UserMedicalCard;
import cn.yujian95.hospital.entity.UserMedicalCardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMedicalCardMapper {
    long countByExample(UserMedicalCardExample example);

    int deleteByExample(UserMedicalCardExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserMedicalCard record);

    int insertSelective(UserMedicalCard record);

    List<UserMedicalCard> selectByExample(UserMedicalCardExample example);

    UserMedicalCard selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserMedicalCard record, @Param("example") UserMedicalCardExample example);

    int updateByExample(@Param("record") UserMedicalCard record, @Param("example") UserMedicalCardExample example);

    int updateByPrimaryKeySelective(UserMedicalCard record);

    int updateByPrimaryKey(UserMedicalCard record);
}