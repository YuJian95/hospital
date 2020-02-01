package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.LogAccountLogin;
import cn.yujian95.hospital.entity.LogAccountLoginExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogAccountLoginMapper {
    long countByExample(LogAccountLoginExample example);

    int deleteByExample(LogAccountLoginExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LogAccountLogin record);

    int insertSelective(LogAccountLogin record);

    List<LogAccountLogin> selectByExample(LogAccountLoginExample example);

    LogAccountLogin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LogAccountLogin record, @Param("example") LogAccountLoginExample example);

    int updateByExample(@Param("record") LogAccountLogin record, @Param("example") LogAccountLoginExample example);

    int updateByPrimaryKeySelective(LogAccountLogin record);

    int updateByPrimaryKey(LogAccountLogin record);
}