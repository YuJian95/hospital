package cn.yujian95.little.admin.modules.system.controller;

import cn.yujian95.little.admin.modules.system.service.ISystemDictionaryService;
import cn.yujian95.little.common.api.CommonPage;
import cn.yujian95.little.common.api.CommonResult;
import cn.yujian95.little.mbg.modules.system.entity.SystemDictionary;
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
 * 系统数据字典 前端接口控制器
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
@Api(value = "SystemDictionaryController", tags = "系统数据字典")
@RestController
@AllArgsConstructor
@RequestMapping("/system")
public class SystemDictionaryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemDictionaryController.class);

    private ISystemDictionaryService systemDictionaryService;

    @ApiOperation("获取指定 id，系统数据字典")
    @GetMapping("/dictionary/{id}")
    public CommonResult<SystemDictionary> getSystemDictionary(@PathVariable Long id) {

        if (systemDictionaryService.isNotExist(id)) {
            LOGGER.debug("get SystemDictionary fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        return CommonResult.success(systemDictionaryService.getById(id));
    }

    @ApiOperation("获取所有系统数据字典")
    @GetMapping("/dictionary/list/all")
    public CommonResult<List<SystemDictionary>> listAllSystemDictionary() {
        return CommonResult.success(systemDictionaryService.list());
    }

    @ApiOperation("分页获取系统数据字典")
    @GetMapping("/dictionary/list")
    public CommonResult<CommonPage<SystemDictionary>> listSystemDictionary(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                           @RequestParam(defaultValue = "15") Integer pageSize) {

        Page<SystemDictionary> page = new Page<>(pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(systemDictionaryService.page(page)));
    }

    @ApiOperation("通过显示值 / 字典码、类型，查找系统数据字典")
    @GetMapping("/dictionary/search")
    public CommonResult<CommonPage<SystemDictionary>> searchSystemDictionary(@RequestParam(required = false) String keyword,
                                                                             @RequestParam(required = false) String type,
                                                                             @RequestParam(defaultValue = "1") Integer pageNum,
                                                                             @RequestParam(defaultValue = "15") Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(systemDictionaryService.search(keyword, type, pageNum, pageSize)));
    }

    @ApiOperation("添加系统数据字典")
    @PostMapping("/dictionary")
    public CommonResult<SystemDictionary> createSystemDictionary(@RequestBody SystemDictionary systemDictionary) {

        if (systemDictionaryService.save(systemDictionary)) {
            LOGGER.debug("create SystemDictionary success:{}", systemDictionary);
            return CommonResult.success(systemDictionary);
        }

        LOGGER.debug("create SystemDictionary failed:{}", systemDictionary);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("编辑指定 id，系统数据字典")
    @PutMapping("/dictionary")
    public CommonResult<SystemDictionary> updateSystemDictionary(@RequestBody SystemDictionary systemDictionary) {

        if (systemDictionaryService.isNotExist(systemDictionary.getId())) {
            LOGGER.debug("update SystemDictionary fail，id not exist :{}", systemDictionary);
            return CommonResult.validateFailed("不存在，该记录编号：" + systemDictionary.getId());
        }

        if (systemDictionaryService.updateById(systemDictionary)) {
            LOGGER.debug("update SystemDictionary success:{}", systemDictionary);
            return CommonResult.success(systemDictionary);
        }

        LOGGER.debug("update SystemDictionary failed:{}", systemDictionary);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("修改系统数据字典状态")
    @PutMapping("/dictionary/status/{id}")
    public CommonResult<SystemDictionary> updateSystemDictionaryStatus(@PathVariable Long id, @RequestParam Integer status) {

        if (systemDictionaryService.isNotExist(id)) {
            LOGGER.debug("update SystemDictionary fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        if (systemDictionaryService.updateStatus(id, status)) {
            LOGGER.debug("update SystemDictionary success, id:{} and status:{}", id, status);
            return CommonResult.success();
        }

        LOGGER.debug("update SystemDictionary failed, id:{} and status:{}", id, status);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation("删除指定 id，系统数据字典")
    @DeleteMapping("/dictionary/{id}")
    public CommonResult deleteSystemDictionary(@PathVariable Long id) {

        if (systemDictionaryService.isNotExist(id)) {
            LOGGER.debug("delete SystemDictionary fail，id not exist :{}", id);
            return CommonResult.validateFailed("不存在，该记录编号：" + id);
        }

        if (systemDictionaryService.removeById(id)) {
            LOGGER.debug("delete SystemDictionary success:{}", id);
            return CommonResult.success();
        }

        LOGGER.debug("delete SystemDictionary failed:{}", id);
        return CommonResult.failed("服务器错误，请联系管理员！");
    }
}

