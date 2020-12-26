package cn.yujian95.little.admin.modules.power.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.yujian95.little.admin.modules.power.param.PowerAdminParam;
import cn.yujian95.little.admin.modules.power.param.UpdateAdminPasswordParam;
import cn.yujian95.little.admin.modules.power.service.IPowerAdminRoleRelationService;
import cn.yujian95.little.admin.modules.power.service.IPowerAdminService;
import cn.yujian95.little.admin.modules.power.service.IPowerRoleService;
import cn.yujian95.little.common.api.CommonPage;
import cn.yujian95.little.common.api.CommonResult;
import cn.yujian95.little.mbg.modules.power.entity.PowerAdmin;
import cn.yujian95.little.mbg.modules.power.entity.PowerAdminRoleRelation;
import cn.yujian95.little.mbg.modules.power.entity.PowerRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限账号 前端接口控制器
 * </p>
 *
 * @author yujian95 yujian95/cn@163.com
 * @since 2020-10-14
 */
@Api(value = "PowerAdminController", tags = "权限账号")
@RestController
@RequestMapping("/power")
public class PowerAdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PowerAdminController.class);

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Resource
    private IPowerAdminService powerAdminService;

    @Resource
    private IPowerAdminRoleRelationService powerAdminRoleRelationService;

    @Resource
    private IPowerRoleService powerRoleService;

    @ApiOperation("注册权限账号")
    @PostMapping("/admin/register")
    public CommonResult registerPowerAdmin(@RequestBody PowerAdminParam param) {

        if (powerAdminService.isExistAdmin(param.getUsername())) {
            return CommonResult.validateFailed("该账号名称，已被使用！");
        }

        if (powerAdminService.registerAdmin(param)) {
            LOGGER.debug("register PowerAdmin success:{}", param);
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation("刷新权限账号 Token")
    @GetMapping("/admin/token/refresh")
    public CommonResult refreshPowerAdminToken(HttpServletRequest request) {

        String refreshToken = powerAdminService.refreshToken(request.getHeader(tokenHeader));

        if (StrUtil.isEmpty(refreshToken)) {
            return CommonResult.validateFailed("token已经过期！");
        }

        return CommonResult.success(tokenHead + " " + refreshToken);
    }

    @ApiOperation("登录权限账号，获取 Token")
    @GetMapping("/admin/login")
    public CommonResult loginPowerAdminToken(@RequestParam String username, @RequestParam String password) {

        if (!powerAdminService.isExistAdmin(username)) {
            return CommonResult.validateFailed("不存在，该权限账号：" + username);
        }

        return CommonResult.success(tokenHead + " " + powerAdminService.loginAdmin(username, password));
    }

    @ApiOperation("获取指定 id，权限账号")
    @GetMapping("/admin/{id}")
    public CommonResult<PowerAdmin> getPowerAdmin(@PathVariable Long id) {

        if (powerAdminService.isNotExist(id)) {
            LOGGER.debug("get PowerAdmin fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        return CommonResult.success(powerAdminService.getById(id));
    }

    @ApiOperation("获取当前，权限账号")
    @GetMapping("/admin/info")
    public CommonResult getCurrentPowerAdmin(Principal principal) {

        if (principal == null) {
            return CommonResult.unauthorized(null);
        }

        String username = principal.getName();
        Optional<PowerAdmin> optional = powerAdminService.getAdminByUsername(username);

        if (optional.isPresent()) {
            PowerAdmin admin = optional.get();

            Map<String, Object> data = new HashMap<>();
            data.put("username", admin.getUsername());
            data.put("menus", powerRoleService.listMenuByAdminId(admin.getId()));
            data.put("icon", admin.getIcon());

            List<PowerRole> roleList = powerAdminService.listRole(admin.getId());

            if (CollUtil.isNotEmpty(roleList)) {
                List<String> roles = roleList.stream()
                        .map(PowerRole::getName)
                        .collect(Collectors.toList());

                data.put("roles", roles);
            }

            return CommonResult.success(data);
        }

        return CommonResult.failed();
    }

    @ApiOperation("获取所有权限账号")
    @GetMapping("/admin/list/all")
    public CommonResult<List<PowerAdmin>> listAllPowerAdmin() {
        return CommonResult.success(powerAdminService.list());
    }

    @ApiOperation("分页获取权限账号")
    @GetMapping("/admin/list")
    public CommonResult<CommonPage<PowerAdmin>> listPowerAdmin(@RequestParam(defaultValue = "1") Integer pageNum,
                                                               @RequestParam(defaultValue = "15") Integer pageSize) {

        Page<PowerAdmin> page = new Page<>(pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(powerAdminService.page(page)));
    }

    @ApiOperation("分页获取权限账号")
    @GetMapping("/admin/search")
    public CommonResult<CommonPage<PowerAdmin>> searchPowerAdmin(@RequestParam(required = false) String keyword,
                                                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                                                 @RequestParam(defaultValue = "15") Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(powerAdminService.search(keyword, pageNum, pageSize)));
    }

    @ApiOperation("添加权限账号")
    @PostMapping("/admin")
    public CommonResult<PowerAdmin> createPowerAdmin(@RequestBody PowerAdmin powerAdmin) {

        if (powerAdminService.save(powerAdmin)) {
            LOGGER.debug("create PowerAdmin success:{}", powerAdmin);
            return CommonResult.success(powerAdmin);
        }

        LOGGER.debug("create PowerAdmin failed:{}", powerAdmin);
        return CommonResult.failed("服务器错误，请联系权限账号！");
    }

    @ApiOperation("编辑指定 id，权限账号")
    @PutMapping("/admin")
    public CommonResult<PowerAdmin> updatePowerAdmin(@RequestBody PowerAdmin powerAdmin) {

        if (powerAdminService.isNotExist(powerAdmin.getId())) {
            LOGGER.debug("update PowerAdmin fail，id not exist :{}", powerAdmin);
            return CommonResult.validateFailed("不存在，该记录编号：" + powerAdmin.getId());
        }

        if (powerAdminService.updateById(powerAdmin)) {
            LOGGER.debug("update PowerAdmin success:{}", powerAdmin);
            return CommonResult.success(powerAdmin);
        }

        LOGGER.debug("update PowerAdmin failed:{}", powerAdmin);
        return CommonResult.failed("服务器错误，请联系权限账号！");
    }

    @ApiOperation("删除指定 id，权限账号")
    @DeleteMapping("/admin/{id}")
    public CommonResult deletePowerAdmin(@PathVariable Long id) {

        if (powerAdminService.isNotExist(id)) {
            LOGGER.debug("delete PowerAdmin fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        if (powerAdminService.removeById(id)) {
            LOGGER.debug("delete PowerAdmin success:{}", id);
            return CommonResult.success();
        }

        LOGGER.debug("delete PowerAdmin failed:{}", id);
        return CommonResult.failed("服务器错误，请联系权限账号！");
    }

    @ApiOperation("获取指定 id，权限账号关联角色")
    @GetMapping("/admin/role/relation")
    public CommonResult<PowerAdminRoleRelation> getPowerAdminRoleRelation(@PathVariable Long id) {

        if (powerAdminRoleRelationService.isNotExist(id)) {
            LOGGER.debug("get PowerAdminRoleRelation fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        return CommonResult.success(powerAdminRoleRelationService.getById(id));
    }

    @ApiOperation("获取所有权限账号关联角色")
    @GetMapping("/admin/role/relation/list/all")
    public CommonResult<List<PowerAdminRoleRelation>> listAllPowerAdminRoleRelation() {
        return CommonResult.success(powerAdminRoleRelationService.list());
    }

    @ApiOperation("分页获取权限账号关联角色")
    @GetMapping("/admin/role/relation/list")
    public CommonResult<CommonPage<PowerAdminRoleRelation>> listPowerAdminRoleRelation(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                                       @RequestParam(defaultValue = "15") Integer pageSize) {

        Page<PowerAdminRoleRelation> page = new Page<>(pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(powerAdminRoleRelationService.page(page)));
    }

    @ApiOperation("添加权限账号关联角色")
    @PostMapping("/admin/role/relation")
    public CommonResult<PowerAdminRoleRelation> createPowerAdminRoleRelation(@RequestBody PowerAdminRoleRelation powerAdminRoleRelation) {

        if (powerAdminRoleRelationService.save(powerAdminRoleRelation)) {
            LOGGER.debug("create PowerAdminRoleRelation success:{}", powerAdminRoleRelation);
            return CommonResult.success(powerAdminRoleRelation);
        }

        LOGGER.debug("create PowerAdminRoleRelation failed:{}", powerAdminRoleRelation);
        return CommonResult.failed("服务器错误，请联系权限账号！");
    }

    @ApiOperation("编辑指定 id，权限账号关联角色")
    @PutMapping("/admin/role/relation")
    public CommonResult<PowerAdminRoleRelation> updatePowerAdminRoleRelation(@RequestBody PowerAdminRoleRelation powerAdminRoleRelation) {

        if (powerAdminRoleRelationService.isNotExist(powerAdminRoleRelation.getId())) {
            LOGGER.debug("update PowerAdminRoleRelation fail，id not exist :{}", powerAdminRoleRelation);
            return CommonResult.validateFailed("不存在，该记录编号：" + powerAdminRoleRelation.getId());
        }

        if (powerAdminRoleRelationService.updateById(powerAdminRoleRelation)) {
            LOGGER.debug("update PowerAdminRoleRelation success:{}", powerAdminRoleRelation);
            return CommonResult.success(powerAdminRoleRelation);
        }

        LOGGER.debug("update PowerAdminRoleRelation failed:{}", powerAdminRoleRelation);
        return CommonResult.failed("服务器错误，请联系权限账号！");
    }

    @ApiOperation("删除指定 id，权限账号关联角色")
    @DeleteMapping("/admin/role/relation/{id}")
    public CommonResult deletePowerAdminRoleRelation(@PathVariable Long id) {

        if (powerAdminRoleRelationService.isNotExist(id)) {
            LOGGER.debug("delete PowerAdminRoleRelation fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        if (powerAdminRoleRelationService.removeById(id)) {
            LOGGER.debug("delete PowerAdminRoleRelation success:{}", id);
            return CommonResult.success();
        }

        LOGGER.debug("delete PowerAdminRoleRelation failed:{}", id);
        return CommonResult.failed("服务器错误，请联系权限账号！");
    }

    @ApiOperation("修改指定用户密码")
    @PutMapping("/admin/password")
    public CommonResult updatePowerAdminPassword(@Validated @RequestBody UpdateAdminPasswordParam param) {

        if (powerAdminService.updatePassword(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation("修改帐号状态")
    @PutMapping("/admin/status/{id}")
    public CommonResult updatePowerAdminStatus(@PathVariable Long id, @RequestParam Integer status) {

        if (powerAdminService.isNotExist(id)) {
            return CommonResult.validateFailed("不存在，该管理员编号：" + id);
        }

        PowerAdmin admin = new PowerAdmin();
        admin.setId(id);
        admin.setPassword(null);
        admin.setStatus(status);
        admin.setGmtModified(new Date());

        if (powerAdminService.updateById(admin)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation("给用户分配角色")
    @PutMapping("/admin/role")
    public CommonResult updatePowerAdminRole(@RequestParam Long adminId, @RequestParam List<Long> roleIdList) {
        if (powerAdminService.updateRole(adminId, roleIdList)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation("获取指定用户的角色")
    @GetMapping("/admin/role/{id}")
    public CommonResult<List<PowerRole>> getRoleList(@PathVariable Long id) {
        return CommonResult.success(powerAdminService.listRole(id));
    }

    @ApiOperation("管理员登出功能")
    @PostMapping("/admin/logout")
    public CommonResult logoutPowerAdmin() {
        return CommonResult.success();
    }
}

