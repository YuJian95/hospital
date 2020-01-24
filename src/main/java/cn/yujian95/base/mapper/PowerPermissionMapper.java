package cn.yujian95.base.mapper;

import cn.yujian95.base.entity.PowerPermission;
import cn.yujian95.base.entity.PowerPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PowerPermissionMapper {
    long countByExample(PowerPermissionExample example);

    int deleteByExample(PowerPermissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PowerPermission record);

    int insertSelective(PowerPermission record);

    List<PowerPermission> selectByExample(PowerPermissionExample example);

    PowerPermission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PowerPermission record, @Param("example") PowerPermissionExample example);

    int updateByExample(@Param("record") PowerPermission record, @Param("example") PowerPermissionExample example);

    int updateByPrimaryKeySelective(PowerPermission record);

    int updateByPrimaryKey(PowerPermission record);
}