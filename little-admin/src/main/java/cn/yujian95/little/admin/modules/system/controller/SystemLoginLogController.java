package cn.yujian95.little.admin.modules.system.controller;

import cn.yujian95.little.admin.modules.system.service.ISystemLoginLogService;
import cn.yujian95.little.common.api.CommonPage;
import cn.yujian95.little.common.api.CommonResult;
import cn.yujian95.little.mbg.modules.system.entity.SystemLoginLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统用户登录日志 前端接口控制器
 * </p>
 *
 * @author yujian95 yujian95/cn@163.com
 * @since 2020-10-14
 */
@Api(value = "SystemLoginLogController", tags = "系统用户登录日志")
@RestController
@AllArgsConstructor
@RequestMapping("/system")
public class SystemLoginLogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemLoginLogController.class);

    private ISystemLoginLogService systemLoginLogService;

    @ApiOperation("获取指定 id，系统用户登录日志")
    @GetMapping("/login/log/{id}")
    public CommonResult<SystemLoginLog> getSystemLoginLog(@PathVariable Long id) {

        if (systemLoginLogService.isNotExist(id)) {
            LOGGER.debug("get SystemLoginLog fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        return CommonResult.success(systemLoginLogService.getById(id));
    }

    @ApiOperation("获取所有系统用户登录日志")
    @GetMapping("/login/log/list/all")
    public CommonResult<List<SystemLoginLog>> listAllSystemLoginLog() {
        return CommonResult.success(systemLoginLogService.list());
    }

    @ApiOperation("分页获取系统用户登录日志")
    @GetMapping("/login/log/list")
    public CommonResult<CommonPage<SystemLoginLog>> listSystemLoginLog(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                       @RequestParam(defaultValue = "15") Integer pageSize) {

        Page<SystemLoginLog> page = new Page<>(pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(systemLoginLogService.page(page)));
    }

    @ApiOperation("添加系统用户登录日志")
    @PostMapping("/login/log")
    public CommonResult<SystemLoginLog> createSystemLoginLog(@RequestBody SystemLoginLog systemLoginLog) {

        if (systemLoginLogService.save(systemLoginLog)) {
            LOGGER.debug("create SystemLoginLog success:{}", systemLoginLog);
            return CommonResult.success(systemLoginLog);
        }

        LOGGER.debug("create SystemLoginLog failed:{}", systemLoginLog);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("编辑指定 id，系统用户登录日志")
    @PutMapping("/login/log")
    public CommonResult<SystemLoginLog> updateSystemLoginLog(@RequestBody SystemLoginLog systemLoginLog) {

        if (systemLoginLogService.isNotExist(systemLoginLog.getId())) {
            LOGGER.debug("update SystemLoginLog fail，id not exist :{}", systemLoginLog);
            return CommonResult.validateFailed("不存在，该记录编号：" + systemLoginLog.getId());
        }

        if (systemLoginLogService.updateById(systemLoginLog)) {
            LOGGER.debug("update SystemLoginLog success:{}", systemLoginLog);
            return CommonResult.success(systemLoginLog);
        }

        LOGGER.debug("update SystemLoginLog failed:{}", systemLoginLog);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }
}

