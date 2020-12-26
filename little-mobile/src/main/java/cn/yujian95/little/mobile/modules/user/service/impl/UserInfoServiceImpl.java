package cn.yujian95.little.mobile.modules.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.yujian95.little.common.exception.Asserts;
import cn.yujian95.little.mbg.modules.user.entity.UserInfo;
import cn.yujian95.little.mbg.modules.user.mapper.UserInfoMapper;
import cn.yujian95.little.mobile.modules.user.bo.MobileUserDetail;
import cn.yujian95.little.mobile.modules.user.param.UpdateUserPasswordParam;
import cn.yujian95.little.mobile.modules.user.param.UserRegisterParam;
import cn.yujian95.little.mobile.modules.user.service.IUserInfoService;
import cn.yujian95.little.security.util.JwtTokenUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private PasswordEncoder passwordEncoder;

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
     * 根据用户名，获取用户信息
     *
     * @param username 用户名称
     * @return 用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserInfo> optional = getUserByUsername(username);

        if (optional.isPresent()) {
            return new MobileUserDetail(optional.get());
        }

        throw new UsernameNotFoundException("用户名或密码错误！");
    }

    /**
     * 刷新token的功能
     *
     * @param oldToken 旧的 Token
     * @return 新的 Token
     */
    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
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
     * 用户登录
     *
     * @param username 用户名称
     * @param password 密码（MD5加密）
     * @return JWT 验证
     */
    @Override
    public String loginUser(String username, String password) {
        String token = null;

        try {
            UserDetails userDetails = loadUserByUsername(username);

            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                Asserts.fail("密码不正确");
            }

            if (!userDetails.isEnabled()) {
                Asserts.fail("帐号已被禁用");
            }

            UsernamePasswordAuthenticationToken authentication
                    = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);

        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }

        return token;
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

    /**
     * 根据用户名，获取用户信息
     *
     * @param username 用户名称
     * @return 用户信息
     */
    @Override
    public Optional<UserInfo> getUserByUsername(String username) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();

        wrapper.lambda().eq(UserInfo::getUsername, username);
        List<UserInfo> userList = list(wrapper);

        if (CollUtil.isNotEmpty(userList)) {
            return Optional.ofNullable(userList.get(0));
        }

        return Optional.empty();
    }

    /**
     * 更新用户密码
     *
     * @param param 更新密码参数
     * @return 是否成功
     */
    @Override
    public boolean updatePassword(UpdateUserPasswordParam param) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserInfo::getUsername, param.getUsername());
        List<UserInfo> userList = list(wrapper);

        if (CollUtil.isEmpty(userList)) {
            Asserts.fail("该用户名，不存在！");
        }

        UserInfo user = userList.get(0);

        if (!passwordEncoder.matches(param.getOldPassword(), user.getPassword())) {
            Asserts.fail("原密码，不正确！");
        }

        user.setPassword(passwordEncoder.encode(param.getNewPassword()));
        updateById(user);

        return true;
    }
}
