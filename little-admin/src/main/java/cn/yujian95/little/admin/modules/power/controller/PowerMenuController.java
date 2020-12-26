package cn.yujian95.little.admin.modules.power.controller;

import cn.yujian95.little.admin.modules.power.dto.PowerMenuNode;
import cn.yujian95.little.admin.modules.power.service.IPowerMenuService;
import cn.yujian95.little.common.api.CommonPage;
import cn.yujian95.little.common.api.CommonResult;
import cn.yujian95.little.mbg.modules.power.entity.PowerMenu;
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
 * 权限菜单  前端接口控制器
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
@Api(value = "PowerMenuController", tags = "权限菜单 ")
@RestController
@AllArgsConstructor
@RequestMapping("/power")
public class PowerMenuController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PowerMenuController.class);

    private IPowerMenuService powerMenuService;

    @ApiOperation("获取指定 id，权限菜单 ")
    @GetMapping("/menu/{id}")
    public CommonResult<PowerMenu> getPowerMenu(@PathVariable Long id) {

        if (powerMenuService.isNotExist(id)) {
            LOGGER.debug("get PowerMenu fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        return CommonResult.success(powerMenuService.getById(id));
    }

    @ApiOperation("获取所有权限菜单 ")
    @GetMapping("/menu/list/all")
    public CommonResult<List<PowerMenu>> listAllPowerMenu() {
        return CommonResult.success(powerMenuService.list());
    }

    @ApiOperation("分页获取权限菜单 ")
    @GetMapping("/menu/list")
    public CommonResult<CommonPage<PowerMenu>> listPowerMenu(@RequestParam(defaultValue = "1") Integer pageNum,
                                                             @RequestParam(defaultValue = "15") Integer pageSize) {

        Page<PowerMenu> page = new Page<>(pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(powerMenuService.page(page)));
    }

    @ApiOperation("添加权限菜单 ")
    @PostMapping("/menu")
    public CommonResult<PowerMenu> createPowerMenu(@RequestBody PowerMenu powerMenu) {

        if (powerMenuService.create(powerMenu)) {
            LOGGER.debug("create PowerMenu success:{}", powerMenu);
            return CommonResult.success(powerMenu);
        }

        LOGGER.debug("create PowerMenu failed:{}", powerMenu);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("编辑指定 id，权限菜单 ")
    @PutMapping("/menu")
    public CommonResult<PowerMenu> updatePowerMenu(@RequestBody PowerMenu powerMenu) {

        if (powerMenuService.isNotExist(powerMenu.getId())) {
            LOGGER.debug("update PowerMenu fail，id not exist :{}", powerMenu);
            return CommonResult.validateFailed("不存在，该记录编号：" + powerMenu.getId());
        }

        if (powerMenuService.update(powerMenu)) {
            LOGGER.debug("update PowerMenu success:{}", powerMenu);
            return CommonResult.success(powerMenu);
        }

        LOGGER.debug("update PowerMenu failed:{}", powerMenu);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("删除指定 id，权限菜单 ")
    @DeleteMapping("/menu/{id}")
    public CommonResult deletePowerMenu(@PathVariable Long id) {

        if (powerMenuService.isNotExist(id)) {
            LOGGER.debug("delete PowerMenu fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        if (powerMenuService.removeById(id)) {
            LOGGER.debug("delete PowerMenu success:{}", id);
            return CommonResult.success();
        }

        LOGGER.debug("delete PowerMenu failed:{}", id);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("分页查询后台菜单")
    @GetMapping("/menu/list/{parentId}")
    public CommonResult<CommonPage<PowerMenu>> list(@PathVariable Long parentId,
                                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                                    @RequestParam(defaultValue = "15") Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(powerMenuService.search(parentId, pageSize, pageNum)));
    }

    @ApiOperation("树形结构返回所有菜单列表")
    @GetMapping("/menu/list/tree")
    public CommonResult<List<PowerMenuNode>> treeList() {
        return CommonResult.success(powerMenuService.treeList());
    }

    @ApiOperation("修改菜单显示状态")
    @PutMapping("/menu/hidden/{id}")
    public CommonResult hiddenPowerMenu(@PathVariable Long id, @RequestParam Integer hidden) {

        if (powerMenuService.updateHidden(id, hidden)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }
}

