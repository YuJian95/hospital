package cn.yujian95.base.controller;

import cn.yujian95.base.common.api.CommonPage;
import cn.yujian95.base.common.api.CommonResult;
import cn.yujian95.base.dto.PowerPermissionNodeDTO;
import cn.yujian95.base.dto.param.PowerPermissionParam;
import cn.yujian95.base.entity.PowerPermission;
import cn.yujian95.base.service.IPowerPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限权值接口
 *
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/24
 */

@Api(value = "权限模块", tags = "权限权值接口")
@RestController
@CrossOrigin
@RequestMapping("/power/permission")
public class PowerPermissionController {

    @Resource
    private IPowerPermissionService permissionService;

    @ApiOperation(value = "增加权限权值", notes = "传入 权限权值参数")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('power:permission:post')")
    public CommonResult insertPermission(@RequestBody PowerPermissionParam param) {

        if (permissionService.insert(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "修改权限权值", notes = "传入 权限权值参数")
    @ApiImplicitParam(name = "id", value = "权限编号", paramType = "path", dataType = "Long",
            required = true)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('power:permission:put')")
    public CommonResult updatePermission(@PathVariable Long id, @RequestBody PowerPermissionParam param) {
        if (!permissionService.count(id)) {
            return CommonResult.validateFailed("不存在，该权限编号！");
        }

        if (permissionService.update(id, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "分页：搜索权限权值", notes = "传入 权限名称，权限类型（-1：全部，0：目录；1：菜单；2：按钮（接口））")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "权限名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "权限类型", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true),
    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('power:permission:list:get')")
    public CommonResult<CommonPage<PowerPermission>> searchPermission(@RequestParam(required = false) String name,
                                                                      @RequestParam Integer type,
                                                                      @RequestParam Integer pageNum,
                                                                      @RequestParam Integer pageSize) {
        if (type > 2 || type < -1) {
            return CommonResult.validateFailed("权限类型错误！");
        }

        return CommonResult.success(CommonPage.restPage(permissionService.search(name, type, pageNum, pageSize)));
    }

    @ApiOperation(value = "获取所有权限权值", notes = "无需参数，以层级结构返回所有权限")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('power:permission:tree:get')")
    public CommonResult<List<PowerPermissionNodeDTO>> treePermission() {
        return CommonResult.success(permissionService.treeList());
    }

    @ApiOperation(value = "获取相应父级权限", notes = "传入 权限类型（0：目录；1：菜单；2：按钮（接口））")
    @ApiImplicitParam(name = "type", value = "权限类型", paramType = "query", dataType = "Integer",
            required = true)
    @RequestMapping(value = "/list/type", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('power:permission:list:type:get')")
    public CommonResult<List<PowerPermission>> listParent(@RequestParam Integer type) {

        if (type > 2 || type < -1) {
            return CommonResult.validateFailed("权限类型错误！");
        }

        return CommonResult.success(permissionService.listParent(type));
    }
}
