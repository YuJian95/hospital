package cn.yujian95.hospital.controller;

import cn.yujian95.hospital.common.api.CommonPage;
import cn.yujian95.hospital.common.api.CommonResult;
import cn.yujian95.hospital.dto.UserMedicalCardDTO;
import cn.yujian95.hospital.dto.param.UserMedicalCardParam;
import cn.yujian95.hospital.dto.param.UserMedicalCardUpdateParam;
import cn.yujian95.hospital.entity.UserMedicalCard;
import cn.yujian95.hospital.service.IPowerAccountService;
import cn.yujian95.hospital.service.IUserMedicalCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/3
 */

@Api(value = "用户模块", tags = "就诊卡信息接口")
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserMedicalCardController {

//    public static final int BOY = 1;

    /**
     * 女性
     */
    private static final int GIRL = 2;
    private static final int MAX_CARD_NUMBER = 6;

    @Resource
    private IUserMedicalCardService medicalCardService;

    @Resource
    private IPowerAccountService powerAccountService;

    @ApiOperation(value = "分页：搜索就诊卡信息", notes = "传入 用户姓名、手机号、性别、第几页、页大小")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户姓名", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "性别：全部，0；男，1；女，2", paramType = "query",
                    dataType = "Integer", required = true, defaultValue = "0"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true),
    })
    @RequestMapping(value = "/card/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserMedicalCard>> searchMedicalCard(@RequestParam(required = false) String name,
                                                                       @RequestParam(required = false) String phone,
                                                                       @RequestParam(defaultValue = "0") Integer gender,
                                                                       @RequestParam Integer pageNum,
                                                                       @RequestParam Integer pageSize) {
        if (gender < 0 || gender > GIRL) {
            return CommonResult.validateFailed("性别参数错误：" + gender);
        }

        return CommonResult.success(CommonPage.restPage(medicalCardService.list(name, phone, gender, pageNum, pageSize)));
    }

    @ApiOperation(value = "添加就诊卡", notes = "传入 账号编号、就诊卡信息参数（关系类型、性别、姓名、手机号、证件号、出生日期）")
    @ApiImplicitParam(name = "accountId", value = "账号编号", paramType = "path", dataType = "Long",
            required = true)
    @RequestMapping(value = "/card/{accountId}", method = RequestMethod.POST)
    public CommonResult insertMedicalCard(@PathVariable Long accountId, @RequestBody UserMedicalCardParam param) {

        if (!powerAccountService.count(accountId)) {
            return CommonResult.validateFailed("不存在，该账号编号！");
        }

        if (medicalCardService.count(accountId) > MAX_CARD_NUMBER) {
            return CommonResult.validateFailed("绑定就诊卡数量不可超过：" + MAX_CARD_NUMBER + "！");
        }

        if (medicalCardService.insert(accountId, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }


    @ApiOperation(value = "修改就诊卡", notes = "传入 关系编号、就诊卡更新信息参数（关系类型、性别、姓名、就诊卡编号）")
    @ApiImplicitParam(name = "relationId", value = "关系编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/card/{relationId}", method = RequestMethod.PUT)
    public CommonResult updateMedicalCard(@PathVariable Long relationId, @RequestBody UserMedicalCardUpdateParam param) {
        if (!medicalCardService.countRelation(relationId)) {
            return CommonResult.validateFailed("不存在，该关系编号！");
        }

        if (medicalCardService.update(relationId, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "获取就诊卡", notes = "传入 就诊卡编号")
    @ApiImplicitParam(name = "cardId", value = "就诊卡编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/card/{cardId}", method = RequestMethod.GET)
    public CommonResult updateMedicalCard(@PathVariable Long cardId) {
        if (!medicalCardService.countCardId(cardId)) {
            return CommonResult.validateFailed("不存在，该就诊卡编号！");
        }

        Optional<UserMedicalCard> cardOptional = medicalCardService.getOptional(cardId);

        if (cardOptional.isPresent()) {
            return CommonResult.success(cardOptional.get());
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "获取用户就诊卡", notes = "传入 账号编号")
    @ApiImplicitParam(name = "accountId", value = "账号编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/card/list/{accountId}", method = RequestMethod.GET)
    public CommonResult<List<UserMedicalCardDTO>> listMedicalCard(@PathVariable Long accountId) {
        if (!powerAccountService.count(accountId)) {
            return CommonResult.validateFailed("不存在，该账号编号！");
        }

        return CommonResult.success(medicalCardService.list(accountId));
    }

    @ApiOperation(value = "删除就诊卡", notes = "传入 关系编号")
    @ApiImplicitParam(name = "relationId", value = "关系编号", paramType = "path", dataType = "Long", required = true)
    @RequestMapping(value = "/card/{relationId}", method = RequestMethod.DELETE)
    public CommonResult deleteMedicalCard(@PathVariable Long relationId) {
        if (!medicalCardService.countRelation(relationId)) {
            return CommonResult.validateFailed("不存在，该关系编号！");
        }

        if (medicalCardService.delete(relationId)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }


    @ApiOperation(value = "检查就诊卡数目是否超过限制", notes = "传入 账号编号")
    @ApiImplicitParam(name = "accountId", value = "账号编号", paramType = "path", dataType = "Long",
            required = true)
    @RequestMapping(value = "/card/number/{accountId}", method = RequestMethod.GET)
    public CommonResult<Boolean> countMedicalCard(@PathVariable Long accountId) {
        if (!powerAccountService.count(accountId)) {
            return CommonResult.validateFailed("不存在，该账号编号！");
        }

        return CommonResult.success(medicalCardService.count(accountId) > MAX_CARD_NUMBER);
    }

    @ApiOperation(value = "检查就诊卡信息是否存在", notes = "传入 身份证编号")
    @ApiImplicitParam(name = "identificationNumber", value = "身份证编号", paramType = "path", dataType = "String",
            required = true)
    @RequestMapping(value = "/card/identification/{identificationNumber}", method = RequestMethod.GET)
    public CommonResult<Boolean> countIdentificationNumber(@PathVariable String identificationNumber) {
        return CommonResult.success(medicalCardService.countIdentificationNumber(identificationNumber));
    }

}
