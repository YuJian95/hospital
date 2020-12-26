package cn.yujian95.little.admin.modules.power.controller;

import cn.yujian95.little.admin.modules.power.service.IPowerResourceCategoryService;
import cn.yujian95.little.common.api.CommonPage;
import cn.yujian95.little.common.api.CommonResult;
import cn.yujian95.little.mbg.modules.power.entity.PowerResourceCategory;
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
 * 权限资源分类  前端接口控制器
 * </p>
 *
 * @author yujian95 yujian95/cn@163.com
 * @since 2020-10-14
 */
@Api(value = "PowerResourceCategoryController", tags = "权限资源分类 ")
@RestController
@AllArgsConstructor
@RequestMapping("/power")
public class PowerResourceCategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PowerResourceCategoryController.class);

    private IPowerResourceCategoryService powerResourceCategoryService;

    @ApiOperation("获取指定 id，权限资源分类 ")
    @GetMapping("/resource/category/{id}")
    public CommonResult<PowerResourceCategory> getPowerResourceCategory(@PathVariable Long id) {

        if (powerResourceCategoryService.isNotExist(id)) {
            LOGGER.debug("get PowerResourceCategory fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        return CommonResult.success(powerResourceCategoryService.getById(id));
    }

    @ApiOperation("获取所有权限资源分类 ")
    @GetMapping("/resource/category/list/all")
    public CommonResult<List<PowerResourceCategory>> listAllPowerResourceCategory() {
        return CommonResult.success(powerResourceCategoryService.listAll());
    }

    @ApiOperation("分页获取权限资源分类 ")
    @GetMapping("/resource/category/list")
    public CommonResult<CommonPage<PowerResourceCategory>> listPowerResourceCategory(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                                     @RequestParam(defaultValue = "15") Integer pageSize) {

        Page<PowerResourceCategory> page = new Page<>(pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(powerResourceCategoryService.page(page)));
    }

    @ApiOperation("添加权限资源分类 ")
    @PostMapping("/resource/category")
    public CommonResult<PowerResourceCategory> createPowerResourceCategory(@RequestBody PowerResourceCategory powerResourceCategory) {

        if (powerResourceCategoryService.save(powerResourceCategory)) {
            LOGGER.debug("create PowerResourceCategory success:{}", powerResourceCategory);
            return CommonResult.success(powerResourceCategory);
        }

        LOGGER.debug("create PowerResourceCategory failed:{}", powerResourceCategory);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("编辑指定 id，权限资源分类 ")
    @PutMapping("/resource/category")
    public CommonResult<PowerResourceCategory> updatePowerResourceCategory(@RequestBody PowerResourceCategory powerResourceCategory) {

        if (powerResourceCategoryService.isNotExist(powerResourceCategory.getId())) {
            LOGGER.debug("update PowerResourceCategory fail，id not exist :{}", powerResourceCategory);
            return CommonResult.validateFailed("不存在，该记录编号：" + powerResourceCategory.getId());
        }

        if (powerResourceCategoryService.updateById(powerResourceCategory)) {
            LOGGER.debug("update PowerResourceCategory success:{}", powerResourceCategory);
            return CommonResult.success(powerResourceCategory);
        }

        LOGGER.debug("update PowerResourceCategory failed:{}", powerResourceCategory);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("删除指定 id，权限资源分类 ")
    @DeleteMapping("/resource/category/{id}")
    public CommonResult deletePowerResourceCategory(@PathVariable Long id) {

        if (powerResourceCategoryService.isNotExist(id)) {
            LOGGER.debug("delete PowerResourceCategory fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        if (powerResourceCategoryService.removeById(id)) {
            LOGGER.debug("delete PowerResourceCategory success:{}", id);
            return CommonResult.success();
        }

        LOGGER.debug("delete PowerResourceCategory failed:{}", id);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }
}

