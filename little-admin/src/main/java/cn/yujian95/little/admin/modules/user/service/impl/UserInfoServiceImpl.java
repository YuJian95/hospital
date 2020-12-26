package cn.yujian95.little.admin.modules.user.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.yujian95.little.admin.modules.user.param.UserRegisterParam;
import cn.yujian95.little.admin.modules.user.service.IUserInfoService;
import cn.yujian95.little.mbg.modules.user.entity.UserInfo;
import cn.yujian95.little.mbg.modules.user.mapper.UserInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 用户信息  接口实现类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-20
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 修改用户状态
     *
     * @param id     用户编号
     * @param status 状态 0：禁用，1：启用
     * @return 是否成功
     */
    @Override
    public boolean updateStatus(Long id, Integer status) {
        UserInfo info = new UserInfo();

        info.setId(id);
        info.setStatus(status);
        info.setGmtModified(new Date());

        return updateById(info);
    }

    /**
     * 用户注册
     *
     * @param param 用户注册参数
     * @return 是否成功
     */
    @Override
    public boolean registerUser(UserRegisterParam param) {
        UserInfo user = new UserInfo();

        BeanUtils.copyProperties(param, user);

        String encodePassword = passwordEncoder.encode(param.getPassword());
        user.setPassword(encodePassword);
        Date now = new Date();
        user.setGmtCreate(now);

        return save(user);
    }

    /**
     * 通过用户名/昵称、手机号，获取用户信息
     *
     * @param nameKeyword 昵称/用户名关键词
     * @param phone       手机号
     * @param pageNum     第几页
     * @param pageSize    页大小
     * @return 用户列表
     */
    @Override
    public Page<UserInfo> search(String nameKeyword, String phone, Integer pageNum, Integer pageSize) {

        Page<UserInfo> page = new Page<>(pageNum, pageSize);

        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");

        if (StrUtil.isNotEmpty(nameKeyword)) {
            LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = wrapper.lambda();

            lambdaQueryWrapper.like(UserInfo::getUsername, nameKeyword);
            lambdaQueryWrapper.or().like(UserInfo::getNickname, nameKeyword);
        }

        if (StrUtil.isNotEmpty(phone)) {
            LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = wrapper.lambda();
            lambdaQueryWrapper.like(UserInfo::getPhone, phone);
        }

        return page(page, wrapper);
    }

    /**
     * 判断是否，不存在该记录编号
     *
     * @param id 记录编号
     * @return 是否不存在
     */
    @Override
    public boolean isNotExist(Long id) {

        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserInfo::getId, id);

        return count(wrapper) == 0;
    }

    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    @Override
    public boolean isExistUser(String username) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();

        wrapper.lambda().eq(UserInfo::getUsername, username);

        return count(wrapper) > 0;
    }
}
