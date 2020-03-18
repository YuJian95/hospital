package cn.yujian95.hospital.service;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/19
 */

public interface IVisitOrderService {

    /**
     * 获取已取号的数目
     *
     * @param planId 出诊编号
     * @return 已取号数目
     */
    int countByPlanId(Long planId);
}
