package cn.yujian95.hospital.controller;

import cn.yujian95.hospital.common.api.CommonPage;
import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.dto.param.HospitalInfoParam;
import cn.yujian95.hospital.dto.param.HospitalOutpatientRelationParam;
import cn.yujian95.hospital.dto.param.HospitalSpecialRelationParam;
import cn.yujian95.hospital.entity.HospitalInfo;
import cn.yujian95.hospital.entity.HospitalSpecial;
import cn.yujian95.hospital.service.IHospitalInfoService;
import cn.yujian95.hospital.service.IHospitalOutpatientService;
import cn.yujian95.hospital.service.IHospitalSpecialService;
import io.swagger.annotations.*;
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

    @Resource
    private IHospitalSpecialService specialService;

    @Resource
    private IHospitalOutpatientService outpatientService;

    @ApiOperation(value = "添加医院信息", notes = "传入 医院信息参数（名称，图片、电话，地址，简介）")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public CommonResult insertHospitalInfo(@RequestBody HospitalInfoParam param) {

        if (infoService.count(param.getPhone())) {
            return CommonResult.validateFailed("该电话号码，已存在！");
        }

        if (infoService.insert(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "更新医院信息", notes = "传入 医院编号、医院信息参数（名称，图片、电话，地址，简介）")
    @ApiImplicitParam(name = "id", value = "医院编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/info/{id}", method = RequestMethod.PUT)
    public CommonResult updateHospitalInfo(@PathVariable Long id, @RequestBody HospitalInfoParam param) {

        if (infoService.count(param.getPhone())) {
            return CommonResult.validateFailed("该电话号码，已存在！");
        }

        if (!infoService.count(id)) {
            return CommonResult.validateFailed("不存在，该医院编号！");
        }

        if (infoService.update(id, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "删除医院信息", notes = "传入 医院编号")
    @ApiImplicitParam(name = "id", value = "医院编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/info/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteHospitalInfo(@PathVariable Long id) {

        if (!infoService.count(id)) {
            return CommonResult.validateFailed("不存在，该医院编号！");
        }

        if (infoService.delete(id)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "分页：搜索医院信息", notes = "传入 医院名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "医院名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true)
    })
    @RequestMapping(value = "/info/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<HospitalInfo>> searchHospitalInfo(@RequestParam(required = false) String name,
                                                                     @RequestParam Integer pageNum,
                                                                     @RequestParam Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(infoService.list(name, pageNum, pageSize)));
    }

    @ApiOperation(value = "添加专科到医院中", notes = "传入 医院专科关系参数（医院编号、专科编号）")
    @RequestMapping(value = "/special/relation", method = RequestMethod.POST)
    public CommonResult insertSpecialRelation(@RequestBody HospitalSpecialRelationParam param) {

        if (!infoService.count(param.getHospitalId())) {
            return CommonResult.validateFailed("不存在，该医院编号！");
        }

        if (!specialService.count(param.getSpecialId())) {
            return CommonResult.validateFailed("不存在，该专科编号! ");
        }

        if (infoService.countSpecialRelation(param)) {
            return CommonResult.validateFailed("已存在，该专科关系！");
        }

        if (infoService.insertSpecialRelation(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "移除医院中的专科", notes = "传入 医院编号、专科编号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hospitalId", value = "医院编号", paramType = "query", dataType = "Long"
                    , required = true),
            @ApiImplicitParam(name = "specialId", value = "专科编号", paramType = "query", dataType = "Long"
                    , required = true)
    })
    @RequestMapping(value = "/special/relation", method = RequestMethod.DELETE)
    public CommonResult deleteSpecialRelation(@RequestParam Long hospitalId, @RequestParam Long specialId) {

        if (!infoService.count(hospitalId)) {
            return CommonResult.validateFailed("不存在，该医院编号！");
        }

        if (!specialService.count(specialId)) {
            return CommonResult.validateFailed("不存在，该专科编号！");
        }

        if (infoService.deleteSpecialRelation(hospitalId, specialId)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "添加门诊到医院中", notes = "传入 医院门诊关系参数（医院编号、门诊编号）")
    @RequestMapping(value = "/outpatient/relation", method = RequestMethod.POST)
    public CommonResult insertOutpatientRelation(@RequestBody HospitalOutpatientRelationParam param) {

        if (!infoService.count(param.getHospitalId())) {
            return CommonResult.validateFailed("不存在，该医院编号！");
        }

        if (!outpatientService.count(param.getOutpatientId())) {
            return CommonResult.validateFailed("不存在，该门诊编号! ");
        }

        if (infoService.countOutpatientRelation(param)) {
            return CommonResult.validateFailed("已存在，该门诊关系！");
        }

        if (infoService.insertOutpatientRelation(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");

    }

    @ApiOperation(value = "移除医院中的门诊", notes = "传入 医院编号、门诊编号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hospitalId", value = "医院编号", paramType = "query", dataType = "Long"
                    , required = true),
            @ApiImplicitParam(name = "outpatientId", value = "门诊编号", paramType = "query", dataType = "Long"
                    , required = true)
    })
    @RequestMapping(value = "/outpatient/relation", method = RequestMethod.DELETE)
    public CommonResult deleteOutpatientRelation(@RequestParam Long hospitalId, @RequestParam Long outpatientId) {

        if (!infoService.count(hospitalId)) {
            return CommonResult.validateFailed("不存在，该医院编号！");
        }

        if (!outpatientService.count(outpatientId)) {
            return CommonResult.validateFailed("不存在，该门诊编号! ");
        }

        if (infoService.deleteOutpatientRelation(hospitalId, outpatientId)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "获取医院，所属专科列表", notes = "传入 专科名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hospitalId", value = "医院编号", paramType = "path", dataType = "Long",
                    required = true),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true)
    })
    @RequestMapping(value = "/special/list/{hospitalId}", method = RequestMethod.GET)
    public CommonResult<CommonPage<HospitalSpecial>> listSpecialByHospital(@PathVariable Long hospitalId,
                                                                           @RequestParam Integer pageNum,
                                                                           @RequestParam Integer pageSize) {

        if (!infoService.count(hospitalId)) {
            return CommonResult.validateFailed("不存在，该医院编号！");
        }

        return CommonResult.success(CommonPage.restPage(specialService.list(hospitalId, pageNum, pageSize)));
    }
}
