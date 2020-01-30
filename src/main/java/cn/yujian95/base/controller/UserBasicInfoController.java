package cn.yujian95.base.controller;

import cn.yujian95.base.common.api.CommonPage;
import cn.yujian95.base.common.api.CommonResult;
import cn.yujian95.base.dto.param.PowerAccountRegisterParam;
import cn.yujian95.base.dto.param.UserBasicInfoParam;
import cn.yujian95.base.entity.UserBasicInfo;
import cn.yujian95.base.service.IPowerAccountService;
import cn.yujian95.base.service.IUserBasicInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/29
 */

@Api(value = "用户模块", tags = "用户信息接口")
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserBasicInfoController {

    @Resource
    private IUserBasicInfoService basicInfoService;

    @Resource
    private IPowerAccountService powerAccountService;

    @ApiOperation(value = "发送注册短信", notes = "传入 手机号")
    @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", dataType = "String",
            required = true)
    @RequestMapping(value = "/basic/message", method = RequestMethod.GET)
    public CommonResult sendRegisterMessage(@RequestParam String phone) {

        if (StringUtils.isEmpty(phone)) {
            return CommonResult.validateFailed("手机号码不能未空！");
        }

        if (powerAccountService.count(phone)) {
            return CommonResult.validateFailed("该账号，已注册！");
        }

        if (basicInfoService.sendMessage(phone)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation(value = "校验短信验证码", notes = "传入 手机号、短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", dataType = "String",
                    required = true),
            @ApiImplicitParam(name = "code", value = "短信验证码", paramType = "query", dataType = "String",
                    required = true)
    })
    @RequestMapping(value = "/basic/code", method = RequestMethod.POST)
    public CommonResult<Boolean> verifyCode(@RequestParam String phone, @RequestParam String code) {
        return CommonResult.success(basicInfoService.verifyCode(phone, code));
    }

    @ApiOperation(value = "用户账号注册", notes = "传入 注册对象参数（账号名称、密码）")
    @RequestMapping(value = "/basic/account/register", method = RequestMethod.POST)
    public CommonResult registerUserAccount(@RequestBody PowerAccountRegisterParam param) {
        if (powerAccountService.count(param.getName())) {
            return CommonResult.validateFailed("该账号名称已存在！");
        }

        if (powerAccountService.registerAdmin(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "更新用户基础信息", notes = "传入 用户编号、用户信息参数（姓名、性别、出生日期）")
    @ApiImplicitParam(name = "id", value = "用户编号", paramType = "path", dataType = "Long",
            required = true)
    @RequestMapping(value = "/basic/{id}", method = RequestMethod.PUT)
    public CommonResult updateBasicInfo(@PathVariable Long id, @RequestBody UserBasicInfoParam param) {
        if (!basicInfoService.count(id)) {
            return CommonResult.validateFailed("不存在，该用户编号！");
        }

        if (basicInfoService.update(id, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation(value = "添加用户基础信息", notes = "传入 手机号、用户信息参数（姓名、性别、出生日期）")
    @ApiImplicitParam(name = "phone", value = "手机号", paramType = "path", dataType = "Long",
            required = true)
    @RequestMapping(value = "/basic/{phone}", method = RequestMethod.PUT)
    public CommonResult insertBasicInfo(@PathVariable String phone, @RequestBody UserBasicInfoParam param) {

        if (powerAccountService.count(phone)) {
            return CommonResult.validateFailed("该账号，还未注册！");
        }

        if (basicInfoService.insert(phone, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation(value = "删除用户基础信息", notes = "传入 用户编号")
    @ApiImplicitParam(name = "id", value = "用户编号", paramType = "path", dataType = "Long",
            required = true)
    @RequestMapping(value = "/basic/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('user:basic:delete')")
    public CommonResult deleteBasicInfo(@PathVariable Long id) {
        if (!basicInfoService.count(id)) {
            return CommonResult.validateFailed("不存在，该用户编号！");
        }

        if (basicInfoService.delete(id)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation(value = "分页：搜索用户信息", notes = "传入 用户姓名、手机号、性别、第几页、页大小")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户姓名", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别", paramType = "query", dataType = "Integer", defaultValue = "0",
                    required = true),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true),
    })
    @PreAuthorize("hasAnyAuthority('user:basic:list:get')")
    @RequestMapping(value = "/basic/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserBasicInfo>> searchBasicInfo(@RequestParam(required = false) String name,
                                                                   @RequestParam(required = false) String phone,
                                                                   @RequestParam(defaultValue = "0") Integer sex,
                                                                   @RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        if (sex > 2 || sex < 0) {
            return CommonResult.validateFailed("性别参数错误：" + sex);
        }

        return CommonResult.success(CommonPage.restPage(basicInfoService.list(name, phone, sex, pageNum, pageSize)));
    }
}
