package cn.yujian95.hospital.controller;

import cn.yujian95.hospital.common.api.CommonPage;
import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.dto.param.VisitPlanParam;
import cn.yujian95.hospital.service.IHospitalClinicService;
import cn.yujian95.hospital.service.IHospitalDoctorService;
import cn.yujian95.hospital.service.IPowerAccountService;
import cn.yujian95.hospital.service.IVisitPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

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
    private IHospitalDoctorService hospitalDoctorService;

    @Resource
    private IHospitalClinicService hospitalClinicService;


    @ApiOperation(value = "添加出诊计划", notes = "传入 出诊计划参数（医院编号、专科编号、门诊编号、诊室编号、医生编号、出诊日期）")
    @RequestMapping(value = "/plan", method = RequestMethod.POST)
    public CommonResult insertVisitPlan(@RequestBody VisitPlanParam param) {

        if (!hospitalDoctorService.count(param.getDoctorId())) {
            return CommonResult.validateFailed("不存在，该医生编号！");
        }

        if (!hospitalClinicService.count(param.getClinicId())) {
            return CommonResult.validateFailed("不存在，该诊室编号！");
        }

        if (planService.insert(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation(value = "更新出诊计划", notes = "传入 出诊编号、出诊计划参数（医院编号、专科编号、门诊编号、诊室编号、医生编号、出诊日期）")
    @ApiImplicitParam(name = "id", value = "出诊编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/plan/{id}", method = RequestMethod.PUT)
    public CommonResult updateVisitPlan(@PathVariable Long id, @RequestBody VisitPlanParam param) {

        if (!planService.count(id)) {
            return CommonResult.validateFailed("不存在，该出诊编号");
        }

        if (!hospitalDoctorService.count(param.getDoctorId())) {
            return CommonResult.validateFailed("不存在，该医生编号！");
        }

        if (!hospitalClinicService.count(param.getClinicId())) {
            return CommonResult.validateFailed("不存在，该诊室编号！");
        }

        if (planService.update(id, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation(value = "搜索出诊计划", notes = "传入 医生编号、第几页、页大小")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hospitalId", value = "医院编号", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "specialId", value = "专科编号", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "outpatientId", value = "门诊编号", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "day", value = "出诊日期", paramType = "query", dataType = "Date",
                    required = true),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true),
    })
    @RequestMapping(value = "/plan/list", method = RequestMethod.GET)
    public CommonResult searchVisitPlan(@RequestParam(required = false) Long hospitalId,
                                        @RequestParam(required = false) Long specialId,
                                        @RequestParam(required = false) Long outpatientId,
                                        @RequestParam Date day,
                                        @RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(planService.list(hospitalId, specialId, outpatientId, null,
                day, pageNum, pageSize)));
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

        return CommonResult.failed();
    }
}
