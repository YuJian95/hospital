package cn.yujian95.little.mobile.modules.user.controller;

import cn.hutool.core.util.StrUtil;
import cn.yujian95.little.common.api.CommonResult;
import cn.yujian95.little.mbg.modules.user.entity.UserInfo;
import cn.yujian95.little.mobile.modules.user.param.UpdateUserPasswordParam;
import cn.yujian95.little.mobile.modules.user.param.UserRegisterParam;
import cn.yujian95.little.mobile.modules.user.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

/**
 * <p>
 * 用户信息  前端接口控制器
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-20
 */
@Api(value = "UserInfoController", tags = "用户信息 ")
@RestController
@RequestMapping("/user")
public class UserInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Resource
    private IUserInfoService userInfoService;

    @ApiOperation("获取指定 id，用户信息 ")
    @GetMapping("/info/{id}")
    public CommonResult<UserInfo> getUserInfo(@PathVariable Long id) {

        if (userInfoService.isNotExist(id)) {
            LOGGER.debug("get UserInfo fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        return CommonResult.success(userInfoService.getById(id));
    }

    @ApiOperation("添加用户信息 ")
    @PostMapping("/info")
    public CommonResult<UserInfo> createUserInfo(@RequestBody UserInfo userInfo) {

        if (userInfoService.save(userInfo)) {
            LOGGER.debug("create UserInfo success:{}", userInfo);
            return CommonResult.success(userInfo);
        }

        LOGGER.debug("create UserInfo failed:{}", userInfo);
        return CommonResult.failed("服务器错误，请联系用户！");
    }

    @ApiOperation("编辑指定 id，用户信息 ")
    @PutMapping("/info")
    public CommonResult<UserInfo> updateUserInfo(@RequestBody UserInfo userInfo) {

        if (userInfoService.isNotExist(userInfo.getId())) {
            LOGGER.debug("update UserInfo fail，id not exist :{}", userInfo);
            return CommonResult.validateFailed("不存在，该记录编号：" + userInfo.getId());
        }

        if (userInfoService.updateById(userInfo)) {
            LOGGER.debug("update UserInfo success:{}", userInfo);
            return CommonResult.success(userInfo);
        }

        LOGGER.debug("update UserInfo failed:{}", userInfo);
        return CommonResult.failed("服务器错误，请联系用户！");
    }

    @ApiOperation("注册用户账号")
    @PostMapping("/register")
    public CommonResult registerUserInfo(@RequestBody UserRegisterParam param) {

        if (userInfoService.isExistUser(param.getUsername())) {
            return CommonResult.validateFailed("该账号名称，已被使用！");
        }

        if (userInfoService.registerUser(param)) {
            LOGGER.debug("register UserInfo success:{}", param);
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation("刷新用户账号 Token")
    @GetMapping("/token/refresh")
    public CommonResult refreshUserInfoToken(HttpServletRequest request) {

        String refreshToken = userInfoService.refreshToken(request.getHeader(tokenHeader));

        if (StrUtil.isEmpty(refreshToken)) {
            return CommonResult.validateFailed("token已经过期！");
        }

        return CommonResult.success(tokenHead + " " + refreshToken);
    }

    @ApiOperation("登录用户账号，获取 Token")
    @GetMapping("/login")
    public CommonResult loginUserInfoToken(@RequestParam String username, @RequestParam String password) {

        if (!userInfoService.isExistUser(username)) {
            return CommonResult.validateFailed("不存在，该用户账号：" + username);
        }

        return CommonResult.success(tokenHead + " " + userInfoService.loginUser(username, password));
    }

    @ApiOperation("获取当前，用户账号")
    @GetMapping("/info")
    public CommonResult getCurrentUserInfo(Principal principal) {

        if (principal == null) {
            return CommonResult.unauthorized(null);
        }

        String username = principal.getName();
        Optional<UserInfo> optional = userInfoService.getUserByUsername(username);

        if (optional.isPresent()) {
            UserInfo user = optional.get();
            user.setPassword(null);

            return CommonResult.success(user);
        }

        return CommonResult.failed();
    }

    @ApiOperation("修改指定用户密码")
    @PutMapping("/info/password")
    public CommonResult updatePowerAdminPassword(@Validated @RequestBody UpdateUserPasswordParam param) {

        if (userInfoService.updatePassword(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation("用户登出功能")
    @PostMapping("/logout")
    public CommonResult logoutUserInfo() {
        return CommonResult.success();
    }
}

