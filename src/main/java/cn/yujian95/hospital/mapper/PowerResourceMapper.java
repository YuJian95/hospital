package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.PowerResource;
import cn.yujian95.hospital.entity.PowerResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PowerResourceMapper {
    long countByExample(PowerResourceExample example);

    int deleteByExample(PowerResourceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PowerResource record);

    int insertSelective(PowerResource record);

    List<PowerResource> selectByExample(PowerResourceExample example);

    PowerResource selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PowerResource record, @Param("example") PowerResourceExample example);

    int updateByExample(@Param("record") PowerResource record, @Param("example") PowerResourceExample example);

    int updateByPrimaryKeySelective(PowerResource record);

    int updateByPrimaryKey(PowerResource record);
}