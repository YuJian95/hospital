package cn.yujian95.little.admin.modules.power.controller;

import cn.yujian95.little.admin.modules.power.service.IPowerResourceService;
import cn.yujian95.little.common.api.CommonPage;
import cn.yujian95.little.common.api.CommonResult;
import cn.yujian95.little.mbg.modules.power.entity.PowerResource;
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
 * 权限资源  前端接口控制器
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
@Api(value = "PowerResourceController", tags = "权限资源 ")
@RestController
@AllArgsConstructor
@RequestMapping("/power")
public class PowerResourceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PowerResourceController.class);

    private IPowerResourceService powerResourceService;

    @ApiOperation("获取指定 id，权限资源 ")
    @GetMapping("/resource/{id}")
    public CommonResult<PowerResource> getPowerResource(@PathVariable Long id) {

        if (powerResourceService.isNotExist(id)) {
            LOGGER.debug("get PowerResource fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        return CommonResult.success(powerResourceService.getById(id));
    }

    @ApiOperation("获取所有权限资源 ")
    @GetMapping("/resource/list/all")
    public CommonResult<List<PowerResource>> listAllPowerResource() {
        return CommonResult.success(powerResourceService.list());
    }

    @ApiOperation("分页获取权限资源 ")
    @GetMapping("/resource/list")
    public CommonResult<CommonPage<PowerResource>> listPowerResource(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                     @RequestParam(defaultValue = "15") Integer pageSize) {

        Page<PowerResource> page = new Page<>(pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(powerResourceService.page(page)));
    }

    @ApiOperation("添加权限资源 ")
    @PostMapping("/resource")
    public CommonResult<PowerResource> createPowerResource(@RequestBody PowerResource powerResource) {

        if (powerResourceService.save(powerResource)) {
            LOGGER.debug("create PowerResource success:{}", powerResource);
            return CommonResult.success(powerResource);
        }

        LOGGER.debug("create PowerResource failed:{}", powerResource);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("编辑指定 id，权限资源 ")
    @PutMapping("/resource")
    public CommonResult<PowerResource> updatePowerResource(@RequestBody PowerResource powerResource) {

        if (powerResourceService.isNotExist(powerResource.getId())) {
            LOGGER.debug("update PowerResource fail，id not exist :{}", powerResource);
            return CommonResult.validateFailed("不存在，该记录编号：" + powerResource.getId());
        }

        if (powerResourceService.updateById(powerResource)) {
            LOGGER.debug("update PowerResource success:{}", powerResource);
            return CommonResult.success(powerResource);
        }

        LOGGER.debug("update PowerResource failed:{}", powerResource);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("删除指定 id，权限资源 ")
    @DeleteMapping("/resource/{id}")
    public CommonResult deletePowerResource(@PathVariable Long id) {

        if (powerResourceService.isNotExist(id)) {
            LOGGER.debug("delete PowerResource fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        if (powerResourceService.removeById(id)) {
            LOGGER.debug("delete PowerResource success:{}", id);
            return CommonResult.success();
        }

        LOGGER.debug("delete PowerResource failed:{}", id);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("分页模糊查询后台资源")
    @GetMapping("/resource/search")
    public CommonResult<CommonPage<PowerResource>> searchPowerResource(@RequestParam(required = false) Long categoryId,
                                                                       @RequestParam(required = false) String nameKeyword,
                                                                       @RequestParam(required = false) String urlKeyword,
                                                                       @RequestParam(defaultValue = "1") Integer pageNum,
                                                                       @RequestParam(defaultValue = "15") Integer pageSize) {
        Page<PowerResource> resourceList = powerResourceService.list(categoryId, nameKeyword, urlKeyword, pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(resourceList));
    }
}

