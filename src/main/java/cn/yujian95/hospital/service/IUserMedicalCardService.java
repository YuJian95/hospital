package cn.yujian95.hospital.service;

import cn.yujian95.hospital.dto.UserMedicalCardDTO;
import cn.yujian95.hospital.dto.param.UserMedicalCardParam;
import cn.yujian95.hospital.dto.param.UserMedicalCardUpdateParam;
import cn.yujian95.hospital.entity.UserMedicalCard;

import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/3
 */

public interface IUserMedicalCardService {
    /**
     * 添加医疗卡
     *
     * @param accountId 账号编号
     * @param param     就诊卡参数
     * @return 是否成功
     */
    boolean insert(Long accountId, UserMedicalCardParam param);

    /**
     * 更新医疗卡（关系类型、姓名、电话）
     *
     * @param relationId 关系编号
     * @param param      就诊卡更新参数
     * @return 是否成功
     */
    boolean update(Long relationId, UserMedicalCardUpdateParam param);

    /**
     * 删除医疗卡
     *
     * @param relationId 关系编号
     * @return 是否成功
     */
    boolean delete(Long relationId);

    /**
     * 获取就诊卡信息
     *
     * @param id 就诊卡编号
     * @return 就诊卡信息
     */
    Optional<UserMedicalCard> getOptional(Long id);

    /**
     * 获取患者名称
     *
     * @param id 就诊卡编号
     * @return 患者名称，或未知
     */
    String getName(Long id);

    /**
     * 查找就诊卡信息
     *
     * @param name     姓名
     * @param phone    手机号
     * @param gender   性别（ 0 所有，1 男，2 女）
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 就诊卡列表
     */
    List<UserMedicalCard> list(String name, String phone, Integer gender, Integer pageNum, Integer pageSize);

    /**
     * 通过账号编号，获取就诊卡信息
     *
     * @param accountId 账号编号
     * @return 就诊卡列表
     */
    List<UserMedicalCardDTO> list(Long accountId);

    /**
     * 根据就诊卡编号
     *
     * @param idList 就诊卡编号
     * @return 用户就诊信息
     */
    List<UserMedicalCard> list(List<Long> idList);

    /**
     * 判断关系编号是否存在
     *
     * @param relationId 关系编号
     * @return 是否存在
     */
    boolean countRelation(Long relationId);

    /**
     * 判断就诊卡号是否存在
     *
     * @param cardId 就诊卡号
     * @return 是否存在
     */
    boolean countCardId(Long cardId);

    /**
     * 判断就诊卡信息是否存在
     *
     * @param identificationNumber 身份证编号
     * @return 是否存在
     */
    boolean countIdentificationNumber(String identificationNumber);

    /**
     * 统计用户绑定的就诊卡数量
     *
     * @param accountId 账号编号
     * @return 就诊卡数量
     */
    long count(Long accountId);
}
