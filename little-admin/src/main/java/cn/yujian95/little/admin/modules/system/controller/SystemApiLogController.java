package cn.yujian95.little.admin.modules.system.controller;

import cn.yujian95.little.admin.modules.system.service.ISystemApiLogService;
import cn.yujian95.little.common.api.CommonPage;
import cn.yujian95.little.common.api.CommonResult;
import cn.yujian95.little.mbg.modules.system.entity.SystemApiLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统接口日志  前端接口控制器
 * </p>
 *
 * @author yujian95 yujian95/cn@163.com
 * @since 2020-10-14
 */
@Api(value = "SystemApiLogController", tags = "系统接口日志 ")
@RestController
@AllArgsConstructor
@RequestMapping("/system")
public class SystemApiLogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemApiLogController.class);

    private ISystemApiLogService systemApiLogService;

    @ApiOperation("获取指定 id，系统接口日志 ")
    @GetMapping("/api/log/{id}")
    public CommonResult<SystemApiLog> getSystemApiLog(@PathVariable Long id) {

        if (systemApiLogService.isNotExist(id)) {
            LOGGER.debug("get SystemApiLog fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        return CommonResult.success(systemApiLogService.getById(id));
    }

    @ApiOperation("分页搜索，系统接口日志 ")
    @GetMapping("/api/log/search")
    public CommonResult<CommonPage<SystemApiLog>> searchSystemApiLog(@RequestParam(required = false) String keyword,
                                                                     @RequestParam(required = false) String uri,
                                                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                                                     @RequestParam(defaultValue = "15") Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(systemApiLogService.search(keyword, uri, pageNum, pageSize)));
    }
}

