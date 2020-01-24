package cn.yujian95.base.mapper;

import cn.yujian95.base.entity.LogOperation;
import cn.yujian95.base.entity.LogOperationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogOperationMapper {
    long countByExample(LogOperationExample example);

    int deleteByExample(LogOperationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LogOperation record);

    int insertSelective(LogOperation record);

    List<LogOperation> selectByExample(LogOperationExample example);

    LogOperation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LogOperation record, @Param("example") LogOperationExample example);

    int updateByExample(@Param("record") LogOperation record, @Param("example") LogOperationExample example);

    int updateByPrimaryKeySelective(LogOperation record);

    int updateByPrimaryKey(LogOperation record);
}