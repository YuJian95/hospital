package cn.yujian95.hospital.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.yujian95.hospital.dto.*;
import cn.yujian95.hospital.dto.param.VisitPlanParam;
import cn.yujian95.hospital.dto.param.VisitPlanUpdateParam;
import cn.yujian95.hospital.entity.VisitPlan;
import cn.yujian95.hospital.entity.VisitPlanExample;
import cn.yujian95.hospital.mapper.VisitPlanMapper;
import cn.yujian95.hospital.service.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/1
 */

@Service
public class VisitPlanServiceImpl implements IVisitPlanService {

    /**
     * 每段时间内 最大就诊人数
     */
    private static final Integer MAX_OF_PATIENTS = 5;

    @Resource
    private VisitPlanMapper planMapper;

    @Resource
    private IHospitalDoctorService hospitalDoctorService;

    @Resource
    private IHospitalClinicService hospitalClinicService;

    @Resource
    private IHospitalInfoService hospitalInfoService;

    @Resource
    private IHospitalOutpatientService hospitalOutpatientService;

    @Resource
    private IHospitalSpecialService hospitalSpecialService;

    @Resource
    private IVisitAppointmentService appointmentService;

    /**
     * 创建出诊计划
     *
     * @param param 出诊计划参数
     * @return 是否成功
     */
    @Override
    public boolean insertAll(VisitPlanParam param) {

        VisitPlan plan = new VisitPlan();

        BeanUtils.copyProperties(param, plan);

        plan.setGmtCreate(new Date());
        plan.setGmtModified(new Date());

        return planMapper.insertSelective(plan) > 0;
    }

    /**
     * 更新出诊计划
     *
     * @param id    记录编号
     * @param param 出诊计划参数
     * @return 是否成功
     */
    @Override
    public boolean update(Long id, VisitPlanUpdateParam param) {

        VisitPlan plan = new VisitPlan();

        BeanUtils.copyProperties(param, plan);

        plan.setId(id);
        plan.setGmtModified(new Date());

        return planMapper.updateByPrimaryKeySelective(plan) > 0;
    }

    /**
     * 获取出诊计划
     *
     * @param id 计划编号
     * @return 出诊计划
     */
    @Override
    public Optional<VisitPlanDTO> getOptional(Long id) {
        return Optional.ofNullable(covert(planMapper.selectByPrimaryKey(id)));
    }

    /**
     * 删除出诊计划
     *
     * @param idList 计划编号
     * @return 是否成功
     */
    @Override
    public boolean deleteAll(List<Long> idList) {
        VisitPlanExample example = new VisitPlanExample();

        example.createCriteria()
                .andIdIn(idList);

        return planMapper.deleteByExample(example) > 0;
    }

    /**
     * 删除出诊计划
     *
     * @param id 计划编号
     * @return 是否成功
     */
    @Override
    public boolean delete(Long id) {
        return planMapper.deleteByPrimaryKey(id) > 0;
    }

    /**
     * 判断是否，存在该计划
     *
     * @param id 计划编号
     * @return 是否存在
     */
    @Override
    public boolean count(Long id) {

        VisitPlanExample example = new VisitPlanExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return planMapper.countByExample(example) > 0;
    }

    /**
     * 获取医生出诊信息
     *
     * @param doctorId 医生编号
     * @param start    开始日期
     * @param end      结束日期
     * @return 医生出诊信息
     */
    @Override
    public VisitDoctorPlanDTO getDoctorPlan(Long doctorId, Date start, Date end) {

        VisitDoctorPlanDTO dto = new VisitDoctorPlanDTO();

        // 设置医生信息
        if (hospitalDoctorService.getConvert(doctorId).isPresent()) {
            dto.setDoctorDTO(hospitalDoctorService.getConvert(doctorId).get());
        }

        // 设置医生出诊信息列表
        dto.setPlanListDTOList(getVisitPlanDTO(doctorId, start, end));

        return dto;
    }

    /**
     * 获取医生出诊信息
     *
     * @param hospitalId 医院编号
     * @param doctorId   医生编号
     * @param date       日期
     * @return 医生出诊信息
     */
    @Override
    public List<VisitPlanResiduesDTO> getDoctorPlanByDate(Long hospitalId, Long doctorId, Date date) {

        // 获取当天出诊计划
        VisitPlanExample example = new VisitPlanExample();

        example.createCriteria()
                .andDoctorIdEqualTo(doctorId)
                .andHospitalIdEqualTo(hospitalId)
                .andDayBetween(DateUtil.beginOfDay(date), DateUtil.endOfDay(date));

        List<VisitPlan> list = planMapper.selectByExample(example);

        if (CollUtil.isEmpty(list)) {
            return null;
        }

        return list.stream()
                .map(this::covertToResidues)
                .collect(Collectors.toList());
    }

    /**
     * 查找出诊列表
     *
     * @param hospitalId   医院编号
     * @param specialId    专科编号
     * @param outpatientId 门诊编号
     * @param doctorId     医生编号
     * @param day          出诊日期
     * @param pageNum      第几页
     * @param pageSize     页大小
     * @return 出诊列表
     */
    @Override
    public List<VisitPlanDTO> list(Long hospitalId, Long specialId, Long outpatientId, Long doctorId, Date day,
                                   Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        VisitPlanExample example = new VisitPlanExample();

        VisitPlanExample.Criteria criteria = example.createCriteria();

        if (hospitalId != null) {
            criteria.andHospitalIdEqualTo(hospitalId);
        }

        if (specialId != null) {
            criteria.andSpecialIdEqualTo(specialId);
        }

        if (outpatientId != null) {
            criteria.andOutpatientIdEqualTo(outpatientId);
        }

        if (doctorId != null) {
            criteria.andDoctorIdEqualTo(doctorId);
        }

        criteria.andDayBetween(DateUtil.beginOfDay(day), DateUtil.endOfDay(day));

        return planMapper.selectByExample(example).stream()
                .map(this::covert)
                .collect(Collectors.toList());
    }


    /**
     * 获取某段时间出诊计划
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 出诊计划编号
     */
    @Override
    public List<Long> list(Date start, Date end) {
        VisitPlanExample example = new VisitPlanExample();

        example.createCriteria()
                .andDayGreaterThanOrEqualTo(start)
                .andDayLessThanOrEqualTo(end);

        return planMapper.selectByExample(example).stream()
                .map(VisitPlan::getId).collect(Collectors.toList());
    }

    /**
     * 获取某天出诊计划信息
     *
     * @param doctorId 医生编号
     * @param time     时间段：1 上午，2 下午
     * @param day      某天
     * @return 出诊计划列表
     */
    @Override
    public List<VisitPlan> getByTimeAndDate(Long doctorId, Integer time, Date day) {
        VisitPlanExample example = new VisitPlanExample();

        example.createCriteria()
                .andDoctorIdEqualTo(doctorId)
                .andTimeEqualTo(time)
                .andDayGreaterThanOrEqualTo(DateUtil.beginOfDay(day))
                .andDayLessThanOrEqualTo(DateUtil.endOfDay(day));

        return planMapper.selectByExample(example);
    }

    /**
     * 转换为出诊计划封装类
     * 增加诊室地址、医生名称
     *
     * @param plan 出诊计划
     * @return 出诊计划封装类
     */
    private VisitPlanDTO covert(VisitPlan plan) {
        VisitPlanDTO dto = new VisitPlanDTO();

        BeanUtils.copyProperties(plan, dto);

        // 设置诊室地址
        dto.setClinicName(hospitalClinicService.getAddress(plan.getClinicId()));

        // 设置医生名称
        dto.setDoctorName(hospitalDoctorService.getName(plan.getDoctorId()));

        // 设置门诊名称
        dto.setOutpatientName(hospitalOutpatientService.getName(plan.getOutpatientId()));

        // 设置专科名称
        dto.setSpecialName(hospitalSpecialService.getName(plan.getSpecialId()));

        // 设置医院名称
        dto.setHospitalName(hospitalInfoService.getName(plan.getHospitalId()));

        return dto;
    }

    /**
     * 转换为出诊计划挂号封装类
     * 增加剩余挂号
     *
     * @param plan 出诊计划
     * @return 出诊计划封装类
     */
    private VisitPlanResiduesDTO covertToResidues(VisitPlan plan) {

        VisitPlanResiduesDTO dto = new VisitPlanResiduesDTO();

        BeanUtils.copyProperties(plan, dto);

        List<Integer> residueList = new ArrayList<>();


        TimePeriodEnum timePeriod = (plan.getTime().equals(TimePeriodEnum.AM.getTime())) ? TimePeriodEnum.AM : TimePeriodEnum.PM;

        for (int i = timePeriod.getStart(); i <= timePeriod.getEnd(); i++) {

            // 设置剩余号数
            residueList.add(MAX_OF_PATIENTS - appointmentService.countByPlanId(plan.getId(), plan.getTime()));
        }

        // 设置剩余号数
        dto.setResidues(residueList);

        return dto;
    }

    /**
     * 获取医生，某段时间，以后出诊信息列表
     *
     * @param doctorId 医生编号
     * @param start    获取该时间之后
     * @return 出诊列表
     */
    private List<VisitPlanListDTO> getVisitPlanDTO(Long doctorId, Date start, Date end) {

        // 查找医生出诊信息列表
        List<VisitPlanListDTO> planDTOList = new ArrayList<>();

        VisitPlanExample example = new VisitPlanExample();

        example.createCriteria()
                .andDoctorIdEqualTo(doctorId)
                .andDayBetween(DateUtil.beginOfDay(start), DateUtil.endOfDay(end));

        planMapper.selectByExample(example).stream()
                // 按照不同医院，进行分组
                .collect(Collectors.groupingBy(VisitPlan::getHospitalId, Collectors.toList()))

                // 按照不同医院，进行遍历
                .forEach((hospitalId, list) -> {

                    // 转换为医生出诊列表
                    VisitPlanListDTO dto = new VisitPlanListDTO();

                    // 设置医院信息
                    if (hospitalInfoService.getOptional(hospitalId).isPresent()) {
                        dto.setInfo(hospitalInfoService.getOptional(hospitalId).get());
                    }

                    // 设置医生出诊列表
                    if (CollUtil.isNotEmpty(list)) {
                        dto.setPlanResiduesDTOList(list.stream()
                                .map(this::covertToResidues)
                                .collect(Collectors.toList()));
                    }

                    // 设置对应医院中，医生出诊计划信息
                    planDTOList.add(dto);

                });

        return planDTOList;
    }

}
