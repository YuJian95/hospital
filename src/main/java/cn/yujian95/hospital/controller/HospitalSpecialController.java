package cn.yujian95.hospital.controller;

import cn.yujian95.hospital.common.api.CommonPage;
import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.dto.param.HospitalSpecialParam;
import cn.yujian95.hospital.dto.param.HospitalSpecialRelationParam;
import cn.yujian95.hospital.entity.HospitalSpecial;
import cn.yujian95.hospital.service.IHospitalInfoService;
import cn.yujian95.hospital.service.IHospitalSpecialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/5
 */

@Api(value = "医院模块", tags = "医院专科接口")
@RestController
@CrossOrigin
@RequestMapping("/hospital")
public class HospitalSpecialController {

    @Resource
    private IHospitalSpecialService specialService;

    @Resource
    private IHospitalInfoService infoService;

    @ApiOperation(value = "添加专科信息", notes = "传入 专科信息参数（名称，描述）")
    @RequestMapping(value = "/special", method = RequestMethod.POST)
    public CommonResult insertSpecial(@RequestBody HospitalSpecialParam param) {

        if (infoService.count(param.getName())) {
            return CommonResult.validateFailed("已存在，该专科名称! ");
        }

        if (specialService.insert(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "更新专科信息", notes = "传入 专科编号、专科信息参数（名称，描述）")
    @ApiImplicitParam(name = "id", value = "专科编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/special/{id}", method = RequestMethod.PUT)
    public CommonResult updateSpecial(@PathVariable Long id, @RequestBody HospitalSpecialParam param) {

        if (!specialService.count(id)) {
            return CommonResult.validateFailed("不存在，该专科编号! ");
        }

        if (specialService.update(id, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "删除专科信息", notes = "传入 专科编号")
    @ApiImplicitParam(name = "id", value = "专科编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/special/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteSpecial(@PathVariable Long id) {

        if (!specialService.count(id)) {
            return CommonResult.validateFailed("不存在，该专科编号! ");
        }

        if (specialService.delete(id)) {
            return CommonResult.success();
        }
        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "获取专科信息", notes = "传入 专科编号")
    @ApiImplicitParam(name = "id", value = "专科编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/special/{id}", method = RequestMethod.GET)
    public CommonResult<HospitalSpecial> getSpecial(@PathVariable Long id) {

        if (!specialService.count(id)) {
            return CommonResult.validateFailed("不存在，该专科编号! ");
        }

        Optional<HospitalSpecial> optionalSpecial = specialService.getOptional(id);

        return optionalSpecial.map(CommonResult::success)
                .orElseGet(() -> CommonResult.failed("服务器错误，请联系管理员！"));
    }

    @ApiOperation(value = "分页：搜索专科信息", notes = "传入 专科名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "专科名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true),
    })
    @RequestMapping(value = "/special/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<HospitalSpecial>> searchSpecial(@RequestParam(required = false) String name,
                                                                   @RequestParam Integer pageNum,
                                                                   @RequestParam Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(specialService.list(name, pageNum, pageSize)));
    }
}
