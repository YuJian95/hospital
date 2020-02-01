package cn.yujian95.hospital.service;

import cn.yujian95.hospital.dto.param.UserBasicInfoParam;
import cn.yujian95.hospital.dto.param.UserRegisterParam;
import cn.yujian95.hospital.entity.UserBasicInfo;
import org.springframework.transaction.annotation.Transactional;

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
     * 发送注册短信
     *
     * @param phone 手机号码
     * @return 是否成功
     */
    boolean sendMessage(String phone);

    /**
     * 校验短信验证码
     *
     * @param phone 手机号
     * @param code  验证码
     * @return 是否正确
     */
    boolean verifyCode(String phone, String code);

    /**
     * 创建普通用户信息
     *
     * @param param 普通用户信息参数
     * @return 是否成功
     */
    boolean insert(UserRegisterParam param);

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
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 用户信息列表
     */
    List<UserBasicInfo> list(String name, String phone, Integer pageNum, Integer pageSize);

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
