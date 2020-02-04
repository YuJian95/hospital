package cn.yujian95.hospital.controller;

import cn.yujian95.hospital.common.api.CommonPage;
import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.dto.param.HospitalInfoParam;
import cn.yujian95.hospital.entity.HospitalInfo;
import cn.yujian95.hospital.service.IHospitalInfoService;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/3
 */

@Api(value = "医院模块", tags = "医院信息接口")
@RestController
@CrossOrigin
@RequestMapping("/hospital")
public class HospitalInfoController {

    @Resource
    private IHospitalInfoService infoService;

    @ApiOperation(value = "添加医院信息", notes = "传入 医院信息参数（名称，电话，地址，简介，图片）")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('hospital:info:post')")
    public CommonResult insertHospitalInfo(@RequestBody HospitalInfoParam param) {

        if (infoService.count(param.getPhone())){
            return CommonResult.validateFailed("该电话号码，已存在！");
        }

        if (infoService.insert(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation(value = "更新医院信息", notes = "传入 医院编号、医院信息参数（名称，电话，地址，简介，图片）")
    @ApiImplicitParam(name = "id", value = "医院编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/info/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('hospital:info:put')")
    public CommonResult updateHospitalInfo(@PathVariable Long id, @RequestBody HospitalInfoParam param) {

        if (infoService.count(param.getPhone())){
            return CommonResult.validateFailed("该电话号码，已存在！");
        }

        if (!infoService.count(id)) {
            return CommonResult.validateFailed("不存在，该医院编号！");
        }

        if (infoService.update(id, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation(value = "删除医院信息", notes = "传入 医院编号")
    @ApiImplicitParam(name = "id", value = "医院编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/info/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('hospital:info:delete')")
    public CommonResult deleteHospitalInfo(@PathVariable Long id) {

        if (!infoService.count(id)) {
            return CommonResult.validateFailed("不存在，该医院编号！");
        }

        if (infoService.delete(id)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation(value = "分页：搜索医院信息", notes = "传入 医院名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "医院名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true),
    })
    @RequestMapping(value = "/info/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<HospitalInfo>> searchHospitalInfo(@RequestParam(required = false) String name,
                                                                     @RequestParam Integer pageNum,
                                                                     @RequestParam Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(infoService.list(name, pageNum, pageSize)));
    }

}
