package cn.yujian95.hospital.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.yujian95.hospital.dto.UserCreditDTO;
import cn.yujian95.hospital.dto.VisitAppointmentDTO;
import cn.yujian95.hospital.dto.VisitAppointmentWithCaseDTO;
import cn.yujian95.hospital.dto.VisitPlanDTO;
import cn.yujian95.hospital.dto.param.VisitAppointmentParam;
import cn.yujian95.hospital.entity.UserCase;
import cn.yujian95.hospital.entity.VisitAppointment;
import cn.yujian95.hospital.entity.VisitAppointmentExample;
import cn.yujian95.hospital.mapper.VisitAppointmentMapper;
import cn.yujian95.hospital.service.IUserCaseService;
import cn.yujian95.hospital.service.IUserMedicalCardService;
import cn.yujian95.hospital.service.IVisitAppointmentService;
import cn.yujian95.hospital.service.IVisitPlanService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static cn.yujian95.hospital.dto.AppointmentEnum.*;


/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/19
 */
@Service
public class VisitAppointmentServiceImpl implements IVisitAppointmentService {

    @Resource
    private VisitAppointmentMapper appointmentMapper;

    @Resource
    private IVisitPlanService visitPlanService;

    @Resource
    private IUserMedicalCardService userMedicalCardService;

    @Resource
    private IUserCaseService userCaseService;

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
                .andStatusNotEqualTo(CANCEL.getStatus());

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

        // 倒序从近到远
        example.setOrderByClause("gmt_create desc");

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
                .andStatusNotEqualTo(CANCEL.getStatus())
                .andPlanIdEqualTo(planId);

        return appointmentMapper.countByExample(example) > 0;
    }

    /**
     * 获取当月信用情况
     *
     * @param accountId 账号编号
     * @param cardId    就诊卡编号
     * @return 当月信用情况
     */
    @Override
    public UserCreditDTO getCurrentCredit(Long accountId, Long cardId) {

        VisitAppointmentExample example = new VisitAppointmentExample();

        example.setDistinct(true);

        Date date = new Date();

        example.createCriteria()
                .andGmtCreateBetween(DateUtil.beginOfMonth(date), DateUtil.endOfMonth(date))
                .andAccountIdEqualTo(accountId);

        example.or().andCardIdEqualTo(cardId);

        List<VisitAppointment> list = appointmentMapper.selectByExample(example);

        if (CollUtil.isEmpty(list)) {
            return null;
        }

        return getCredit(list);
    }

    /**
     * 获取全部信用情况
     *
     * @param accountId 账号编号
     * @param cardId    就诊卡
     * @return 用户全部信用信息
     */
    @Override
    public UserCreditDTO getAllCredit(Long accountId, Long cardId) {

        VisitAppointmentExample example = new VisitAppointmentExample();

        example.setDistinct(true);

        Date date = new Date();

        example.createCriteria()
                .andGmtCreateLessThanOrEqualTo(date)
                .andAccountIdEqualTo(accountId);

        example.or().andCardIdEqualTo(cardId);

        List<VisitAppointment> list = appointmentMapper.selectByExample(example);

        if (CollUtil.isEmpty(list)) {
            return null;
        }

        return getCredit(list);

    }

    /**
     * 获取就诊记录详情
     *
     * @param id 预约编号
     * @return 就诊记录详情
     */
    @Override
    public VisitAppointmentWithCaseDTO getVisitAppointmentWithCaseDTO(Long id) {
        VisitAppointmentWithCaseDTO dto = new VisitAppointmentWithCaseDTO();

        BeanUtils.copyProperties(convert(appointmentMapper.selectByPrimaryKey(id)), dto);


        List<UserCase> list = userCaseService.listByAppointment(id);

        if (CollUtil.isEmpty(list)) {
            dto.setUserCase(null);
            return dto;
        }

        dto.setUserCase(list.get(0));

        return dto;
    }

    /**
     * 获取就诊记录列表
     *
     * @param cardId   就诊卡号
     * @param pageNum  第一页
     * @param pageSize 页大小
     * @return 就诊记录列表
     */
    @Override
    public List<VisitAppointmentDTO> listFinishAppointment(Long cardId, Integer pageNum, Integer pageSize) {

        // 获取已完成记录情况
        List<VisitAppointment> list = list(cardId, FINISH.getStatus(), pageNum, pageSize);

        if (CollUtil.isEmpty(list)) {
            return null;
        }

        return list.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    /**
     * 转换获取对应中文名称
     *
     * @param appointment 预约记录
     * @return 预约记录以及就诊用户
     */
    private VisitAppointmentDTO convert(VisitAppointment appointment) {

        VisitAppointmentDTO dto = new VisitAppointmentDTO();

        dto.setAppointmentId(appointment.getId());

        Optional<VisitPlanDTO> optional = visitPlanService.getOptional(appointment.getPlanId());

        // 获取出诊计划信息
        optional.ifPresent(visitPlanDTO -> BeanUtils.copyProperties(visitPlanDTO, dto));

        dto.setName(userMedicalCardService.getName(appointment.getCardId()));

        return dto;
    }

    /**
     * 统计信用情况
     *
     * @param list 就诊记录列表
     * @return 信用情况
     */
    private UserCreditDTO getCredit(List<VisitAppointment> list) {
        UserCreditDTO dto = new UserCreditDTO();

        int miss = 0, cancel = 0, finish = 0;

        Map<Integer, Long> map = list.stream()
                .collect(Collectors.groupingBy(VisitAppointment::getStatus, Collectors.counting()));

        if (map.containsKey(MISSING.getStatus())) {
            miss = map.get(MISSING.getStatus()).intValue();
        }

        if (map.containsKey(CANCEL.getStatus())) {
            cancel = map.get(CANCEL.getStatus()).intValue();
        }

        if (map.containsKey(FINISH.getStatus())) {
            finish = map.get(FINISH.getStatus()).intValue();
        }

        dto.setFinish(finish);
        dto.setCancel(cancel);
        dto.setMiss(miss);

        return dto;

    }

}
