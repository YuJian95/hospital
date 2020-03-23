package cn.yujian95.hospital.service;

import cn.yujian95.hospital.dto.VisitDoctorPlanDTO;
import cn.yujian95.hospital.dto.VisitPlanDTO;
import cn.yujian95.hospital.dto.param.VisitPlanParam;
import cn.yujian95.hospital.dto.param.VisitPlanUpdateParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
    @Transactional
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
     * @param date     出诊日期
     * @return 医生出诊信息
     */
    VisitDoctorPlanDTO getDoctorPlan(Long doctorId, Date date);

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
}
