package cn.yujian95.hospital.controller;

import cn.hutool.core.date.DateUtil;
import cn.yujian95.hospital.common.api.CommonPage;
import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.dto.*;
import cn.yujian95.hospital.dto.param.VisitAppointmentParam;
import cn.yujian95.hospital.entity.VisitAppointment;
import cn.yujian95.hospital.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Date;

import static cn.yujian95.hospital.dto.AppointmentEnum.*;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/19
 */

@Api(value = "出诊模块", tags = "出诊预约接口")
@RestController
@CrossOrigin
@RequestMapping("/visit")
public class VisitAppointmentController {

    @Resource
    private IPowerAccountService accountService;

    @Resource
    private IVisitAppointmentService appointmentService;

    @Resource
    private IUserMedicalCardService userMedicalCardService;

    @Resource
    private IHospitalDoctorService hospitalDoctorService;

    @ApiOperation(value = "添加预约信息", notes = "传入 预约参数对象（出诊编号、就诊卡号、账号编号）")
    @RequestMapping(value = "/appointment", method = RequestMethod.POST)
    public CommonResult insertAppointment(@RequestBody VisitAppointmentParam param) {

        if (appointmentService.insert(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation(value = "修改预约状态：取消", notes = "传入 预约编号")
    @RequestMapping(value = "/appointment/cancel/{id}", method = RequestMethod.PUT)
    public CommonResult cancelAppointment(@PathVariable Long id) {

        return updateAppointmentStatus(id, CANCEL.getStatus());
    }

    @ApiOperation(value = "修改预约状态：失信", notes = "传入 预约编号")
    @RequestMapping(value = "/appointment/miss/{id}", method = RequestMethod.PUT)
    public CommonResult missAppointment(@PathVariable Long id) {

        return updateAppointmentStatus(id, MISSING.getStatus());
    }

    @ApiOperation(value = "修改预约状态：完成", notes = "传入 预约编号")
    @RequestMapping(value = "/appointment/finish/{id}", method = RequestMethod.PUT)
    public CommonResult finishAppointment(@PathVariable Long id) {

        return updateAppointmentStatus(id, FINISH.getStatus());
    }

    @ApiOperation(value = "查找挂号记录", notes = "传入就诊卡编号、挂号状态")
    @RequestMapping(value = "/appointment/search", method = RequestMethod.GET)
    public CommonResult<CommonPage<VisitAppointment>> searchAppointment(@RequestParam(required = false) Long cardId,
                                                                        @RequestParam(required = false) Integer status,
                                                                        @RequestParam Integer pageNum,
                                                                        @RequestParam Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(appointmentService.list(cardId, status, pageNum, pageSize)));
    }

    @ApiOperation(value = "获取所有挂号记录", notes = "传入就诊卡编号、账号编号")
    @RequestMapping(value = "/appointment/all", method = RequestMethod.GET)
    public CommonResult<CommonPage<VisitAppointmentWithQueueDTO>> listAllAppointment(@RequestParam Long cardId,
                                                                                     @RequestParam Long accountId,
                                                                                     @RequestParam Integer pageNum,
                                                                                     @RequestParam Integer pageSize) {

        if (!userMedicalCardService.countCardId(cardId)) {
            return CommonResult.validateFailed("不存在，该就诊卡编号！");
        }

        if (!accountService.count(accountId)) {
            return CommonResult.validateFailed("不存在，该账号编号！");
        }

        return CommonResult.success(CommonPage.restPage(appointmentService.listAllAppointment(cardId, accountId, pageNum, pageSize)));
    }

    @ApiOperation(value = "获取失信记录详情", notes = "传入预约编号")
    @RequestMapping(value = "/appointment/miss/details", method = RequestMethod.GET)
    public CommonResult<VisitAppointmentWithQueueDTO> listAllAppointment(@RequestParam Long appointmentId) {

        if (!appointmentService.count(appointmentId)) {
            return CommonResult.validateFailed("不存在，该预约编号！");
        }

        return CommonResult.success(appointmentService.getAppointmentDetails(appointmentId));
    }

    @ApiOperation(value = "获取就诊记录列表", notes = "传入就诊卡编号")
    @RequestMapping(value = "/appointment/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<VisitAppointmentDTO>> listAppointment(@RequestParam Long cardId, @RequestParam Integer pageNum,
                                                                         @RequestParam Integer pageSize) {

        if (!userMedicalCardService.countCardId(cardId)) {
            return CommonResult.validateFailed("不存在，该就诊卡编号！");
        }

        return CommonResult.success(CommonPage.restPage(appointmentService.listFinishAppointment(cardId, pageNum, pageSize)));
    }

    @ApiOperation(value = "查看就诊记录详情", notes = "传入就诊卡编号")
    @RequestMapping(value = "/appointment/details", method = RequestMethod.GET)
    public CommonResult<VisitAppointmentWithCaseDTO> getAppointmentDetails(@RequestParam Long appointmentId) {

        if (!appointmentService.count(appointmentId)) {
            return CommonResult.validateFailed("不存在，该预约编号！");
        }

        return CommonResult.success(appointmentService.getVisitAppointmentWithCaseDTO(appointmentId));
    }

    @ApiOperation(value = "查看当天排队信息", notes = "传入就诊卡编号、账号编号")
    @RequestMapping(value = "/appointment/today", method = RequestMethod.GET)
    public CommonResult<CommonPage<VisitAppointmentQueueDTO>> getTodayAppointment(@RequestParam Long cardId,
                                                                                  @RequestParam Long accountId,
                                                                                  @RequestParam String date) {

        if (!userMedicalCardService.countCardId(cardId)) {
            return CommonResult.validateFailed("不存在，该就诊卡编号！");
        }

        if (!accountService.count(accountId)) {
            return CommonResult.validateFailed("不存在，该账号编号！");
        }

        return CommonResult.success(CommonPage.restPage(appointmentService.listTodayQueue(DateUtil.parseDate(date), cardId, accountId)));
    }

    @ApiOperation(value = "查看用户预约情况", notes = "传入医生编号、日期、时间段（上午：1、下午：2）")
    @RequestMapping(value = "/appointment/user", method = RequestMethod.GET)
    public CommonResult<CommonPage<VisitUserInfoDTO>> listVisitUserInfo(@RequestParam Long doctorId, @RequestParam String date,
                                                                        @RequestParam Integer time, @RequestParam Integer pageNum,
                                                                        @RequestParam Integer pageSize) {

        if (!hospitalDoctorService.count(doctorId)) {
            return CommonResult.validateFailed("不存在，该医生编号");
        }

        Date day = DateUtil.parse(date);

        return CommonResult.success(CommonPage.restPage(appointmentService.listVisitUserInfo(doctorId, time, day, pageNum, pageSize)));
    }

    @ApiOperation(value = "查看获取预约诊室名称", notes = "传入医生编号、日期、时间段（上午：1、下午：2）")
    @RequestMapping(value = "/appointment/clinic", method = RequestMethod.GET)
    public CommonResult getClinicName(@RequestParam Long doctorId, @RequestParam String date,
                                      @RequestParam Integer time) {

        if (!hospitalDoctorService.count(doctorId)) {
            return CommonResult.validateFailed("不存在，该医生编号");
        }

        Date day = DateUtil.parse(date);

        return CommonResult.success(appointmentService.getClinicName(doctorId, time, day));
    }

    /**
     * 更新预订状态
     *
     * @param id     预订编号
     * @param status 状态：1 失约，2 取消， 3 完成
     * @return 更新情况
     */
    private CommonResult updateAppointmentStatus(Long id, Integer status) {
        if (!appointmentService.count(id)) {
            return CommonResult.validateFailed("不存在，该预约编号！");
        }

        if (appointmentService.update(id, status)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }
}
