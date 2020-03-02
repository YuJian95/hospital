package cn.yujian95.hospital.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.yujian95.hospital.dto.param.VisitPlanParam;
import cn.yujian95.hospital.entity.VisitPlan;
import cn.yujian95.hospital.entity.VisitPlanExample;
import cn.yujian95.hospital.mapper.VisitPlanMapper;
import cn.yujian95.hospital.service.IVisitPlanService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/1
 */

@Service
public class VisitPlanServiceImpl implements IVisitPlanService {

    @Resource
    private VisitPlanMapper planMapper;

    /**
     * 创建出诊计划
     *
     * @param param 出诊计划参数
     * @return 是否成功
     */
    @Override
    public boolean insert(VisitPlanParam param) {

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
    public boolean update(Long id, VisitPlanParam param) {

        VisitPlan plan = new VisitPlan();

        BeanUtils.copyProperties(param, plan);

        plan.setId(id);
        plan.setGmtModified(new Date());

        return planMapper.updateByPrimaryKeySelective(plan) > 0;
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
    public List<VisitPlan> list(Long hospitalId, Long specialId, Long outpatientId, Long doctorId, Date day,
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
            criteria.andOutpatientEqualTo(outpatientId);
        }

        if (doctorId != null) {
            criteria.andDoctorIdEqualTo(doctorId);
        }

        criteria.andDayBetween(DateUtil.beginOfDay(day), DateUtil.endOfDay(day));

        return planMapper.selectByExample(example);
    }
}
