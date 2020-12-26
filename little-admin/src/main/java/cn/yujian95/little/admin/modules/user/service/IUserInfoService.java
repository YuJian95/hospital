package cn.yujian95.little.admin.modules.user.service;

import cn.yujian95.little.admin.modules.user.param.UserRegisterParam;
import cn.yujian95.little.mbg.modules.user.entity.UserInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * 通过用户名/昵称、手机号，获取用户信息
     *
     * @param nameKeyword 昵称/用户名关键词
     * @param phone       手机号
     * @param pageNum     第几页
     * @param pageSize    页大小
     * @return 用户列表
     */
    Page<UserInfo> search(String nameKeyword, String phone, Integer pageNum, Integer pageSize);

    /**
     * 修改用户状态
     *
     * @param id     用户编号
     * @param status 状态 0：禁用，1：启用
     * @return 是否成功
     */
    boolean updateStatus(Long id, Integer status);

    /**
     * 用户注册
     *
     * @param param 用户注册参数
     * @return 是否成功
     */
    boolean registerUser(UserRegisterParam param);

    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean isExistUser(String username);
}
