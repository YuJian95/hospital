package cn.yujian95.hospital.controller;

import cn.yujian95.hospital.common.api.CommonPage;
import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.dto.UserCreditDTO;
import cn.yujian95.hospital.entity.VisitAppointment;
import cn.yujian95.hospital.service.IPowerAccountService;
import cn.yujian95.hospital.service.IUserMedicalCardService;
import cn.yujian95.hospital.service.IVisitAppointmentService;
import cn.yujian95.hospital.service.IVisitBlacklistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static cn.yujian95.hospital.dto.AppointmentEnum.MISSING;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/4/21
 */

@Api(value = "出诊模块", tags = "出诊信用接口")
@RestController
@CrossOrigin
@RequestMapping("/visit")
public class VisitCreditController {

    @Resource
    private IVisitBlacklistService blacklistService;

    @Resource
    private IUserMedicalCardService userMedicalCardService;

    @Resource
    private IPowerAccountService accountService;

    @Resource
    private IVisitAppointmentService appointmentService;

    @ApiOperation(value = "验证就诊卡号是否黑名单", notes = "传入 就诊卡号")
    @RequestMapping(value = "/black/verify", method = RequestMethod.GET)
    public CommonResult verifyBlack(@RequestParam Long cardId) {
        if (!userMedicalCardService.countCardId(cardId)) {
            return CommonResult.validateFailed("不存在，该就诊卡号！");
        }

        return CommonResult.success(blacklistService.insert(cardId));
    }

    @ApiOperation(value = "获取当月信用详情", notes = "传入 账号编号、就诊卡编号")
    @RequestMapping(value = "/credit/current", method = RequestMethod.GET)
    public CommonResult<UserCreditDTO> getCurrentCredit(@RequestParam Long accountId, @RequestParam Long cardId) {

        if (!userMedicalCardService.countCardId(cardId)) {
            return CommonResult.validateFailed("不存在，该就诊卡编号！");
        }

        if (!accountService.count(accountId)) {
            return CommonResult.validateFailed("不存在，该账号编号！");
        }

        return CommonResult.success(appointmentService.getCurrentCredit(accountId, cardId));
    }

    @ApiOperation(value = "获取以往信用详情", notes = "传入 账号编号、就诊卡编号")
    @RequestMapping(value = "/credit/all", method = RequestMethod.GET)
    public CommonResult<UserCreditDTO> getAllCredit(@RequestParam Long accountId, @RequestParam Long cardId) {

        if (!userMedicalCardService.countCardId(cardId)) {
            return CommonResult.validateFailed("不存在，该就诊卡编号！");
        }

        if (!accountService.count(accountId)) {
            return CommonResult.validateFailed("不存在，该账号编号！");
        }

        return CommonResult.success(appointmentService.getAllCredit(accountId, cardId));
    }

    @ApiOperation(value = "获取失信记录", notes = "传入就诊卡编号")
    @RequestMapping(value = "/credit/miss", method = RequestMethod.GET)
    public CommonResult<CommonPage<VisitAppointment>> listMissRecord(@RequestParam Long cardId,
                                                                     @RequestParam Integer pageNum,
                                                                     @RequestParam Integer pageSize) {

        // TODO 需要帮人帮人挂号的失信情况
        // TODO 需要整合具体出诊计划日期
        return CommonResult.success(CommonPage.restPage(appointmentService.list(cardId, MISSING.getStatus(), pageNum, pageSize)));

    }
}
