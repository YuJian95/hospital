package cn.yujian95.hospital.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.yujian95.hospital.dto.param.VisitAppointmentParam;
import cn.yujian95.hospital.entity.VisitAppointment;
import cn.yujian95.hospital.entity.VisitAppointmentExample;
import cn.yujian95.hospital.mapper.VisitAppointmentMapper;
import cn.yujian95.hospital.service.IVisitAppointmentService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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
     * @param planId     出诊编号
     * @param timePeriod 时间段
     * @return 已取号数目
     */
    @Override
    public int countByPlanId(Long planId, Integer timePeriod) {
        VisitAppointmentExample example = new VisitAppointmentExample();

        example.createCriteria()
                .andPlanIdEqualTo(planId)
                .andTimePeriodEqualTo(timePeriod)
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
        VisitAppointment appointment = new VisitAppointment();

        BeanUtils.copyProperties(param, appointment);

        appointment.setGmtCreate(new Date());
        appointment.setGmtModified(new Date());

        return appointmentMapper.insertSelective(appointment) > 0;
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
        VisitAppointment appointment = new VisitAppointment();

        appointment.setId(id);
        appointment.setStatus(status);
        appointment.setGmtModified(new Date());

        return appointmentMapper.updateByPrimaryKeySelective(appointment) > 0;
    }

    /**
     * 获取预约详情
     *
     * @param id 预订编号
     * @return 预定信息
     */
    @Override
    public Optional<VisitAppointment> getOptional(Long id) {
        return Optional.ofNullable(appointmentMapper.selectByPrimaryKey(id));
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

        PageHelper.startPage(pageNum, pageSize);

        VisitAppointmentExample example = new VisitAppointmentExample();

        VisitAppointmentExample.Criteria criteria = example.createCriteria();

        if (cardId != null) {
            criteria.andCardIdEqualTo(cardId);
        }

        if (status != null) {
            criteria.andStatusEqualTo(status);
        }

        return appointmentMapper.selectByExample(example);
    }

    /**
     * 判断预订是否存在
     *
     * @param id 预定编号
     * @return 是否存在
     */
    @Override
    public boolean count(Long id) {
        VisitAppointmentExample example = new VisitAppointmentExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return appointmentMapper.countByExample(example) > 0;
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
        VisitAppointmentExample example = new VisitAppointmentExample();

        example.createCriteria()
                .andCardIdEqualTo(cardId)
                // 同一出诊中，除取消预约外
                .andStatusNotEqualTo(CANCEL)
                .andPlanIdEqualTo(planId);

        return appointmentMapper.countByExample(example) > 0;
    }
}
