package cn.yujian95.hospital.service;

import cn.yujian95.hospital.dto.param.VisitAppointmentParam;
import cn.yujian95.hospital.entity.VisitAppointment;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/19
 */

public interface IVisitAppointmentService {


    /**
     * 插入预约记录
     *
     * @param param 就诊预约参数
     * @return 是否成功
     */
    boolean insert(VisitAppointmentParam param);

    /**
     * 更新预约状态
     *
     * @param id     预约编号
     * @param status 预约状态 0：未开始，1：未按时就诊，2：取消预约挂号，3：已完成
     * @return 是否成功
     */
    boolean update(Long id, Integer status);

    /**
     * 获取预约详情
     *
     * @param id 预订编号
     * @return 预定信息
     */
    Optional<VisitAppointment> getOptional(Long id);

    /**
     * 获取预约记录
     *
     * @param cardId   预约编号
     * @param status   预约状态 0：未开始，1：未按时就诊，2：取消预约挂号，3：已完成
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 预约记录
     */
    List<VisitAppointment> list(Long cardId, Integer status, Integer pageNum, Integer pageSize);

    /**
     * 判断预订是否存在
     *
     * @param id 预定编号
     * @return 是否存在
     */
    boolean count(Long id);

    /**
     * 获取已取号的数目
     *
     * @param planId     出诊编号
     * @param timePeriod 时间段
     * @return 已取号数目
     */
    int countByPlanId(Long planId, Integer timePeriod);

    /**
     * 判断是否，已预约
     *
     * @param cardId 就诊卡号
     * @param planId 出诊编号
     * @return 是否存在
     */
    boolean count(Long cardId, Long planId);
}
