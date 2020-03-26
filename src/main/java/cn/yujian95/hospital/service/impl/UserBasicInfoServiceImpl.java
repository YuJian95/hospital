package cn.yujian95.hospital.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.yujian95.hospital.component.AliSendSmsComponent;
import cn.yujian95.hospital.dto.param.UserBasicInfoParam;
import cn.yujian95.hospital.dto.param.UserRegisterParam;
import cn.yujian95.hospital.entity.UserBasicInfo;
import cn.yujian95.hospital.entity.UserBasicInfoExample;
import cn.yujian95.hospital.mapper.UserBasicInfoMapper;
import cn.yujian95.hospital.service.IRedisService;
import cn.yujian95.hospital.service.IUserBasicInfoService;
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

        basicInfo.setId(id);
        basicInfo.setGmtModified(new Date());

        return basicInfoMapper.updateByPrimaryKeySelective(basicInfo) > 0;
    }

    /**
     * 创建普通用户信息
     *
     * @param param 普通用户信息参数
     * @return 是否成功
     */
    @Override
    public boolean insert(UserRegisterParam param) {

        UserBasicInfo info = new UserBasicInfo();

        BeanUtils.copyProperties(param, info);

        info.setGmtCreate(new Date());
        info.setGmtModified(new Date());

        return basicInfoMapper.insertSelective(info) > 0;
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
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 用户信息列表
     */
    @Override
    public List<UserBasicInfo> list(String name, String phone, Integer pageNum, Integer pageSize) {
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
