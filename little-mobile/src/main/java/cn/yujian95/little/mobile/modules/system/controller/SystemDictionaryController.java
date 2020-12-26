package cn.yujian95.little.mobile.modules.system.controller;

import cn.yujian95.little.common.api.CommonPage;
import cn.yujian95.little.common.api.CommonResult;
import cn.yujian95.little.mbg.modules.system.entity.SystemDictionary;
import cn.yujian95.little.mobile.modules.system.service.ISystemDictionaryService;
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
}

