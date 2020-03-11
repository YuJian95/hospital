package cn.yujian95.hospital.mapper;

import cn.yujian95.hospital.entity.PowerResourceCategory;
import cn.yujian95.hospital.entity.PowerResourceCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PowerResourceCategoryMapper {
    long countByExample(PowerResourceCategoryExample example);

    int deleteByExample(PowerResourceCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PowerResourceCategory record);

    int insertSelective(PowerResourceCategory record);

    List<PowerResourceCategory> selectByExample(PowerResourceCategoryExample example);

    PowerResourceCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PowerResourceCategory record, @Param("example") PowerResourceCategoryExample example);

    int updateByExample(@Param("record") PowerResourceCategory record, @Param("example") PowerResourceCategoryExample example);

    int updateByPrimaryKeySelective(PowerResourceCategory record);

    int updateByPrimaryKey(PowerResourceCategory record);
}