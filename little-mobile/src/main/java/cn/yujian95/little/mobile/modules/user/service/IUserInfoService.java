package cn.yujian95.little.mobile.modules.user.service;

import cn.yujian95.little.mbg.modules.user.entity.UserInfo;
import cn.yujian95.little.mobile.modules.user.param.UpdateUserPasswordParam;
import cn.yujian95.little.mobile.modules.user.param.UserRegisterParam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * <p>
 * 用户信息  接口类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-20
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 判断是否，不存在该记录编号
     *
     * @param id 记录编号
     * @return 是否不存在
     */
    boolean isNotExist(Long id);

    /**
     * 根据用户名，获取用户信息
     *
     * @param username 用户名称
     * @return 用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 刷新token的功能
     *
     * @param oldToken 旧的 Token
     * @return 新的 Token
     */
    String refreshToken(String oldToken);

    /**
     * 用户注册
     *
     * @param param 用户注册参数
     * @return 是否成功
     */
    boolean registerUser(UserRegisterParam param);

    /**
     * 用户登录
     *
     * @param username 用户名称
     * @param password 密码（MD5加密）
     * @return JWT 验证
     */
    String loginUser(String username, String password);

    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean isExistUser(String username);

    /**
     * 更新用户密码
     *
     * @param param 更新密码参数
     * @return 是否成功
     */
    boolean updatePassword(UpdateUserPasswordParam param);

    /**
     * 根据用户名，获取用户信息
     *
     * @param username 用户名称
     * @return 用户信息
     */
    Optional<UserInfo> getUserByUsername(String username);
}
