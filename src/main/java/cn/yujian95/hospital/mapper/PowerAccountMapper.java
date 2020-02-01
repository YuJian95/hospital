package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.PowerAccount;
import cn.yujian95.hospital.entity.PowerAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PowerAccountMapper {
    long countByExample(PowerAccountExample example);

    int deleteByExample(PowerAccountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PowerAccount record);

    int insertSelective(PowerAccount record);

    List<PowerAccount> selectByExample(PowerAccountExample example);

    PowerAccount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PowerAccount record, @Param("example") PowerAccountExample example);

    int updateByExample(@Param("record") PowerAccount record, @Param("example") PowerAccountExample example);

    int updateByPrimaryKeySelective(PowerAccount record);

    int updateByPrimaryKey(PowerAccount record);
}