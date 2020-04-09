package cn.yujian95.hospital.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.yujian95.hospital.common.api.CommonPage;
import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.dto.VisitDoctorPlanDTO;
import cn.yujian95.hospital.dto.VisitPlanDTO;
import cn.yujian95.hospital.dto.VisitPlanResiduesDTO;
import cn.yujian95.hospital.dto.param.VisitPlanParam;
import cn.yujian95.hospital.dto.param.VisitPlanUpdateParam;
import cn.yujian95.hospital.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/2
 */

@Api(value = "出诊模块", tags = "出诊计划接口")
@RestController
@CrossOrigin
@RequestMapping("/visit")
public class VisitPlanController {

    @Resource
    private IVisitPlanService planService;

    @Resource
    private IHospitalInfoService hospitalInfoService;

    @Resource
    private IHospitalDoctorService hospitalDoctorService;

    @Resource
    private IHospitalOutpatientService hospitalOutpatientService;

    @Resource
    private IHospitalClinicService hospitalClinicService;

    @Resource
    private IHospitalSpecialService hospitalSpecialService;


    @ApiOperation(value = "添加出诊计划", notes = "传入 出诊计划参数（医院编号、专科编号、门诊编号、诊室编号、医生编号、出诊时间段（1：上午，2：下午）、出诊日期）")
    @RequestMapping(value = "/plan", method = RequestMethod.POST)
    public CommonResult insertVisitPlan(@RequestBody VisitPlanParam param) {

        if (!hospitalDoctorService.count(param.getDoctorId())) {
            return CommonResult.validateFailed("不存在，该医生编号！");
        }

        if (!hospitalInfoService.count(param.getHospitalId())) {
            return CommonResult.validateFailed("不存在，该医院编号！");
        }

        if (!hospitalSpecialService.count(param.getSpecialId())) {
            return CommonResult.validateFailed("不存在，该专科编号！");
        }

        if (!hospitalOutpatientService.count(param.getOutpatientId())) {
            return CommonResult.validateFailed("不存在，该门诊编号!");
        }

        if (!hospitalClinicService.count(param.getClinicId())) {
            return CommonResult.validateFailed("不存在，该诊室编号！");
        }

        if (param.getTime() > 2 || param.getTime() < 1) {
            return CommonResult.validateFailed("不存在，该出诊时间段（1：上午，2：下午）！");
        }

        if (planService.insertAll(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "更新出诊计划", notes = "传入 出诊编号、出诊计划参数（医院编号、专科编号、门诊编号、诊室编号、医生编号、出诊日期）")
    @ApiImplicitParam(name = "id", value = "出诊编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/plan/{id}", method = RequestMethod.PUT)
    public CommonResult updateVisitPlan(@PathVariable Long id, @RequestBody VisitPlanUpdateParam param) {

        if (!hospitalDoctorService.count(param.getDoctorId())) {
            return CommonResult.validateFailed("不存在，该医生编号！");
        }

        if (!hospitalInfoService.count(param.getHospitalId())) {
            return CommonResult.validateFailed("不存在，该医院编号！");
        }

        if (!hospitalSpecialService.count(param.getSpecialId())) {
            return CommonResult.validateFailed("不存在，该专科编号！");
        }

        if (!hospitalOutpatientService.count(param.getOutpatientId())) {
            return CommonResult.validateFailed("不存在，该门诊编号!");
        }

        if (!hospitalClinicService.count(param.getClinicId())) {
            return CommonResult.validateFailed("不存在，该诊室编号！");
        }

        if (planService.update(id, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "搜索出诊计划", notes = "传入 医院编号、专科编号、门诊编号、出诊日期、第几页、页大小")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hospitalId", value = "医院编号", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "specialId", value = "专科编号", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "outpatientId", value = "门诊编号", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "day", value = "出诊日期", paramType = "query", dataType = "String",
                    required = true),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true),
    })
    @RequestMapping(value = "/plan/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<VisitPlanDTO>> searchVisitPlan(@RequestParam(required = false) Long hospitalId,
                                                                  @RequestParam(required = false) Long specialId,
                                                                  @RequestParam(required = false) Long outpatientId,
                                                                  @RequestParam String day,
                                                                  @RequestParam Integer pageNum,
                                                                  @RequestParam Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(planService.list(hospitalId, specialId, outpatientId, null,
                DateUtil.parse(day), pageNum, pageSize)));
    }

    @ApiOperation(value = "删除出诊计划", notes = "传入 出诊编号")
    @ApiImplicitParam(name = "id", value = "出诊编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/plan/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteVisitPlan(@PathVariable Long id) {

        if (!planService.count(id)) {
            return CommonResult.validateFailed("不存在，该出诊编号");
        }

        if (planService.delete(id)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "批量删除出诊计划", notes = "传入 出诊编号列表")
    @ApiImplicitParam(name = "id", value = "出诊编号列表", paramType = "query", dataType = "List<Long>", required = true)
    @RequestMapping(value = "/plan/all", method = RequestMethod.DELETE)
    public CommonResult deleteAllVisitPlan(@RequestParam List<Long> idList) {

        if (CollUtil.isEmpty(idList)) {
            return CommonResult.validateFailed("出诊编号列表为空！");
        }

        if (planService.deleteAll(idList)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "根据医生，获取出诊信息", notes = "传入 医生编号、开始日期、结束日期")
    @RequestMapping(value = "/plan/doctor", method = RequestMethod.GET)
    public CommonResult<VisitDoctorPlanDTO> searchVisitPlanByDoctor(@RequestParam Long doctorId,
                                                                    @RequestParam String startDate,
                                                                    @RequestParam String endDate) {

        if (!hospitalDoctorService.count(doctorId)) {
            return CommonResult.validateFailed("不存在，该医生编号！");
        }

        Date start = DateUtil.parseDate(startDate);
        Date end = DateUtil.parseDate(endDate);

        if (start.getTime() > end.getTime()) {
            return CommonResult.validateFailed("不存在，该日期时间段");
        }

        return CommonResult.success(planService.getDoctorPlan(doctorId, start, end));
    }

    @ApiOperation(value = "根据医生编号、日期，获取出诊信息", notes = "传入 医生编号、日期")
    @RequestMapping(value = "/plan/doctor/date", method = RequestMethod.GET)
    public CommonResult<List<VisitPlanResiduesDTO>> searchVisitPlanByDoctorAndDate(@RequestParam Long hospitalId,
                                                                                   @RequestParam Long doctorId,
                                                                                   @RequestParam String date) {

        if (!hospitalInfoService.count(hospitalId)) {
            return CommonResult.validateFailed("不存在，该医院编号！");
        }

        if (!hospitalDoctorService.count(doctorId)) {
            return CommonResult.validateFailed("不存在，该医生编号！");
        }

        Date time = DateUtil.parseDate(date);

        return CommonResult.success(planService.getDoctorPlanByDate(hospitalId, doctorId, time));
    }
}
