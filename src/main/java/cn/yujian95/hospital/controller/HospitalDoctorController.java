package cn.yujian95.hospital.controller;

import cn.yujian95.hospital.common.api.CommonPage;
import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.dto.param.HospitalDoctorInfoParam;
import cn.yujian95.hospital.entity.HospitalDoctorInfo;
import cn.yujian95.hospital.entity.HospitalInfo;
import cn.yujian95.hospital.service.IHospitalDoctorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/4
 */

@Api(value = "医院模块", tags = "医生信息接口")
@RestController
@CrossOrigin
@RequestMapping("/hospital")
public class HospitalDoctorController {

    @Resource
    private IHospitalDoctorService doctorService;

    @ApiOperation(value = "添加医生信息", notes = "传入 医生信息参数（姓名，头像，职称，专长）")
    @RequestMapping(value = "/doctor", method = RequestMethod.GET)
    public CommonResult insertDoctor(@RequestBody HospitalDoctorInfoParam param) {

        if (doctorService.insert(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "更新医生信息", notes = "传入 医生编号、医生信息参数（姓名，头像，职称，专长）")
    @ApiImplicitParam(name = "id", value = "医生编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/doctor/{id}", method = RequestMethod.PUT)
    public CommonResult updateDoctor(@PathVariable Long id, @RequestBody HospitalDoctorInfoParam param) {
        if (!doctorService.count(id)) {
            return CommonResult.validateFailed("不存在，该医生编号");
        }

        if (doctorService.update(id, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "删除医生信息", notes = "传入 医生编号")
    @ApiImplicitParam(name = "id", value = "医生编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/doctor/{id}", method = RequestMethod.DELETE)
    public CommonResult deleteDoctor(@PathVariable Long id) {
        if (!doctorService.count(id)) {
            return CommonResult.validateFailed("不存在，该医生编号");
        }

        if (doctorService.delete(id)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "分页：搜索医生信息", notes = "传入 医生姓名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "医生姓名", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true),
    })
    @RequestMapping(value = "/doctor/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<HospitalDoctorInfo>> searchDoctor(@RequestParam(required = false) String name,
                                                                     @RequestParam Integer pageNum,
                                                                     @RequestParam Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(doctorService.list(name, pageNum, pageSize)));
    }


    @ApiOperation(value = "获取医生信息", notes = "传入 医生编号")
    @ApiImplicitParam(name = "id", value = "医生编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/doctor/{id}", method = RequestMethod.GET)
    public CommonResult<HospitalDoctorInfo> getDoctor(@PathVariable Long id) {
        if (!doctorService.count(id)) {
            return CommonResult.validateFailed("不存在，该医生编号");
        }

        Optional<HospitalDoctorInfo> doctorOptional = doctorService.getOptional(id);

        return doctorOptional.map(CommonResult::success)
                .orElseGet(() -> CommonResult.failed("服务器错误，请联系管理员！"));

    }

}
