package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.HospitalSpecial;
import cn.yujian95.hospital.entity.HospitalSpecialExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HospitalSpecialMapper {
    long countByExample(HospitalSpecialExample example);

    int deleteByExample(HospitalSpecialExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HospitalSpecial record);

    int insertSelective(HospitalSpecial record);

    List<HospitalSpecial> selectByExample(HospitalSpecialExample example);

    HospitalSpecial selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HospitalSpecial record, @Param("example") HospitalSpecialExample example);

    int updateByExample(@Param("record") HospitalSpecial record, @Param("example") HospitalSpecialExample example);

    int updateByPrimaryKeySelective(HospitalSpecial record);

    int updateByPrimaryKey(HospitalSpecial record);
}