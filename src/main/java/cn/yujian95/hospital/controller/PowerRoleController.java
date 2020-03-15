package cn.yujian95.hospital.controller;

import cn.yujian95.hospital.common.api.CommonPage;
import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.dto.param.PowerRoleParam;
import cn.yujian95.hospital.dto.param.StatusParam;
import cn.yujian95.hospital.entity.PowerMenu;
import cn.yujian95.hospital.entity.PowerResource;
import cn.yujian95.hospital.entity.PowerRole;
import cn.yujian95.hospital.service.IPowerRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限角色接口
 *
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/24
 */

@Api(value = "权限模块", tags = "权限角色接口")
@RestController
@CrossOrigin
@RequestMapping("/power/role")
public class PowerRoleController {

    @Resource
    private IPowerRoleService roleService;

    @ApiOperation(value = "分页：搜索权限角色", notes = "传入 第几页、页大小")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "chineseName", value = "中文名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true),
    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<PowerRole>> searchRole(@RequestParam(required = false) String chineseName,
                                                          @RequestParam Integer pageNum,
                                                          @RequestParam Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(roleService.list(chineseName, pageNum, pageSize)));
    }

    @ApiOperation(value = "增加权限角色", notes = "传入 权限角色参数")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public CommonResult insertRole(@RequestBody PowerRoleParam param) {

        if (roleService.insert(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "修改权限角色", notes = "传入 权限角色参数")
    @ApiImplicitParam(name = "id", value = "角色编号", paramType = "path", dataType = "Long",
            required = true)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public CommonResult updateRole(@PathVariable Long id, @RequestBody PowerRoleParam param) {

        if (!roleService.count(id)) {
            return CommonResult.validateFailed("不存在，该角色编号！");
        }

        if (roleService.update(id, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "删除权限角色", notes = "传入 权限角色编号")
    @ApiImplicitParam(name = "id", value = "角色编号", paramType = "path", dataType = "Long",
            required = true)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteRole(@PathVariable Long id) {

        if (!roleService.count(id)) {
            return CommonResult.validateFailed("不存在，该角色编号！");
        }

        if (roleService.delete(id)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }


    @ApiOperation(value = "更新角色状态", notes = "传入 权限角色编号、状态参数")
    @ApiImplicitParam(name = "id", value = "角色编号", paramType = "path", dataType = "Long",
            required = true)
    @RequestMapping(value = "/status/{id}", method = RequestMethod.PUT)
    public CommonResult updateRoleStatus(@PathVariable Long id, @RequestBody StatusParam param) {

        if (!roleService.count(id)) {
            return CommonResult.validateFailed("不存在，该角色编号！");
        }

        if (roleService.updateStatus(id, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }


    @ApiOperation("获取角色相关菜单")
    @RequestMapping(value = "/menu/{roleId}", method = RequestMethod.GET)
    public CommonResult<List<PowerMenu>> listRoleMenu(@PathVariable Long roleId) {

        if (!roleService.count(roleId)) {
            return CommonResult.validateFailed("不存在，该角色编号！");
        }


        List<PowerMenu> roleList = roleService.listMenu(roleId);

        return CommonResult.success(roleList);
    }

    @ApiOperation("获取角色相关资源")
    @RequestMapping(value = "/resource/{roleId}", method = RequestMethod.GET)
    public CommonResult<List<PowerResource>> listRoleResource(@PathVariable Long roleId) {

        if (!roleService.count(roleId)) {
            return CommonResult.validateFailed("不存在，该角色编号！");
        }

        List<PowerResource> roleList = roleService.listMenuResource(roleId);
        return CommonResult.success(roleList);
    }

    @ApiOperation("给角色分配菜单")
    @RequestMapping(value = "/allocMenu", method = RequestMethod.POST)
    public CommonResult allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIdList) {

        if (!roleService.count(roleId)) {
            return CommonResult.validateFailed("不存在，该角色编号！");
        }

        if (roleService.allocMenu(roleId, menuIdList)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("给角色分配资源")
    @RequestMapping(value = "/allocResource", method = RequestMethod.POST)
    public CommonResult allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIdList) {

        if (!roleService.count(roleId)) {
            return CommonResult.validateFailed("不存在，该角色编号！");
        }


        if (roleService.allocResource(roleId, resourceIdList)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }
}
