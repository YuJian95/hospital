package cn.yujian95.little.admin.modules.user.controller;

import cn.yujian95.little.admin.modules.user.param.UserRegisterParam;
import cn.yujian95.little.admin.modules.user.service.IUserInfoService;
import cn.yujian95.little.common.api.CommonPage;
import cn.yujian95.little.common.api.CommonResult;
import cn.yujian95.little.mbg.modules.user.entity.UserInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
@AllArgsConstructor
@RequestMapping("/user")
public class UserInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);

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

    @ApiOperation("分页获取用户信息 ")
    @GetMapping("/info/list")
    public CommonResult<CommonPage<UserInfo>> listUserInfo(@RequestParam(defaultValue = "1") Integer pageNum,
                                                           @RequestParam(defaultValue = "15") Integer pageSize) {

        Page<UserInfo> page = new Page<>(pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(userInfoService.page(page)));
    }

    @ApiOperation("通过用户名/昵称、手机号，获取用户信息 ")
    @GetMapping("/info/search")
    public CommonResult<CommonPage<UserInfo>> searchUserInfo(@RequestParam(required = false) String nameKeyword,
                                                             @RequestParam(required = false) String phone,
                                                             @RequestParam(defaultValue = "1") Integer pageNum,
                                                             @RequestParam(defaultValue = "15") Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(userInfoService.search(nameKeyword, phone, pageNum, pageSize)));
    }

    @ApiOperation("注册用户账号")
    @PostMapping("/info/register")
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
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("修改用户状态 ")
    @PutMapping("/info/status/{id}")
    public CommonResult<UserInfo> updateUserInfoStatus(@PathVariable Long id, @RequestParam Integer status) {

        if (userInfoService.isNotExist(id)) {
            LOGGER.debug("update UserInfo fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        if (userInfoService.updateStatus(id, status)) {
            LOGGER.debug("update UserInfo success, id:{} and status:{}", id, status);
            return CommonResult.success();
        }

        LOGGER.debug("update UserInfo failed, id:{} and status:{}", id, status);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("删除指定 id，用户信息 ")
    @DeleteMapping("/info/{id}")
    public CommonResult deleteUserInfo(@PathVariable Long id) {

        if (userInfoService.isNotExist(id)) {
            LOGGER.debug("delete UserInfo fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        if (userInfoService.removeById(id)) {
            LOGGER.debug("delete UserInfo success:{}", id);
            return CommonResult.success();
        }

        LOGGER.debug("delete UserInfo failed:{}", id);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }
}

