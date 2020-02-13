package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.VisitPlan;
import cn.yujian95.hospital.entity.VisitPlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VisitPlanMapper {
    long countByExample(VisitPlanExample example);

    int deleteByExample(VisitPlanExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VisitPlan record);

    int insertSelective(VisitPlan record);

    List<VisitPlan> selectByExample(VisitPlanExample example);

    VisitPlan selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VisitPlan record, @Param("example") VisitPlanExample example);

    int updateByExample(@Param("record") VisitPlan record, @Param("example") VisitPlanExample example);

    int updateByPrimaryKeySelective(VisitPlan record);

    int updateByPrimaryKey(VisitPlan record);
}