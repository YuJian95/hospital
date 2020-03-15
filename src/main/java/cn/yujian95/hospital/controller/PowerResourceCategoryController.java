package cn.yujian95.hospital.controller;

import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.dto.param.PowerResourceCategoryParam;
import cn.yujian95.hospital.entity.PowerResourceCategory;
import cn.yujian95.hospital.service.IPowerResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/13
 */
@Api(value = "权限模块", tags = "权限资源分类接口")
@RestController
@CrossOrigin
@RequestMapping("/power")
public class PowerResourceCategoryController {

    @Resource
    private IPowerResourceCategoryService resourceCategoryService;

    @ApiOperation("查询所有权限资源分类")
    @RequestMapping(value = "/category/list", method = RequestMethod.GET)
    public CommonResult<List<PowerResourceCategory>> listAllCategory() {

        List<PowerResourceCategory> resourceList = resourceCategoryService.listAll();

        return CommonResult.success(resourceList);
    }

    @ApiOperation("添加权限资源分类")
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public CommonResult insertCategory(@RequestBody PowerResourceCategoryParam param) {

        if (resourceCategoryService.insert(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("修改权限资源分类")
    @RequestMapping(value = "/category/{id}", method = RequestMethod.PUT)
    public CommonResult updateCategory(@PathVariable Long id, @RequestBody PowerResourceCategoryParam param) {

        if (!resourceCategoryService.count(id)) {
            return CommonResult.validateFailed("不存在，该分类编号！");
        }

        if (resourceCategoryService.update(id, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("删除权限资源")
    @RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteCategory(@PathVariable Long id) {

        if (!resourceCategoryService.count(id)) {
            return CommonResult.validateFailed("不存在，该分类编号！");
        }

        if (resourceCategoryService.delete(id)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }
}
