package cn.yujian95.hospital.controller;

import cn.yujian95.hospital.common.api.CommonPage;
import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.dto.param.HospitalOutpatientParam;
import cn.yujian95.hospital.entity.HospitalClinic;
import cn.yujian95.hospital.entity.HospitalOutpatient;
import cn.yujian95.hospital.service.IHospitalClinicService;
import cn.yujian95.hospital.service.IHospitalInfoService;
import cn.yujian95.hospital.service.IHospitalOutpatientService;
import cn.yujian95.hospital.service.IHospitalSpecialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/5
 */

@Api(value = "医院模块", tags = "门诊信息接口")
@RestController
@CrossOrigin
@RequestMapping("/hospital")
public class HospitalOutpatientController {

    @Resource
    private IHospitalOutpatientService outpatientService;

    @Resource
    private IHospitalSpecialService specialService;

    @Resource
    private IHospitalClinicService clinicService;

    @Resource
    private IHospitalInfoService infoService;

    @ApiOperation(value = "添加门诊信息", notes = "传入 门诊信息参数（名称，描述）")
    @RequestMapping(value = "/outpatient", method = RequestMethod.POST)
    public CommonResult insertOutpatient(@RequestBody HospitalOutpatientParam param) {

        if (!specialService.count(param.getSpecialId())) {
            return CommonResult.validateFailed("不存在，该专科编号！");
        }

        if (outpatientService.insert(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "更新门诊信息", notes = "传入 门诊编号、门诊信息参数（名称，描述）")
    @ApiImplicitParam(name = "id", value = "门诊编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/outpatient/{id}", method = RequestMethod.PUT)
    public CommonResult updateOutpatient(@PathVariable Long id, @RequestBody HospitalOutpatientParam param) {

        if (!outpatientService.count(id)) {
            return CommonResult.validateFailed("不存在，该门诊编号! ");
        }

        if (!specialService.count(param.getSpecialId())) {
            return CommonResult.validateFailed("不存在，该专科编号！");
        }

        if (outpatientService.update(id, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "删除门诊信息", notes = "传入 门诊编号")
    @ApiImplicitParam(name = "id", value = "门诊编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/outpatient/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteOutpatient(@PathVariable Long id) {

        if (!outpatientService.count(id)) {
            return CommonResult.validateFailed("不存在，该门诊编号! ");
        }

        if (outpatientService.delete(id)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "获取门诊信息", notes = "传入 门诊编号")
    @ApiImplicitParam(name = "id", value = "门诊编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/outpatient/{id}", method = RequestMethod.GET)
    public CommonResult<HospitalOutpatient> getOutpatient(@PathVariable Long id) {

        if (!outpatientService.count(id)) {
            return CommonResult.validateFailed("不存在，该门诊编号! ");
        }

        Optional<HospitalOutpatient> optionalOutpatient = outpatientService.getOptional(id);

        return optionalOutpatient.map(CommonResult::success)
                .orElseGet(() -> CommonResult.failed("服务器错误，请联系管理员！"));
    }

    @ApiOperation(value = "分页：搜索门诊信息", notes = "传入 门诊名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "门诊名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true),
    })
    @RequestMapping(value = "/outpatient/search", method = RequestMethod.GET)
    public CommonResult<CommonPage<HospitalOutpatient>> searchOutpatient(@RequestParam(required = false) String name,
                                                                         @RequestParam Integer pageNum,
                                                                         @RequestParam Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(outpatientService.list(name, pageNum, pageSize)));
    }

    @ApiOperation(value = "分页：查找指定医院、专科的门诊列表", notes = "传入 医院编号、专科编号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hospitalId", value = "医院编号", paramType = "query", dataType = "Long",
                    required = true),
            @ApiImplicitParam(name = "specialId", value = "专科编号", paramType = "query", dataType = "Long",
                    required = true),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true)
    })
    @RequestMapping(value = "/outpatient/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<HospitalOutpatient>> searchOutpatientByHospitalAndSpecial(@RequestParam Long hospitalId,
                                                                                             @RequestParam Long specialId,
                                                                                             @RequestParam Integer pageNum,
                                                                                             @RequestParam Integer pageSize) {
        if (!infoService.count(hospitalId)) {
            return CommonResult.validateFailed("不存在，该医院编号！");
        }

        if (!specialService.count(specialId)) {
            return CommonResult.validateFailed("不存在，该专科编号！");
        }

        return CommonResult.success(CommonPage.restPage(outpatientService.list(hospitalId, specialId, pageNum, pageSize)));
    }

    @ApiOperation(value = "分页：通过专科编号，获取未添加到医院的门诊列表", notes = "传入 专科编号，第几页，页大小")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "specialId", value = "专科编号", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true)
    })
    @RequestMapping(value = "/outpatient/alone", method = RequestMethod.GET)
    public CommonResult listAloneOutpatient(@RequestParam(required = false) Long specialId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return CommonResult.success(CommonPage.restPage(outpatientService.listAlone(specialId, pageNum, pageSize)));
    }

    @ApiOperation(value = "获取门诊所属诊室信息", notes = "传入 门诊编号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "门诊编号", paramType = "path", dataType = "Long",
                    required = true),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true),
    })
    @RequestMapping(value = "/outpatient/clinic/list/{id}", method = RequestMethod.GET)
    public CommonResult<CommonPage<HospitalClinic>> listClinic(@PathVariable Long id, @RequestParam Integer pageNum,
                                                               @RequestParam Integer pageSize) {
        if (!outpatientService.count(id)) {
            return CommonResult.validateFailed("不存在，该门诊编号! ");
        }

        return CommonResult.success(CommonPage.restPage(clinicService.list(id, pageNum, pageSize)));
    }
}
