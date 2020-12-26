package cn.yujian95.little.admin.modules.power.service;

import cn.yujian95.little.admin.modules.power.param.PowerAdminParam;
import cn.yujian95.little.admin.modules.power.param.UpdateAdminPasswordParam;
import cn.yujian95.little.mbg.modules.power.entity.PowerAdmin;
import cn.yujian95.little.mbg.modules.power.entity.PowerResource;
import cn.yujian95.little.mbg.modules.power.entity.PowerRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 权限账号 接口类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
public interface IPowerAdminService extends IService<PowerAdmin> {

    /**
     * 判断是否，不存在该记录编号
     *
     * @param id 记录编号
     * @return 是否不存在
     */
    boolean isNotExist(Long id);

    /**
     * 根据用户名，获取后台管理员
     *
     * @param username 用户名称
     * @return 管理员信息
     */
    Optional<PowerAdmin> getAdminByUsername(String username);

    /**
     * 根据用户名，获取后台管理员
     *
     * @param username 用户名称
     * @return 管理员信息
     */
    UserDetails loadAdminByUsername(String username);

    /**
     * 刷新token的功能
     *
     * @param oldToken 旧的 Token
     * @return 新的 Token
     */
    String refreshToken(String oldToken);

    /**
     * 管理员注册
     *
     * @param param 管理员注册参数
     * @return 是否成功
     */
    boolean registerAdmin(PowerAdminParam param);

    /**
     * 管理员登录
     *
     * @param username 管理员名称
     * @param password 密码（MD5加密）
     * @return JWT 验证
     */
    String loginAdmin(String username, String password);

    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean isExistAdmin(String username);

    /**
     * 通过账号名，昵称查找管理员
     *
     * @param keyword  关键词
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 管理员列表
     */
    Page<PowerAdmin> search(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 更新用户角色
     *
     * @param adminId    管理员编号
     * @param roleIdList 角色编号列表
     * @return 是否成功
     */
    boolean updateRole(Long adminId, List<Long> roleIdList);

    /**
     * 更新用户密码
     *
     * @param param 更新密码参数
     * @return 是否成功
     */
    boolean updatePassword(UpdateAdminPasswordParam param);

    /**
     * 通过管理员编号，获取角色列表
     *
     * @param adminId 管理员编号
     * @return 角色列表
     */
    List<PowerRole> listRole(Long adminId);

    /**
     * 通过管理员编号，获取资源列表
     *
     * @param adminId 管理员编号
     * @return 资源列表
     */
    List<PowerResource> listResource(Long adminId);
}
