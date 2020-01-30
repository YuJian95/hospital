### 流程

> 遵循《阿里 Java 开发手册》相关要求。

1. 设计数据表, 运行并添加相关外键链接。

2. 使用`Generator`, 逆向生成mapper、entity、mapper.xml相关文件。

3. 设计接口`IxxxService`，编辑相关doc注释。
    - insert()
    - delete()
    - getOptional() : 使用`Optional`解决空值异常。
    - list()
    - update()
    
4. 编写实现类`xxxServiceImpl`，实现`IxxxService`接口，使用`xxxMapper`(DAO)实现增删改查。

5. 编写`xxxController`, 调用`IxxxService`接口, 并添加`Swagger`注解, 添加相应访问权值。
    - 校验传入参数。
    - 满足`Restful`规范。

### 以普通用户表为例。
 
#### 1. 设计`user_basic_info`数据表

> 根据《 阿里 Java 开发手册》，数据表必须有 id，gmt_create, gmt_modified; 数据表命名必须全小写，单词之间用下划线隔开。

![设计普通用户表数据表](https://github.com/YuJian95/base-service/blob/master/document/images/feature-table-design.jpg)

这里的手机号是用于账号注册，与权限账号名做了一个外键关联。

**建表脚本（含外键约束）**

```sql
create table user_basic_info
(
	id bigint auto_increment comment '编号'
		primary key,
	name varchar(32) not null comment '姓名',
	phone varchar(32) not null comment '手机号',
	sex int default 1 not null comment '性别 1男，2女',
	birth_date datetime default CURRENT_TIMESTAMP not null comment '出生日期',
	gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	gmt_modified datetime default CURRENT_TIMESTAMP not null comment '更新时间',
	constraint user_basic_info_phone_uindex
		unique (phone),
	constraint user_basic_info_power_account_name_fk
		foreign key (phone) references power_account (name)
			on update cascade on delete cascade
)
comment '用户基础信息表 ';

```

#### 2. 执行`common/mbg/Generator`

这里可以先修改一下`resource/config/MybatisGeneratorConfig.xml`, 当然也可以直接运行，重新生成所有`mapper、entity、mapper.xml`

```xml
        <!-- % 生成所有数据表 对象 映射文件 -->
        <table tableName="user_basic_info" enableCountByExample="true" enableDeleteByExample="true"
               enableSelectByExample="true" enableUpdateByExample="true"/>
```

执行`Generator`后会自动生成，`UserBasicInfo`,`UserBasicInfoExample`,`UserBasicInfoMapper`,`UserBasicInfoMapper.xml`。

#### 3. 设计`IUserBasicInfoService`接口

**命名**

- insert()
- delete()
- update()
- getOptional() : 使用`Optional`解决空值异常。
- list()

```java
import cn.yujian95.base.dto.param.UserBasicInfoParam;
import cn.yujian95.base.entity.UserBasicInfo;

import java.util.List;
import java.util.Optional;

/**
 * 普通用户基础信息接口
 *
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/28
 */

public interface IUserBasicInfoService {

    /**
     * 创建普通用户信息
     *
     * @param phone 手机号
     * @param param 普通用户信息参数
     * @return 是否成功
     */
    boolean insert(String phone, UserBasicInfoParam param);

    /**
     * 更新普通用户信息
     *
     * @param id    用户编号
     * @param param 普通用户信息参数
     * @return 是否成功
     */
    boolean update(Long id, UserBasicInfoParam param);

    /**
     * 删除普通用户信息
     *
     * @param id 用户编号
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 获取普通用户信息
     *
     * @param id 用户编号
     * @return 普通用户信息
     */
    Optional<UserBasicInfo> getOptional(Long id);

    /**
     * 获取普通用户信息
     *
     * @param phone 手机号
     * @return 普通用户信息
     */
    Optional<UserBasicInfo> getOptionalByPhone(String phone);


    /**
     * 通过关键词，查找用户信息列表
     *
     * @param name     用户名
     * @param phone    手机号
     * @param sex      性别
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 用户信息列表
     */
    List<UserBasicInfo> list(String name, String phone, Integer sex, Integer pageNum, Integer pageSize);

    /**
     * 判断用户信息是否存在
     *
     * @param id 用户编号
     * @return 是否存在
     */
    boolean count(Long id);

    /**
     * 判断用户信息是否存在
     *
     * @param phone 手机号
     * @return 是否存在
     */
    boolean countByPhone(String phone);
}
```

**封装的普通用户基础信息参数**

```java
package cn.yujian95.base.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 普通用户基础信息参数
 *
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/28
 */

@ApiModel("普通用户基础信息参数")
@Data
public class UserBasicInfoParam implements Serializable {
    /**
     * 姓名
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 性别 1男，2女
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "性别 1男，2女")
    private Integer sex;

    /**
     * 出生日期
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "出生日期")
    private Date birthDate;
}

```

#### 4. 编写`UserBasicInfoServiceImpl`实现类

- UserBasicInfoMapper DAO 对象
- @Resource ：注入对象
- @Service ：表面这是一个业务类

```java
package cn.yujian95.base.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.yujian95.base.component.AliSendSmsComponent;
import cn.yujian95.base.dto.param.UserBasicInfoParam;
import cn.yujian95.base.entity.UserBasicInfo;
import cn.yujian95.base.entity.UserBasicInfoExample;
import cn.yujian95.base.mapper.UserBasicInfoMapper;
import cn.yujian95.base.service.IRedisService;
import cn.yujian95.base.service.IUserBasicInfoService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/28
 */

@Service
public class UserBasicInfoServiceImpl implements IUserBasicInfoService {

    private static final int GIRL = 2;
    private static final int BOY = 1;

    @Resource
    private UserBasicInfoMapper basicInfoMapper;

    @Resource
    private AliSendSmsComponent sendSmsComponent;

    @Resource
    private IRedisService redisService;

    /**
     * 存入 redis中的前缀
     */
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;

    /**
     * 验证码有效时间
     */
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    /**
     * 验证码长度
     */
    private static final int AUTH_CODE_LENGTH = 6;

    /**
     * 校验短信验证码
     *
     * @param phone 手机号
     * @param code  验证码
     * @return 是否正确
     */
    @Override
    public boolean verifyCode(String phone, String code) {

        String authCode = String.valueOf(redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + phone));

        if (StringUtils.isEmpty(authCode)) {
            return false;
        }

        return authCode.equals(code);
    }

    /**
     * 发送注册短信
     *
     * @param phone 手机号码
     * @return 是否成功
     */
    @Override
    public boolean sendMessage(String phone) {

        String code = RandomUtil.randomNumbers(AUTH_CODE_LENGTH);

        // 验证码绑定手机号并存储到 redis
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + phone, code, AUTH_CODE_EXPIRE_SECONDS);

        return sendSmsComponent.sendRegisterCode(phone, code);
    }

    /**
     * 创建普通用户信息
     *
     * @param phone 手机号
     * @param param 普通用户信息参数
     * @return 是否成功
     */
    @Override
    public boolean insert(String phone, UserBasicInfoParam param) {

        UserBasicInfo info = new UserBasicInfo();

        // 与登录账号名称一致
        info.setPhone(phone);

        BeanUtils.copyProperties(param, info);

        info.setGmtCreate(new Date());
        info.setGmtModified(new Date());

        return basicInfoMapper.insertSelective(info) > 0;
    }

    /**
     * 更新普通用户信息
     *
     * @param id    用户编号
     * @param param 普通用户信息参数
     * @return 是否成功
     */
    @Override
    public boolean update(Long id, UserBasicInfoParam param) {
        UserBasicInfo basicInfo = new UserBasicInfo();

        BeanUtils.copyProperties(param, basicInfo);

        basicInfo.setGmtModified(new Date());

        return basicInfoMapper.updateByPrimaryKeySelective(basicInfo) > 0;
    }

    /**
     * 删除普通用户信息
     *
     * @param id 用户编号
     * @return 是否成功
     */
    @Override
    public boolean delete(Long id) {
        return basicInfoMapper.deleteByPrimaryKey(id) > 0;
    }

    /**
     * 获取普通用户信息
     *
     * @param id 用户编号
     * @return 普通用户信息
     */
    @Override
    public Optional<UserBasicInfo> getOptional(Long id) {
        return Optional.ofNullable(basicInfoMapper.selectByPrimaryKey(id));
    }


    /**
     * 获取普通用户信息
     *
     * @param phone 手机号
     * @return 普通用户信息
     */
    @Override
    public Optional<UserBasicInfo> getOptionalByPhone(String phone) {

        UserBasicInfoExample example = new UserBasicInfoExample();

        example.createCriteria()
                .andPhoneEqualTo(phone);

        List<UserBasicInfo> list = basicInfoMapper.selectByExample(example);

        return Optional.ofNullable(list.get(0));
    }

    /**
     * 通过关键词，查找用户信息列表
     *
     * @param name     用户名
     * @param phone    手机号
     * @param sex      性别
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 用户信息列表
     */
    @Override
    public List<UserBasicInfo> list(String name, String phone, Integer sex, Integer pageNum, Integer pageSize) {
        // 分页器
        PageHelper.startPage(pageNum, pageSize);

        UserBasicInfoExample example = new UserBasicInfoExample();

        UserBasicInfoExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (!StringUtils.isEmpty(phone)) {
            criteria.andPhoneLike("%" + name + "%");
        }

        // 筛选男生或女生时
        if (sex == BOY || sex == GIRL) {
            criteria.andSexEqualTo(sex);
        }

        return basicInfoMapper.selectByExample(example);
    }

    /**
     * 判断用户信息是否存在
     *
     * @param phone 手机号
     * @return 是否存在
     */
    @Override
    public boolean countByPhone(String phone) {

        UserBasicInfoExample example = new UserBasicInfoExample();

        example.createCriteria()
                .andPhoneEqualTo(phone);

        return basicInfoMapper.countByExample(example) > 0;
    }

    /**
     * 判断用户信息是否存在
     *
     * @param id 用户编号
     * @return 是否存在
     */
    @Override
    public boolean count(Long id) {

        UserBasicInfoExample example = new UserBasicInfoExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return basicInfoMapper.countByExample(example) > 0;
    }
}

```

#### 5. 编写`UserBasicInfoController`控制器

> 这里设计满足`RESTful`规范的接口

- 进行简单的参数校验
- 通过逻辑判断，调用业务接口
- 编写 `Swagger`注解
- 编写 `@PreAuthorize("hasAuthority('user:basic:delete')")`, 这里 **user:basic:delete**权限权值（url+请求类型组成）。

```java
package cn.yujian95.base.controller;

import cn.yujian95.base.common.api.CommonPage;
import cn.yujian95.base.common.api.CommonResult;
import cn.yujian95.base.dto.param.PowerAccountRegisterParam;
import cn.yujian95.base.dto.param.UserBasicInfoParam;
import cn.yujian95.base.entity.UserBasicInfo;
import cn.yujian95.base.service.IPowerAccountService;
import cn.yujian95.base.service.IUserBasicInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/29
 */

@Api(value = "用户模块", tags = "用户信息接口")
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserBasicInfoController {

    @Resource
    private IUserBasicInfoService basicInfoService;

    @Resource
    private IPowerAccountService powerAccountService;

    @ApiOperation(value = "发送注册短信", notes = "传入 手机号")
    @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", dataType = "String",
            required = true)
    @RequestMapping(value = "/basic/message", method = RequestMethod.GET)
    public CommonResult sendRegisterMessage(@RequestParam String phone) {

        if (StringUtils.isEmpty(phone)) {
            return CommonResult.validateFailed("手机号码不能未空！");
        }

        if (powerAccountService.count(phone)) {
            return CommonResult.validateFailed("该账号，已注册！");
        }

        if (basicInfoService.sendMessage(phone)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation(value = "校验短信验证码", notes = "传入 手机号、短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", dataType = "String",
                    required = true),
            @ApiImplicitParam(name = "code", value = "短信验证码", paramType = "query", dataType = "String",
                    required = true)
    })
    @RequestMapping(value = "/basic/code", method = RequestMethod.POST)
    public CommonResult<Boolean> verifyCode(@RequestParam String phone, @RequestParam String code) {
        return CommonResult.success(basicInfoService.verifyCode(phone, code));
    }

    @ApiOperation(value = "用户账号注册", notes = "传入 注册对象参数（账号名称、密码）")
    @RequestMapping(value = "/basic/account/register", method = RequestMethod.POST)
    public CommonResult registerUserAccount(@RequestBody PowerAccountRegisterParam param) {
        if (powerAccountService.count(param.getName())) {
            return CommonResult.validateFailed("该账号名称已存在！");
        }

        if (powerAccountService.registerAdmin(param)) {
            return CommonResult.success();
        }

        return CommonResult.failed("服务器错误，请联系管理员！");
    }

    @ApiOperation(value = "更新用户基础信息", notes = "传入 用户编号、用户信息参数（姓名、性别、出生日期）")
    @ApiImplicitParam(name = "id", value = "用户编号", paramType = "path", dataType = "Long",
            required = true)
    @RequestMapping(value = "/basic/{id}", method = RequestMethod.PUT)
    public CommonResult updateBasicInfo(@PathVariable Long id, @RequestBody UserBasicInfoParam param) {
        if (!basicInfoService.count(id)) {
            return CommonResult.validateFailed("不存在，该用户编号！");
        }

        if (basicInfoService.update(id, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation(value = "添加用户基础信息", notes = "传入 手机号、用户信息参数（姓名、性别、出生日期）")
    @ApiImplicitParam(name = "phone", value = "手机号", paramType = "path", dataType = "Long",
            required = true)
    @RequestMapping(value = "/basic/{phone}", method = RequestMethod.PUT)
    public CommonResult insertBasicInfo(@PathVariable String phone, @RequestBody UserBasicInfoParam param) {

        if (powerAccountService.count(phone)) {
            return CommonResult.validateFailed("该账号，还未注册！");
        }

        if (basicInfoService.insert(phone, param)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation(value = "删除用户基础信息", notes = "传入 用户编号")
    @ApiImplicitParam(name = "id", value = "用户编号", paramType = "path", dataType = "Long",
            required = true)
    @RequestMapping(value = "/basic/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('user:basic:delete')")
    public CommonResult deleteBasicInfo(@PathVariable Long id) {
        if (!basicInfoService.count(id)) {
            return CommonResult.validateFailed("不存在，该用户编号！");
        }

        if (basicInfoService.delete(id)) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    @ApiOperation(value = "分页：搜索用户信息", notes = "传入 用户姓名、手机号、性别、第几页、页大小")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户姓名", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别", paramType = "query", dataType = "Integer", defaultValue = "0",
                    required = true),
            @ApiImplicitParam(name = "pageNum", value = "第几页", paramType = "query", dataType = "Integer",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", dataType = "Integer",
                    required = true),
    })
    @PreAuthorize("hasAnyAuthority('user:basic:list:get')")
    @RequestMapping(value = "/basic/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserBasicInfo>> searchBasicInfo(@RequestParam(required = false) String name,
                                                                   @RequestParam(required = false) String phone,
                                                                   @RequestParam(defaultValue = "0") Integer sex,
                                                                   @RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        if (sex > 2 || sex < 0) {
            return CommonResult.validateFailed("性别参数错误：" + sex);
        }

        return CommonResult.success(CommonPage.restPage(basicInfoService.list(name, phone, sex, pageNum, pageSize)));
    }
}

```