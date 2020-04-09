package cn.yujian95.hospital.controller;

import cn.yujian95.hospital.common.api.CommonPage;
import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.component.WxComponent;
import cn.yujian95.hospital.dto.UserInfoDTO;
import cn.yujian95.hospital.dto.param.PowerAccountPasswordParam;
import cn.yujian95.hospital.dto.param.UserBasicInfoParam;
import cn.yujian95.hospital.dto.param.UserRegisterParam;
import cn.yujian95.hospital.entity.PowerAccount;
import cn.yujian95.hospital.entity.UserBasicInfo;
import cn.yujian95.hospital.service.IPowerAccountService;
import cn.yujian95.hospital.service.IUserBasicInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Optional;

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

    @Resource
    private WxComponent wxComponent;

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

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "发送验证短信", notes = "传入 手机号")
    @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", dataType = "String",
            required = true)
    @RequestMapping(value = "/basic/password/message", method = RequestMethod.GET)
    public CommonResult sendUpdateMessage(@RequestParam String phone) {

        if (StringUtils.isEmpty(phone)) {
            return CommonResult.validateFailed("手机号码不能未空！");
        }

        if (!powerAccountService.count(phone)) {
            return CommonResult.validateFailed("该手机号，还未注册！");
        }

        if (basicInfoService.sendMessage(phone)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
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

    @ApiOperation(value = "授权获取 openid", notes = "传入 session_code")
    @ApiImplicitParam(name = "code", value = "微信生成的session-code", paramType = "query", dataType = "String",
            required = true)
    @RequestMapping(value = "/wx", method = RequestMethod.GET)
    public CommonResult getWxOpenId(@RequestParam String code) {

        if (StringUtils.isEmpty(code)) {
            return CommonResult.validateFailed("code为空！");
        }

        return CommonResult.success(wxComponent.getOpenId(code));
    }

    @ApiOperation(value = "用户账号注册", notes = "传入 注册对象参数（姓名、头像、手机号、密码）")
    @RequestMapping(value = "/basic/account/register", method = RequestMethod.POST)
    public CommonResult registerUserAccount(@RequestBody UserRegisterParam param) {
        if (powerAccountService.count(param.getPhone())) {
            return CommonResult.validateFailed("该账号名称已存在！");
        }

        if (powerAccountService.registerUser(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "获取当前用户信息", notes = "无需参数，通过 jwt校验")
    @RequestMapping(value = "/basic/info", method = RequestMethod.GET)
    public CommonResult getCurrentUserInfo(Principal principal) {

        if (principal == null) {
            return CommonResult.validateFailed("principal 对象为空！");
        }

        String userName = principal.getName();

        Optional<PowerAccount> optional = powerAccountService.getByName(userName);

        if (optional.isPresent()) {

            PowerAccount account = optional.get();
            account.setPassword(null);

            UserInfoDTO dto = new UserInfoDTO();

            // 设置账号信息
            dto.setAccount(account);

            Optional<UserBasicInfo> infoOptional = basicInfoService.getOptionalByPhone(account.getName());

            // 设置用户基础信息
            infoOptional.ifPresent(dto::setBasicInfo);

            return CommonResult.success(dto);
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

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "更新用户账号密码", notes = "传入 账号修改密码对象参数（手机号、密码、验证码）")
    @RequestMapping(value = "/basic/password", method = RequestMethod.PUT)
    public CommonResult updateBasicInfoPassword(@RequestBody PowerAccountPasswordParam param) {

        if (StringUtils.isEmpty(param.getPassword())) {
            return CommonResult.validateFailed("账号密码不能为空！");
        }

        if (StringUtils.isEmpty(param.getName())) {
            return CommonResult.validateFailed("账号名称不能为空！");
        }

        if (!basicInfoService.verifyCode(param.getName(), param.getCode())) {
            return CommonResult.validateFailed("短信验证码错误！");
        }

        Optional<PowerAccount> accountOptional = powerAccountService.getByName(param.getName());

        if (!accountOptional.isPresent()) {
            return CommonResult.validateFailed("不存在，该用户账号！");
        }

        if (powerAccountService.updatePassword(accountOptional.get().getId(), param.getPassword())) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "删除用户基础信息", notes = "传入 用户编号")
    @ApiImplicitParam(name = "id", value = "用户编号", paramType = "path", dataType = "Long",
            required = true)
    @RequestMapping(value = "/basic/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteBasicInfo(@PathVariable Long id) {
        if (!basicInfoService.count(id)) {
            return CommonResult.validateFailed("不存在，该用户编号！");
        }

        if (basicInfoService.delete(id)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "分页：搜索用户信息", notes = "传入 用户姓名、手机号、性别、第几页、页大小")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户姓名", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true),
    })
    @RequestMapping(value = "/basic/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserBasicInfo>> searchBasicInfo(@RequestParam(required = false) String name,
                                                                   @RequestParam(required = false) String phone,
                                                                   @RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(basicInfoService.list(name, phone, pageNum, pageSize)));
    }
}
