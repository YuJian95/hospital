package cn.yujian95.hospital.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.yujian95.hospital.dto.*;
import cn.yujian95.hospital.dto.param.VisitAppointmentParam;
import cn.yujian95.hospital.entity.*;
import cn.yujian95.hospital.mapper.VisitAppointmentMapper;
import cn.yujian95.hospital.service.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static cn.yujian95.hospital.dto.AppointmentEnum.*;
import static cn.yujian95.hospital.dto.TimePeriodEnum.*;


/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/19
 */
@Service
public class VisitAppointmentServiceImpl implements IVisitAppointmentService {

    private static final int TIME_OF_ONE_TIME_PERIOD = 30;
    private static final int MAX_PEOPLE_OF_TIME_PERIOD = 5;

    @Resource
    private VisitAppointmentMapper appointmentMapper;

    @Resource
    private IVisitPlanService visitPlanService;

    @Resource
    private IHospitalClinicService hospitalClinicService;

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

        Date date = new Date();

        List<VisitAppointment> list = listAppointmentByDate(cardId, accountId,
                DateUtil.beginOfMonth(date), DateUtil.endOfMonth(date));

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
     * 获取就诊排队序号
     *
     * @param appointment 预约记录
     * @return 排队号
     */
    @Override
    public int getQueueNum(VisitAppointment appointment) {
        VisitAppointmentExample example = new VisitAppointmentExample();

        // TODO 不需要去取消状态
        example.createCriteria()
                // 预约时间小于等于当前记录
                .andGmtCreateLessThanOrEqualTo(appointment.getGmtCreate())
                // 同一预约时间段
                .andTimePeriodEqualTo(appointment.getTimePeriod())
                // 同一预约出诊
                .andPlanIdEqualTo(appointment.getPlanId());

        List<VisitAppointment> list = appointmentMapper.selectByExample(example);

        // 同一时间段排前面的人数
        int peopleBeforeTimePeriod = list.size();

        // 当前时间段对应的排队号
        return MAX_PEOPLE_OF_TIME_PERIOD * (appointment.getTimePeriod() - 1) + peopleBeforeTimePeriod;
    }

    /**
     * 获取前面等待人数
     *
     * @param planId 出诊编号
     * @param cardId 就诊卡号
     * @return 等待人数
     */
    @Override
    public int getWaitPeopleNum(Long planId, Long cardId) {
        VisitAppointmentExample example = new VisitAppointmentExample();

        example.createCriteria()
                .andPlanIdEqualTo(planId)
                .andStatusEqualTo(WAITING.getStatus());

        List<VisitAppointment> list = appointmentMapper.selectByExample(example);

        return getCountNum(cardId, list);
    }

    /**
     * 获取候诊队列信息
     *
     * @param date      就诊日期
     * @param cardId    就诊卡
     * @param accountId 账号编号
     * @return 当天候诊队列
     */
    @Override
    public List<VisitAppointmentQueueDTO> listTodayQueue(Date date, Long cardId, Long accountId) {

        List<VisitAppointment> list = listAppointmentByDate(cardId, accountId,
                DateUtil.beginOfDay(date), DateUtil.endOfDay(date));

        if (CollUtil.isEmpty(list)) {
            return null;
        }

        // 统计
        return list.stream().distinct()
                .map(this::convertQueue)
                .collect(Collectors.toList());

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
     * 获取预约记录列表
     *
     * @param cardId    就诊卡编号
     * @param accountId 账号编号
     * @param pageNum   第几页
     * @param pageSize  页大小
     * @return 预约记录
     */
    @Override
    public List<VisitAppointmentWithQueueDTO> listAllAppointment(Long cardId, Long accountId, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        VisitAppointmentExample example = new VisitAppointmentExample();

        example.setDistinct(true);

        example.createCriteria()
                .andCardIdEqualTo(cardId);

        // 或账号编号一致
        example.or().andAccountIdEqualTo(accountId);

        List<VisitAppointment> list = appointmentMapper.selectByExample(example);

        if (CollUtil.isEmpty(list)) {
            return null;
        }

        return list.stream()
                .map(this::convertTo)
                .collect(Collectors.toList());
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
     * 获取挂号详情
     *
     * @param id 预约编号
     * @return 就诊详情
     */
    @Override
    public VisitAppointmentWithQueueDTO getAppointmentDetails(Long id) {
        Optional<VisitAppointment> optional = getOptional(id);

        return optional.map(this::convertTo).orElse(null);
    }

    /**
     * 获取诊室地址
     *
     * @param doctorId 医生编号
     * @param time     时间段：1 上午，2 下午
     * @param day      日期
     * @return 诊室地址
     */
    @Override
    public String getClinicName(Long doctorId, Integer time, Date day) {
        // 获取出诊计划
        List<VisitPlan> plans = visitPlanService.getByTimeAndDate(doctorId, time, day);

        if (CollUtil.isEmpty(plans)) {
            return null;
        }

        VisitPlan plan = plans.get(0);

        return hospitalClinicService.getAddress(plan.getClinicId());
    }

    /**
     * 获取预约用户信息列表
     *
     * @param doctorId 医生编号
     * @param time     时间段：1 上午，2 下午
     * @param day      日期
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 用户信息列表
     */
    @Override
    public List<VisitUserInfoDTO> listVisitUserInfo(Long doctorId, Integer time, Date day, Integer pageNum, Integer pageSize) {

        // 获取出诊计划
        List<VisitPlan> plans = visitPlanService.getByTimeAndDate(doctorId, time, day);

        if (CollUtil.isEmpty(plans)) {
            return null;
        }

        PageHelper.startPage(pageNum, pageSize);

        // 获取预约卡号
        VisitAppointmentExample example = new VisitAppointmentExample();

        // 根据预约时间段排序
        example.setOrderByClause("time_period asc");

        VisitAppointmentExample.Criteria criteria = example.createCriteria();
        criteria.andPlanIdEqualTo(plans.get(0).getId());

        // 筛选时间段
        if (AM.getTime().equals(time)) {
            criteria.andTimePeriodBetween(AM.getStart(), AM.getEnd());
        } else {
            criteria.andTimePeriodBetween(PM.getStart(), PM.getEnd());
        }

        return appointmentMapper.selectByExample(example).stream()
                .map(this::convertToUserInfo)
                // 去除取消后的记录
                .filter(userInfo -> !CANCEL.getStatus().equals(userInfo.getStatus()))
                .collect(Collectors.toList());
    }

    /**
     * 转换为候诊队列
     *
     * @param appointment 预约记录信息
     * @return 候诊队列信息
     */
    private VisitAppointmentQueueDTO convertQueue(VisitAppointment appointment) {

        VisitAppointmentQueueDTO dto = new VisitAppointmentQueueDTO();

        Long cardId = appointment.getCardId();
        Long planId = appointment.getPlanId();

        int queueNum = getQueueNum(appointment);
        int waitPeopleNum = getWaitPeopleNum(planId, cardId);

        dto.setAppointmentId(appointment.getId());

        dto.setQueueNum(queueNum);
        dto.setWaitPeopleNum(waitPeopleNum);

        Optional<VisitPlanDTO> optional = visitPlanService.getOptional(appointment.getPlanId());

        // 获取出诊计划信息
        optional.ifPresent(visitPlanDTO -> BeanUtils.copyProperties(visitPlanDTO, dto));

        dto.setStatus(appointment.getStatus());
        dto.setName(userMedicalCardService.getName(appointment.getCardId()));

        // 假设一个时间段 30 分钟内 5 个人
        dto.setWaitTime(waitPeopleNum * (TIME_OF_ONE_TIME_PERIOD / MAX_PEOPLE_OF_TIME_PERIOD));
        dto.setTimePeriod(appointment.getTimePeriod());

        return dto;
    }

    /**
     * 转换未用户预约信息
     *
     * @param appointment 预约信息
     * @return 预约信息
     */
    private VisitUserInfoDTO convertToUserInfo(VisitAppointment appointment) {

        VisitUserInfoDTO dto = new VisitUserInfoDTO();

        // 设置就诊卡信息
        Long cardId = appointment.getCardId();

        Optional<UserMedicalCard> optional = userMedicalCardService.getOptional(cardId);

        if (optional.isPresent()) {
            UserMedicalCard card = optional.get();
            BeanUtils.copyProperties(card, dto);
        }

        // 预约信息
        dto.setCardId(cardId);
        dto.setStatus(appointment.getStatus());
        dto.setQueueNum(getQueueNum(appointment));
        dto.setAppointmentId(appointment.getId());
        dto.setTimePeriod(appointment.getTimePeriod());
        dto.setStatus(appointment.getStatus());

        return dto;
    }

    /**
     * 获取统计数目
     *
     * @param cardId 就诊卡编号
     * @param list   就诊列表
     * @return 统计数目
     */
    private int getCountNum(Long cardId, List<VisitAppointment> list) {

        if (CollUtil.isEmpty(list)) {
            return 0;
        }

        // 排队人数
        int waitPeopleCount = 0;

        for (int i = 0; i < list.size(); i++) {
            if (cardId.equals(list.get(i).getCardId())) {
                waitPeopleCount = i;
                break;
            }
        }

        return waitPeopleCount;
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

        dto.setStatus(appointment.getStatus());
        dto.setName(userMedicalCardService.getName(appointment.getCardId()));

        return dto;
    }

    /**
     * 转换获取对应中文名称
     *
     * @param appointment 预约记录
     * @return 预约记录以及就诊用户
     */
    private VisitAppointmentWithQueueDTO convertTo(VisitAppointment appointment) {

        VisitAppointmentWithQueueDTO dto = new VisitAppointmentWithQueueDTO();

        BeanUtils.copyProperties(convert(appointment), dto);

        dto.setStatus(appointment.getStatus());
        dto.setQueueNum(getQueueNum(appointment));
        dto.setGmtCreate(appointment.getGmtCreate());

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

    /**
     * 获取出诊预约情况
     *
     * @param cardId    就诊卡号
     * @param accountId 账号编号
     * @param start     开始日期
     * @param end       结束日期
     * @return 预约记录列表
     */
    private List<VisitAppointment> listAppointmentByDate(Long cardId, Long accountId, Date start, Date end) {

        List<Long> planList = visitPlanService.list(start, end);

        if (CollUtil.isEmpty(planList)) {
            return null;
        }

        VisitAppointmentExample example = new VisitAppointmentExample();

        example.setDistinct(true);

        example.createCriteria()
                .andPlanIdIn(planList)
                .andCardIdEqualTo(cardId);

        // 或账号编号一致
        example.or().andPlanIdIn(planList)
                .andAccountIdEqualTo(accountId);

        return appointmentMapper.selectByExample(example);
    }
}
