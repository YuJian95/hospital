package cn.yujian95.hospital.controller;

import cn.yujian95.hospital.common.api.CommonPage;
import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.entity.LogAccountLogin;
import cn.yujian95.hospital.entity.LogOperation;
import cn.yujian95.hospital.service.ILogAccountLoginService;
import cn.yujian95.hospital.service.ILogOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 日志接口
 *
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/19
 */

@Api(value = "日志模块", tags = "日志接口")
@RestController
@CrossOrigin
@RequestMapping("/log")
public class LogController {

    @Resource
    private ILogAccountLoginService accountLoginService;

    @Resource
    private ILogOperationService operationService;

    @ApiOperation(value = "分页：搜索账号登录日志", notes = "传入 账号名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountName", value = "账号名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer", required = true),
    })
    @RequestMapping(value = "/account/login/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<LogAccountLogin>> searchAccountLogin(@RequestParam(required = false) String accountName,
                                                                        @RequestParam Integer pageNum,
                                                                        @RequestParam Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(accountLoginService.search(accountName, pageNum, pageSize)));

    }

    @ApiOperation(value = "分页：搜索账号操作日志", notes = "传入 账号名称，请求方法（get、post、delete、put）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountName", value = "账号名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "method", value = "请求方法", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer", required = true),
    })
    @RequestMapping(value = "/operation/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<LogOperation>> searchOperation(@RequestParam(required = false) String accountName,
                                                                  @RequestParam(required = false) String method,
                                                                  @RequestParam Integer pageNum,
                                                                  @RequestParam Integer pageSize) {

        return CommonResult.success(CommonPage.restPage(operationService.search(accountName, method, pageNum, pageSize)));
    }


}
