package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.VisitBlacklist;
import cn.yujian95.hospital.entity.VisitBlacklistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VisitBlacklistMapper {
    long countByExample(VisitBlacklistExample example);

    int deleteByExample(VisitBlacklistExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VisitBlacklist record);

    int insertSelective(VisitBlacklist record);

    List<VisitBlacklist> selectByExample(VisitBlacklistExample example);

    VisitBlacklist selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VisitBlacklist record, @Param("example") VisitBlacklistExample example);

    int updateByExample(@Param("record") VisitBlacklist record, @Param("example") VisitBlacklistExample example);

    int updateByPrimaryKeySelective(VisitBlacklist record);

    int updateByPrimaryKey(VisitBlacklist record);
}