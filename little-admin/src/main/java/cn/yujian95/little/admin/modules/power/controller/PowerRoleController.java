package cn.yujian95.little.admin.modules.power.controller;

import cn.yujian95.little.admin.modules.power.service.IPowerRoleMenuRelationService;
import cn.yujian95.little.admin.modules.power.service.IPowerRoleResourceRelationService;
import cn.yujian95.little.admin.modules.power.service.IPowerRoleService;
import cn.yujian95.little.common.api.CommonPage;
import cn.yujian95.little.common.api.CommonResult;
import cn.yujian95.little.mbg.modules.power.entity.*;
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
 * 权限角色 前端接口控制器
 * </p>
 *
 * @author yujian95 yujian95/cn@163.com
 * @since 2020-10-14
 */
@Api(value = "PowerRoleController", tags = "权限角色")
@RestController
@AllArgsConstructor
@RequestMapping("/power")
public class PowerRoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PowerRoleController.class);

    private IPowerRoleService powerRoleService;

    private IPowerRoleMenuRelationService powerRoleMenuRelationService;

    private IPowerRoleResourceRelationService powerRoleResourceRelationService;

    @ApiOperation("获取指定 id，权限角色")
    @GetMapping("/role/{id}")
    public CommonResult<PowerRole> getPowerRole(@PathVariable Long id) {

        if (powerRoleService.isNotExist(id)) {
            LOGGER.debug("get PowerRole fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        return CommonResult.success(powerRoleService.getById(id));
    }

    @ApiOperation("获取所有权限角色")
    @GetMapping("/role/list/all")
    public CommonResult<List<PowerRole>> listAllPowerRole() {
        return CommonResult.success(powerRoleService.list());
    }

    @ApiOperation("分页获取权限角色")
    @GetMapping("/role/list")
    public CommonResult<CommonPage<PowerRole>> listPowerRole(@RequestParam(defaultValue = "1") Integer pageNum,
                                                             @RequestParam(defaultValue = "15") Integer pageSize) {

        Page<PowerRole> page = new Page<>(pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(powerRoleService.page(page)));
    }

    @ApiOperation("添加权限角色")
    @PostMapping("/role")
    public CommonResult<PowerRole> createPowerRole(@RequestBody PowerRole powerRole) {

        if (powerRoleService.save(powerRole)) {
            LOGGER.debug("create PowerRole success:{}", powerRole);
            return CommonResult.success(powerRole);
        }

        LOGGER.debug("create PowerRole failed:{}", powerRole);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("编辑指定 id，权限角色")
    @PutMapping("/role")
    public CommonResult<PowerRole> updatePowerRole(@RequestBody PowerRole powerRole) {

        if (powerRoleService.isNotExist(powerRole.getId())) {
            LOGGER.debug("update PowerRole fail，id not exist :{}", powerRole);
            return CommonResult.validateFailed("不存在，该记录编号：" + powerRole.getId());
        }

        if (powerRoleService.updateById(powerRole)) {
            LOGGER.debug("update PowerRole success:{}", powerRole);
            return CommonResult.success(powerRole);
        }

        LOGGER.debug("update PowerRole failed:{}", powerRole);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("删除指定 id，权限角色")
    @DeleteMapping("/role/{id}")
    public CommonResult deletePowerRole(@PathVariable Long id) {

        if (powerRoleService.isNotExist(id)) {
            LOGGER.debug("delete PowerRole fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        if (powerRoleService.removeById(id)) {
            LOGGER.debug("delete PowerRole success:{}", id);
            return CommonResult.success();
        }

        LOGGER.debug("delete PowerRole failed:{}", id);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("批量删除角色")
    @DeleteMapping("/role/list")
    public CommonResult deleteListPowerRole(@RequestParam("ids") List<Long> idList) {

        if (powerRoleService.removeByIds(idList)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @GetMapping("/role/search")
    public CommonResult<CommonPage<PowerRole>> searchPowerRole(@RequestParam(required = false) String keyword,
                                                               @RequestParam(defaultValue = "1") Integer pageNum,
                                                               @RequestParam(defaultValue = "15") Integer pageSize) {
        Page<PowerRole> roleList = powerRoleService.search(keyword, pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(roleList));
    }

    @ApiOperation("修改角色状态")
    @PutMapping("/role/status/{id}")
    public CommonResult updatePowerRoleStatus(@PathVariable Long id, @RequestParam Integer status) {

        PowerRole role = new PowerRole();
        role.setId(id);
        role.setStatus(status);

        if (powerRoleService.updateById(role)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation("获取角色相关菜单")
    @GetMapping("/role/menu/{roleId}")
    public CommonResult<List<PowerMenu>> listMenuByRoleId(@PathVariable Long roleId) {

        List<PowerMenu> roleList = powerRoleService.listMenuByRoleId(roleId);

        return CommonResult.success(roleList);
    }

    @ApiOperation("获取角色相关资源")
    @GetMapping("/role/resource/{roleId}")
    public CommonResult<List<PowerResource>> listResourceByRoleId(@PathVariable Long roleId) {

        List<PowerResource> roleList = powerRoleService.listResource(roleId);

        return CommonResult.success(roleList);
    }

    @ApiOperation("给角色分配菜单")
    @PutMapping("/role/menu")
    public CommonResult allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIdList) {

        if (powerRoleService.allocMenu(roleId, menuIdList)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation("给角色分配资源")
    @PutMapping("/role/resource")
    public CommonResult allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIdList) {

        if (powerRoleService.allocResource(roleId, resourceIdList)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation("获取指定 id，权限角色关联菜单 ")
    @GetMapping("/role/menu/relation")
    public CommonResult<PowerRoleMenuRelation> getPowerRoleMenuRelation(@PathVariable Long id) {

        if (powerRoleMenuRelationService.isNotExist(id)) {
            LOGGER.debug("get PowerRoleMenuRelation fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        return CommonResult.success(powerRoleMenuRelationService.getById(id));
    }

    @ApiOperation("获取所有权限角色关联菜单 ")
    @GetMapping("/role/menu/relation/list/all")
    public CommonResult<List<PowerRoleMenuRelation>> listAllPowerRoleMenuRelation() {
        return CommonResult.success(powerRoleMenuRelationService.list());
    }

    @ApiOperation("分页获取权限角色关联菜单 ")
    @GetMapping("/role/menu/relation/list")
    public CommonResult<CommonPage<PowerRoleMenuRelation>> listPowerRoleMenuRelation(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                                     @RequestParam(defaultValue = "15") Integer pageSize) {

        Page<PowerRoleMenuRelation> page = new Page<>(pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(powerRoleMenuRelationService.page(page)));
    }

    @ApiOperation("添加权限角色关联菜单 ")
    @PostMapping("/role/menu/relation")
    public CommonResult<PowerRoleMenuRelation> createPowerRoleMenuRelation(@RequestBody PowerRoleMenuRelation powerRoleMenuRelation) {

        if (powerRoleMenuRelationService.save(powerRoleMenuRelation)) {
            LOGGER.debug("create PowerRoleMenuRelation success:{}", powerRoleMenuRelation);
            return CommonResult.success(powerRoleMenuRelation);
        }

        LOGGER.debug("create PowerRoleMenuRelation failed:{}", powerRoleMenuRelation);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("编辑指定 id，权限角色关联菜单 ")
    @PutMapping("/role/menu/relation")
    public CommonResult<PowerRoleMenuRelation> updatePowerRoleMenuRelation(@RequestBody PowerRoleMenuRelation powerRoleMenuRelation) {

        if (powerRoleMenuRelationService.isNotExist(powerRoleMenuRelation.getId())) {
            LOGGER.debug("update PowerRoleMenuRelation fail，id not exist :{}", powerRoleMenuRelation);
            return CommonResult.validateFailed("不存在，该记录编号：" + powerRoleMenuRelation.getId());
        }

        if (powerRoleMenuRelationService.updateById(powerRoleMenuRelation)) {
            LOGGER.debug("update PowerRoleMenuRelation success:{}", powerRoleMenuRelation);
            return CommonResult.success(powerRoleMenuRelation);
        }

        LOGGER.debug("update PowerRoleMenuRelation failed:{}", powerRoleMenuRelation);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("删除指定 id，权限角色关联菜单 ")
    @DeleteMapping("/role/menu/relation/{id}")
    public CommonResult deletePowerRoleMenuRelation(@PathVariable Long id) {

        if (powerRoleMenuRelationService.isNotExist(id)) {
            LOGGER.debug("delete PowerRoleMenuRelation fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        if (powerRoleMenuRelationService.removeById(id)) {
            LOGGER.debug("delete PowerRoleMenuRelation success:{}", id);
            return CommonResult.success();
        }

        LOGGER.debug("delete PowerRoleMenuRelation failed:{}", id);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("获取指定 id，权限角色关联资源 ")
    @GetMapping("/role/resource/relation")
    public CommonResult<PowerRoleResourceRelation> getPowerRoleResourceRelation(@PathVariable Long id) {

        if (powerRoleResourceRelationService.isNotExist(id)) {
            LOGGER.debug("get PowerRoleResourceRelation fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        return CommonResult.success(powerRoleResourceRelationService.getById(id));
    }

    @ApiOperation("获取所有权限角色关联资源 ")
    @GetMapping("/role/resource/relation/list/all")
    public CommonResult<List<PowerRoleResourceRelation>> listAllPowerRoleResourceRelation() {
        return CommonResult.success(powerRoleResourceRelationService.list());
    }

    @ApiOperation("分页获取权限角色关联资源 ")
    @GetMapping("/role/resource/relation/list")
    public CommonResult<CommonPage<PowerRoleResourceRelation>> listPowerRoleResourceRelation(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                                             @RequestParam(defaultValue = "15") Integer pageSize) {

        Page<PowerRoleResourceRelation> page = new Page<>(pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(powerRoleResourceRelationService.page(page)));
    }

    @ApiOperation("添加权限角色关联资源 ")
    @PostMapping("/role/resource/relation")
    public CommonResult<PowerRoleResourceRelation> createPowerRoleResourceRelation(@RequestBody PowerRoleResourceRelation powerRoleResourceRelation) {

        if (powerRoleResourceRelationService.save(powerRoleResourceRelation)) {
            LOGGER.debug("create PowerRoleResourceRelation success:{}", powerRoleResourceRelation);
            return CommonResult.success(powerRoleResourceRelation);
        }

        LOGGER.debug("create PowerRoleResourceRelation failed:{}", powerRoleResourceRelation);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("编辑指定 id，权限角色关联资源 ")
    @PutMapping("/role/resource/relation")
    public CommonResult<PowerRoleResourceRelation> updatePowerRoleResourceRelation(@RequestBody PowerRoleResourceRelation powerRoleResourceRelation) {

        if (powerRoleResourceRelationService.isNotExist(powerRoleResourceRelation.getId())) {
            LOGGER.debug("update PowerRoleResourceRelation fail，id not exist :{}", powerRoleResourceRelation);
            return CommonResult.validateFailed("不存在，该记录编号：" + powerRoleResourceRelation.getId());
        }

        if (powerRoleResourceRelationService.updateById(powerRoleResourceRelation)) {
            LOGGER.debug("update PowerRoleResourceRelation success:{}", powerRoleResourceRelation);
            return CommonResult.success(powerRoleResourceRelation);
        }

        LOGGER.debug("update PowerRoleResourceRelation failed:{}", powerRoleResourceRelation);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("删除指定 id，权限角色关联资源 ")
    @DeleteMapping("/role/resource/relation/{id}")
    public CommonResult deletePowerRoleResourceRelation(@PathVariable Long id) {

        if (powerRoleResourceRelationService.isNotExist(id)) {
            LOGGER.debug("delete PowerRoleResourceRelation fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        if (powerRoleResourceRelationService.removeById(id)) {
            LOGGER.debug("delete PowerRoleResourceRelation success:{}", id);
            return CommonResult.success();
        }

        LOGGER.debug("delete PowerRoleResourceRelation failed:{}", id);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }
}

