package cn.yujian95.hospital.service;

import cn.yujian95.hospital.dto.param.UserCaseParam;
import cn.yujian95.hospital.dto.param.UserCaseUpdateParam;
import cn.yujian95.hospital.entity.UserCase;

import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/2
 */

public interface IUserCaseService {

    /**
     * 创建病例信息
     *
     * @param param 病例参数
     * @return 是否成功
     */
    boolean insert(UserCaseParam param);

    /**
     * 更新病例信息
     *
     * @param id    病例参数
     * @param param 病例参数
     * @return 是否成功
     */
    boolean update(Long id, UserCaseUpdateParam param);

    /**
     * 删除病例信息
     *
     * @param id 记录编号
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 判断病例信息，是否存在
     *
     * @param id 记录编号
     * @return 是否存在
     */
    boolean count(Long id);

    /**
     * 获取病例信息
     *
     * @param accountId 账号编号
     * @param orderId   预约记录
     * @return 是否存在
     */
    Optional<UserCase> getOptional(Long accountId, Long orderId);

    /**
     * 获取病例列表
     *
     * @param accountId 账号编号
     * @param pageNum   第几页
     * @param pageSize  页大小
     * @return 病例列表
     */
    List<UserCase> list(Long accountId, Integer pageNum, Integer pageSize);
}
