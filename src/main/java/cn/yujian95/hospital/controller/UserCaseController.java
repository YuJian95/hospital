package cn.yujian95.hospital.controller;

import cn.yujian95.hospital.common.api.CommonPage;
import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.dto.param.UserCaseParam;
import cn.yujian95.hospital.dto.param.UserCaseUpdateParam;
import cn.yujian95.hospital.entity.UserCase;
import cn.yujian95.hospital.service.IHospitalDoctorService;
import cn.yujian95.hospital.service.IPowerAccountService;
import cn.yujian95.hospital.service.IUserCaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

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
    private IPowerAccountService powerAccountService;

    @Resource
    private IHospitalDoctorService hospitalDoctorService;

    @ApiOperation(value = "添加用户病例", notes = "传入 用户病例参数（账号编号、预约编号、医生编号、病例详情）")
    @RequestMapping(value = "/case", method = RequestMethod.POST)
    public CommonResult insertUserCase(@RequestBody UserCaseParam param) {

        if (!powerAccountService.count(param.getAccountId())) {
            return CommonResult.validateFailed("不存在，该账号编号！");
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
    @ApiImplicitParam(name = "accountId", value = "账号编号", paramType = "query", dataType = "Long", required = true)
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
    @ApiImplicitParam(name = "accountId", value = "账号编号", paramType = "query", dataType = "Long", required = true)
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

    @ApiOperation(value = "获取用户病例", notes = "传入 账号编号、预定编号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountId", value = "账号编号", paramType = "query", dataType = "Long",
                    required = true),
            @ApiImplicitParam(name = "orderId", value = "预约编号", paramType = "query", dataType = "Long",
                    required = true),
    })
    @RequestMapping(value = "/case", method = RequestMethod.GET)
    public CommonResult getUserCase(@RequestParam Long accountId, @RequestParam Long orderId) {

        if (!powerAccountService.count(accountId)) {
            return CommonResult.validateFailed("不存在，该账号编号！");
        }

        Optional<UserCase> userCaseOptional = caseService.getOptional(accountId, orderId);

        if (userCaseOptional.isPresent()) {
            return CommonResult.success(userCaseOptional.get());
        }

       return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "查找用户病例", notes = "传入 账号编号、第几页、页大小")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountId", value = "账号编号", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true),
    })
    @RequestMapping(value = "/case/list", method = RequestMethod.GET)
    public CommonResult listUserCase(@RequestParam(required = false) Long accountId,
                                     @RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(caseService.list(accountId, pageNum, pageSize)));
    }

}
