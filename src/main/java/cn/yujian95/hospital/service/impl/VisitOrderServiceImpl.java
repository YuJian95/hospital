package cn.yujian95.hospital.service.impl;

import cn.yujian95.hospital.entity.VisitOrderExample;
import cn.yujian95.hospital.mapper.VisitOrderMapper;
import cn.yujian95.hospital.service.IVisitOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/19
 */
@Service
public class VisitOrderServiceImpl implements IVisitOrderService {

    private static final Integer CANCEL_ORDER = 2;

    @Resource
    private VisitOrderMapper orderMapper;

    /**
     * 获取已取号的数目
     *
     * @param planId 出诊编号
     * @return 已取号数目
     */
    @Override
    public int countByPlanId(Long planId) {
        VisitOrderExample example = new VisitOrderExample();

        example.createCriteria()
                .andPlanIdEqualTo(planId)
                // 除了取消预约外
                .andStatusNotEqualTo(CANCEL_ORDER);

        return (int) orderMapper.countByExample(example);
    }
}
