package cn.yujian95.base.mapper.dao;

import cn.yujian95.base.entity.PowerAccountPermissionRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/20
 */

public interface PowerAccountPermissionRelationDao {

    /**
     * 批量插入账号特殊权限
     *
     * @param list 账号特殊权限
     * @return 成功记录
     */
    int insertList(List<PowerAccountPermissionRelation> list);
}
