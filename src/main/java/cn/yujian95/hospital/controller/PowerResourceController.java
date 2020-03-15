package cn.yujian95.hospital.controller;

import cn.yujian95.hospital.common.api.CommonPage;
import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.common.security.DynamicSecurityMetadataSource;
import cn.yujian95.hospital.dto.param.PowerResourceParam;
import cn.yujian95.hospital.entity.PowerResource;
import cn.yujian95.hospital.service.IPowerResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/13
 */
@Api(value = "权限模块", tags = "权限资源接口")
@RestController
@CrossOrigin
@RequestMapping("/power")
public class PowerResourceController {

    @Resource
    private IPowerResourceService resourceService;

    @Resource
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @ApiOperation("添加权限资源")
    @RequestMapping(value = "/resource", method = RequestMethod.POST)
    public CommonResult insertResource(@RequestBody PowerResourceParam param) {

        if (resourceService.insert(param)) {
            dynamicSecurityMetadataSource.clearDataSource();
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("修改权限资源")
    @RequestMapping(value = "/resource/{id}", method = RequestMethod.PUT)
    public CommonResult updateResource(@PathVariable Long id, @RequestBody PowerResourceParam param) {

        if (!resourceService.count(id)) {
            return CommonResult.validateFailed("不存在，该资源编号！");
        }

        if (resourceService.update(id, param)) {
            dynamicSecurityMetadataSource.clearDataSource();
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("获取资源详情")
    @RequestMapping(value = "/resource/{id}", method = RequestMethod.GET)
    public CommonResult<PowerResource> getResource(@PathVariable Long id) {

        if (!resourceService.count(id)) {
            return CommonResult.validateFailed("不存在，该资源编号！");
        }

        Optional<PowerResource> optionalPowerResource = resourceService.get(id);

        return optionalPowerResource.map(CommonResult::success)
                .orElseGet(() -> CommonResult.failed("服务器错误，请联系管理员！"));

    }

    @ApiOperation("删除权限资源")
    @RequestMapping(value = "/resource/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteResource(@PathVariable Long id) {

        if (!resourceService.count(id)) {
            return CommonResult.validateFailed("不存在，该资源编号！");
        }

        if (resourceService.delete(id)) {
            dynamicSecurityMetadataSource.clearDataSource();
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("分页：模糊查询权限资源")
    @RequestMapping(value = "/resource/search", method = RequestMethod.GET)
    public CommonResult<CommonPage<PowerResource>> listResource(@RequestParam(required = false) Long categoryId,
                                                                @RequestParam(required = false) String nameKeyword,
                                                                @RequestParam(required = false) String urlKeyword,
                                                                @RequestParam(defaultValue = "5") Integer pageSize,
                                                                @RequestParam(defaultValue = "1") Integer pageNum) {
        List<PowerResource> resourceList = resourceService.list(categoryId, nameKeyword, urlKeyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(resourceList));
    }

    @ApiOperation("查询所有权限资源")
    @RequestMapping(value = "/resource/list", method = RequestMethod.GET)
    public CommonResult<List<PowerResource>> listAllResource() {
        List<PowerResource> resourceList = resourceService.listAll();
        return CommonResult.success(resourceList);
    }

}
