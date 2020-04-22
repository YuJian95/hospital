package cn.yujian95.hospital.service;

import cn.yujian95.hospital.dto.VisitDoctorPlanDTO;
import cn.yujian95.hospital.dto.VisitPlanDTO;
import cn.yujian95.hospital.dto.VisitPlanResiduesDTO;
import cn.yujian95.hospital.dto.param.VisitPlanParam;
import cn.yujian95.hospital.dto.param.VisitPlanUpdateParam;
import cn.yujian95.hospital.entity.VisitPlan;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/1
 */

public interface IVisitPlanService {

    /**
     * 创建出诊计划
     *
     * @param param 出诊计划参数
     * @return 是否成功
     */
    boolean insertAll(VisitPlanParam param);

    /**
     * 更新出诊计划
     *
     * @param id    记录编号
     * @param param 出诊计划参数
     * @return 是否成功
     */
    boolean update(Long id, VisitPlanUpdateParam param);

    /**
     * 删除出诊计划
     *
     * @param id 计划编号
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 删除出诊计划
     *
     * @param idList 计划编号
     * @return 是否成功
     */
    boolean deleteAll(List<Long> idList);

    /**
     * 获取出诊计划
     *
     * @param id 计划编号
     * @return 出诊计划
     */
    Optional<VisitPlanDTO> getOptional(Long id);

    /**
     * 判断是否，存在该计划
     *
     * @param id 计划编号
     * @return 是否存在
     */
    boolean count(Long id);

    /**
     * 获取医生出诊信息
     *
     * @param doctorId 医生编号
     * @param start    开始日期
     * @param end      结束日期
     * @return 医生出诊信息
     */
    VisitDoctorPlanDTO getDoctorPlan(Long doctorId, Date start, Date end);

    /**
     * 查找出诊列表
     *
     * @param hospitalId 医院编号
     * @param specialId  专科编号
     * @param clinicId   门诊编号
     * @param doctorId   医生编号
     * @param day        出诊日期
     * @param pageNum    第几页
     * @param pageSize   页大小
     * @return 出诊列表
     */
    List<VisitPlanDTO> list(Long hospitalId, Long specialId, Long clinicId, Long doctorId, Date day,
                            Integer pageNum, Integer pageSize);

    /**
     * 获取医生出诊信息
     *
     * @param hospitalId 医院编号
     * @param doctorId   医生编号
     * @param date       日期
     * @return 医生出诊信息
     */
    List<VisitPlanResiduesDTO> getDoctorPlanByDate(Long hospitalId, Long doctorId, Date date);

    /**
     * 获取某天出诊计划信息
     *
     * @param doctorId 医生编号
     * @param time     时间段：1 上午，2 下午
     * @param day      某天
     * @return 出诊计划列表
     */
    List<VisitPlan> getByTimeAndDate(Long doctorId, Integer time, Date day);

    /**
     * 获取某段时间出诊计划
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 出诊计划编号
     */
    List<Long> list(Date start, Date end);
}
