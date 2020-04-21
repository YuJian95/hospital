package cn.yujian95.hospital.controller;

import cn.yujian95.hospital.common.api.CommonPage;
import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.dto.param.UserCaseParam;
import cn.yujian95.hospital.dto.param.UserCaseUpdateParam;
import cn.yujian95.hospital.entity.UserCase;
import cn.yujian95.hospital.service.IHospitalDoctorService;
import cn.yujian95.hospital.service.IUserCaseService;
import cn.yujian95.hospital.service.IUserMedicalCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/2
 */

@Api(value = "用户模块", tags = "用户病例接口")
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserCaseController {

    @Resource
    private IUserCaseService caseService;

    @Resource
    private IUserMedicalCardService cardService;

    @Resource
    private IHospitalDoctorService hospitalDoctorService;

    @ApiOperation(value = "添加用户病例", notes = "传入 用户病例参数（就诊卡编号、预约编号、医生编号、病例详情）")
    @RequestMapping(value = "/case", method = RequestMethod.POST)
    public CommonResult insertUserCase(@RequestBody UserCaseParam param) {

        if (!cardService.countCardId(param.getCardId())) {
            return CommonResult.validateFailed("不存在，该就诊卡编号！");
        }

        if (!hospitalDoctorService.count(param.getDoctorId())) {
            return CommonResult.validateFailed("不存在，该医生编号！");
        }

        if (caseService.insert(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "更新用户病例", notes = "传入 病例编号、用户病例更新参数（病例详情）")
    @ApiImplicitParam(name = "cardId", value = "就诊卡编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/case/{id}", method = RequestMethod.PUT)
    public CommonResult updateUserCase(@PathVariable Long id, @RequestBody UserCaseUpdateParam param) {
        if (!caseService.count(id)) {
            return CommonResult.validateFailed("不存在，该病例编号！");
        }

        if (caseService.update(id, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "删除用户病例", notes = "传入 病例编号")
    @ApiImplicitParam(name = "cardId", value = "就诊卡编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/case/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteUserCase(@PathVariable Long id) {

        if (!caseService.count(id)) {
            return CommonResult.validateFailed("不存在，该病例编号！");
        }

        if (caseService.delete(id)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");

    }

    @ApiOperation(value = "获取用户病例", notes = "传入 就诊卡编号、预约编号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cardId", value = "就诊卡编号", paramType = "query", dataType = "Long",
                    required = true),
            @ApiImplicitParam(name = "appointmentId", value = "预约编号", paramType = "query", dataType = "Long",
                    required = true),
    })
    @RequestMapping(value = "/case", method = RequestMethod.GET)
    public CommonResult<UserCase> getUserCase(@RequestParam Long cardId, @RequestParam Long appointmentId) {

        if (!cardService.countCardId(cardId)) {
            return CommonResult.validateFailed("不存在，该就诊卡编号！");
        }

        return CommonResult.success(caseService.get(cardId, appointmentId));
    }

    @ApiOperation(value = "查找用户病例", notes = "传入 就诊卡编号、第几页、页大小")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cardId", value = "就诊卡编号", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true),
    })
    @RequestMapping(value = "/case/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserCase>> listUserCase(@RequestParam(required = false) Long cardId,
                                                           @RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(caseService.list(cardId, pageNum, pageSize)));
    }

}
