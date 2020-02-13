package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.VisitOrder;
import cn.yujian95.hospital.entity.VisitOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VisitOrderMapper {
    long countByExample(VisitOrderExample example);

    int deleteByExample(VisitOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VisitOrder record);

    int insertSelective(VisitOrder record);

    List<VisitOrder> selectByExample(VisitOrderExample example);

    VisitOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VisitOrder record, @Param("example") VisitOrderExample example);

    int updateByExample(@Param("record") VisitOrder record, @Param("example") VisitOrderExample example);

    int updateByPrimaryKeySelective(VisitOrder record);

    int updateByPrimaryKey(VisitOrder record);
}