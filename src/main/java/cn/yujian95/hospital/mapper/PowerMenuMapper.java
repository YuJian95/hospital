package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.PowerMenu;
import cn.yujian95.hospital.entity.PowerMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PowerMenuMapper {
    long countByExample(PowerMenuExample example);

    int deleteByExample(PowerMenuExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PowerMenu record);

    int insertSelective(PowerMenu record);

    List<PowerMenu> selectByExample(PowerMenuExample example);

    PowerMenu selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PowerMenu record, @Param("example") PowerMenuExample example);

    int updateByExample(@Param("record") PowerMenu record, @Param("example") PowerMenuExample example);

    int updateByPrimaryKeySelective(PowerMenu record);

    int updateByPrimaryKey(PowerMenu record);
}