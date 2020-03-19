package cn.yujian95.hospital.service.impl;

import cn.yujian95.hospital.dto.param.VisitAppointmentParam;
import cn.yujian95.hospital.entity.VisitAppointment;
import cn.yujian95.hospital.entity.VisitAppointmentExample;
import cn.yujian95.hospital.mapper.VisitAppointmentMapper;
import cn.yujian95.hospital.service.IVisitAppointmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/19
 */
@Service
public class VisitAppointmentServiceImpl implements IVisitAppointmentService {

    /**
     * 预约状态 0：未开始，1：未按时就诊，2：取消预约挂号，3：已完成
     */
    private static final Integer WATTING = 0;
    private static final Integer MISS = 1;
    private static final Integer CANCEL = 2;
    private static final Integer FINISH = 3;

    @Resource
    private VisitAppointmentMapper appointmentMapper;

    /**
     * 获取已取号的数目
     *
     * @param planId 出诊编号
     * @return 已取号数目
     */
    @Override
    public int countByPlanId(Long planId) {
        VisitAppointmentExample example = new VisitAppointmentExample();

        example.createCriteria()
                .andPlanIdEqualTo(planId)
                // 除了取消预约外
                .andStatusNotEqualTo(CANCEL);

        return (int) appointmentMapper.countByExample(example);
    }

    /**
     * 插入预约记录
     *
     * @param param 就诊预约参数
     * @return 是否成功
     */
    @Override
    public boolean insert(VisitAppointmentParam param) {
        return false;
    }

    /**
     * 更新预约状态
     *
     * @param id     预约编号
     * @param status 预约状态 0：未开始，1：未按时就诊，2：取消预约挂号，3：已完成
     * @return 是否成功
     */
    @Override
    public boolean update(Long id, Integer status) {
        return false;
    }

    /**
     * 获取预约详情
     *
     * @param id 预订编号
     * @return 预定信息
     */
    @Override
    public Optional<VisitAppointment> getOptional(Long id) {
        return Optional.empty();
    }

    /**
     * 获取预约记录
     *
     * @param cardId   预约编号
     * @param status   预约状态 0：未开始，1：未按时就诊，2：取消预约挂号，3：已完成
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 预约记录
     */
    @Override
    public List<VisitAppointment> list(Long cardId, Integer status, Integer pageNum, Integer pageSize) {
        return null;
    }

    /**
     * 判断预订是否存在
     *
     * @param id 预定编号
     * @return 是否存在
     */
    @Override
    public boolean count(Long id) {
        return false;
    }

    /**
     * 判断是否，已预约
     *
     * @param cardId 就诊卡号
     * @param planId 出诊编号
     * @return 是否存在
     */
    @Override
    public boolean count(Long cardId, Long planId) {
        return false;
    }
}
