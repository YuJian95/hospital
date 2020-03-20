package cn.yujian95.hospital.controller;

import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.dto.param.VisitAppointmentParam;
import cn.yujian95.hospital.service.IUserMedicalCardService;
import cn.yujian95.hospital.service.IVisitAppointmentService;
import cn.yujian95.hospital.service.IVisitBlacklistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/19
 */

@Api(value = "出诊模块", tags = "出诊预约接口")
@RestController
@CrossOrigin
@RequestMapping("/visit")
public class VisitAppointmentController {

    private static final Integer MISS = 1;
    private static final Integer CANCEL = 2;
    private static final Integer FINISH = 3;

    @Resource
    private IVisitBlacklistService blacklistService;

    @Resource
    private IVisitAppointmentService appointmentService;

    @Resource
    private IUserMedicalCardService userMedicalCardService;

    @ApiOperation(value = "验证就诊卡号是否黑名单", notes = "传入 就诊卡号")
    @RequestMapping(value = "/black/verify", method = RequestMethod.GET)
    public CommonResult verifyBlack(@RequestParam Long cardId) {
        if (!userMedicalCardService.countCardId(cardId)) {
            return CommonResult.validateFailed("不存在，该就诊卡号！");
        }

        return CommonResult.success(blacklistService.insert(cardId));
    }

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

        return updateAppointmentStatus(id, CANCEL);
    }

    @ApiOperation(value = "修改预约状态：失信", notes = "传入 预约编号")
    @RequestMapping(value = "/appointment/miss/{id}", method = RequestMethod.PUT)
    public CommonResult missAppointment(@PathVariable Long id) {

        return updateAppointmentStatus(id, MISS);
    }

    @ApiOperation(value = "修改预约状态：完成", notes = "传入 预约编号")
    @RequestMapping(value = "/appointment/finish/{id}", method = RequestMethod.PUT)
    public CommonResult finishAppointment(@PathVariable Long id) {

        return updateAppointmentStatus(id, FINISH);
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
