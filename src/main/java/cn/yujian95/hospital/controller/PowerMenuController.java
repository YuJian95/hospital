package cn.yujian95.hospital.controller;

import cn.yujian95.hospital.common.api.CommonPage;
import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.dto.PowerMenuNode;
import cn.yujian95.hospital.dto.param.PowerMenuParam;
import cn.yujian95.hospital.entity.PowerMenu;
import cn.yujian95.hospital.service.IPowerMenuService;
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

@Api(value = "权限模块", tags = "权限菜单接口")
@RestController
@CrossOrigin
@RequestMapping("/power")
public class PowerMenuController {

    @Resource
    private IPowerMenuService menuService;

    @ApiOperation("添加权限菜单")
    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    public CommonResult insertMenu(@RequestBody PowerMenuParam param) {


        if (menuService.insert(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("修改权限菜单")
    @RequestMapping(value = "/menu/{id}", method = RequestMethod.PUT)
    public CommonResult updateMenu(@PathVariable Long id, @RequestBody PowerMenuParam param) {

        if (!menuService.count(id)) {
            return CommonResult.validateFailed("不存在，该菜单编号！");
        }

        if (menuService.update(id, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("获取菜单详情")
    @RequestMapping(value = "/menu/{id}", method = RequestMethod.GET)
    public CommonResult<PowerMenu> getMenu(@PathVariable Long id) {
        if (!menuService.count(id)) {
            return CommonResult.validateFailed("不存在，该菜单编号！");
        }

        Optional<PowerMenu> menuOptional = menuService.get(id);

        return menuOptional.map(CommonResult::success)
                .orElseGet(() -> CommonResult.failed("服务器错误，请联系管理员！"));

    }

    @ApiOperation("删除权限菜单")
    @RequestMapping(value = "/menu/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteMenu(@PathVariable Long id) {
        if (!menuService.count(id)) {
            return CommonResult.validateFailed("不存在，该菜单编号！");
        }

        if (menuService.delete(id)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("分页查询权限菜单")
    @RequestMapping(value = "/menu/list/{parentId}", method = RequestMethod.GET)
    public CommonResult<CommonPage<PowerMenu>> listMenu(@PathVariable Long parentId,
                                                        @RequestParam(defaultValue = "1") Integer pageNum,
                                                        @RequestParam(defaultValue = "5") Integer pageSize) {

        List<PowerMenu> menuList = menuService.list(parentId, pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(menuList));
    }

    @ApiOperation("树形结构返回所有菜单列表")
    @RequestMapping(value = "/menu/tree", method = RequestMethod.GET)
    public CommonResult<List<PowerMenuNode>> treeMenuList() {
        List<PowerMenuNode> list = menuService.treeList();

        return CommonResult.success(list);
    }

    @ApiOperation("修改菜单显示状态")
    @RequestMapping(value = "/menu/hidden/{id}", method = RequestMethod.POST)
    public CommonResult updateHiddenMenu(@PathVariable Long id, @RequestParam Integer hidden) {
        if (!menuService.count(id)) {
            return CommonResult.validateFailed("不存在，该菜单编号！");
        }

        if (menuService.updateHidden(id, hidden)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

}
