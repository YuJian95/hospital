package cn.yujian95.base.controller;

import cn.yujian95.base.common.api.CommonResult;
import cn.yujian95.base.dto.param.PowerAccountRegisterParam;
import cn.yujian95.base.dto.param.PowerAccountStatusParam;
import cn.yujian95.base.entity.PowerAccount;
import cn.yujian95.base.entity.PowerPermission;
import cn.yujian95.base.service.IPowerAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * 权限账号接口
 *
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/19
 */

@Api(value = "权限模块", tags = "权限账号接口")
@RestController
@CrossOrigin
@RequestMapping("/power/account")
public class PowerAccountController {

    @Resource
    private IPowerAccountService accountService;

    @ApiOperation(value = "验证账号名称", notes = "传入 账号名称, 返回是否存在")
    @ApiImplicitParam(name = "name", value = "账号名称", paramType = "query", dataType = "String",
            required = true)
    @RequestMapping(value = "/name/validation", method = RequestMethod.GET)
    public CommonResult<Boolean> verifyAccountName(@RequestParam String name) {
        return CommonResult.success(accountService.count(name));
    }

    @ApiOperation(value = "账号注册", notes = "传入 登录对象参数（账号名称、密码）")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult registerAccount(@RequestBody PowerAccountRegisterParam param) {
        if (accountService.count(param.getName())) {
            return CommonResult.validateFailed("该账号名称已存在！");
        }

        if (accountService.register(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "账号登录", notes = "传入 账号名称、账号密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "账号名称", paramType = "query", dataType = "String",
                    required = true),
            @ApiImplicitParam(name = "password", value = "账号密码（md5加密）", paramType = "query", dataType = "String",
                    required = true)
    })
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public CommonResult<String> loginAccount(@RequestParam String name, @RequestParam String password) {
        if (!accountService.count(name)) {
            return CommonResult.validateFailed("不存在，该账号名称！");
        }

        Optional<String> stringOptional = accountService.login(name, password);

        if (stringOptional.isPresent()) {
            return CommonResult.success(stringOptional.get());
        }

        return CommonResult.validateFailed("用户名或密码错误");
    }

    @ApiOperation(value = "退出登录账号", notes = "无需参数，需要前端清除 header中的 jwt字符串")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public CommonResult logoutAccount() {
        return CommonResult.success();
    }

    @ApiOperation(value = "获取当前账号信息", notes = "无需参数，通过 jwt校验")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('power:account:info:get')")
    public CommonResult<PowerAccount> getCurrentAccount(Principal principal) {
        if (principal == null) {
            return CommonResult.validateFailed();
        }

        String name = principal.getName();

        Optional<PowerAccount> accountOptional = accountService.getByName(name);

        if (accountOptional.isPresent()) {

            PowerAccount account = accountOptional.get();
            account.setPassword(null);

            return CommonResult.success(account);
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "刷新 token", notes = "传入 token，返回刷新后的 token")
    @ApiImplicitParam(name = "token", value = "登录后返回的 token", paramType = "query", dataType = "String",
            required = true)
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public CommonResult<String> refreshToken(@RequestParam String token) {

        if (StringUtils.isEmpty(token)) {
            return CommonResult.validateFailed("token不能为空！");
        }

        // TODO 这里需要细化一下返回情况，可优化，jwt工具类
        return CommonResult.success(accountService.refreshToken(token));
    }

    @ApiOperation(value = "更新账号状态", notes = "传入 账号编号、账号状态（1：开启，0：关闭）")
    @RequestMapping(value = "/status", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('power:account:status:put')")
    public CommonResult updateAccountStatus(@RequestBody PowerAccountStatusParam param) {

        if (param.getStatus() > 1 || param.getStatus() < 0) {
            return CommonResult.validateFailed("不存在，该账号状态！");
        }

        if (!accountService.count(param.getAccountId())) {
            return CommonResult.validateFailed("不存在，该账号编号！");
        }

        if (accountService.updateStatus(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "更新账号分配角色", notes = "传入 账号编号，角色编号列表")
    @ApiImplicitParam(name = "accountId", value = "账号编号", paramType = "query", dataType = "Long",
            required = true)
    @RequestMapping(value = "/role", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('power:account:role:post')")
    public CommonResult<Integer> updateAccountRoleRelation(@RequestParam Long accountId,
                                                           @RequestBody List<Long> roleIdList) {
        if (!accountService.count(accountId)) {
            return CommonResult.validateFailed("不存在，该账号编号！");
        }

        int count = accountService.updateRole(accountId, roleIdList);

        if (count >= 0) {
            return CommonResult.success(count);
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "获取账号拥有所有权限（包括特殊权限）", notes = "传入 账号编号")
    @ApiImplicitParam(name = "accountId", value = "账号编号", paramType = "query", dataType = "Long",
            required = true)
    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('power:account:permission:get')")
    public CommonResult<List<PowerPermission>> listAccountPermission(@RequestParam Long accountId) {

        if (!accountService.count(accountId)) {
            return CommonResult.validateFailed("不存在，该账号编号！");
        }

        return CommonResult.success(accountService.listPermission(accountId));
    }
}
