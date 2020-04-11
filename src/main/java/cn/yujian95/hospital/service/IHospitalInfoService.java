package cn.yujian95.hospital.service;

import cn.yujian95.hospital.dto.param.HospitalInfoParam;
import cn.yujian95.hospital.dto.param.HospitalOutpatientRelationParam;
import cn.yujian95.hospital.dto.param.HospitalSpecialRelationParam;
import cn.yujian95.hospital.entity.HospitalInfo;
import cn.yujian95.hospital.entity.HospitalOutpatient;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/3
 */

public interface IHospitalInfoService {

    /**
     * 添加医院信息
     *
     * @param param 医院信息参数
     * @return 是否成功
     */
    boolean insert(HospitalInfoParam param);

    /**
     * 更新医院信息
     *
     * @param id    医院编号
     * @param param 医院信息参数
     * @return 是否成功
     */
    boolean update(Long id, HospitalInfoParam param);

    /**
     * 获取医院信息
     *
     * @param id 医院编号
     * @return 医院信息
     */
    Optional<HospitalInfo> getOptional(Long id);

    /**
     * 获取医院名称
     *
     * @param id 医院编号
     * @return 医院名称，否则返回未知
     */
    String getName(Long id);

    /**
     * 删除医院信息
     *
     * @param id 医院编号
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 判断医院信息是否存在
     *
     * @param id 医院编号
     * @return 是否存在
     */
    boolean count(Long id);

    /**
     * 判断电话是否存在
     *
     * @param phone 电话
     * @return 是否存在
     */
    boolean count(String phone);

    /**
     * 查找医院列表
     *
     * @param name     医院名称
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 医院列表
     */
    List<HospitalInfo> list(String name, Integer pageNum, Integer pageSize);

    /**
     * 插入专科到医院中去
     *
     * @param param 医院专科关系参数
     * @return 是否成功
     */
    boolean insertSpecialRelation(HospitalSpecialRelationParam param);

    /**
     * 插入门诊到医院中去
     *
     * @param param 医院门诊关系参数
     * @return 是否成功
     */
    boolean insertOutpatientRelation(HospitalOutpatientRelationParam param);

    /**
     * 删除从医院中移除门诊
     *
     * @param hospitalId   医院编号
     * @param outpatientId 门诊编号
     * @return 是否成功
     */
    boolean deleteOutpatientRelation(Long hospitalId, Long outpatientId);

    /**
     * 删除从医院中移除专科
     *
     * @param hospitalId 医院编号
     * @param specialId  专科编号
     * @return 是否成功
     */
    boolean deleteSpecialRelation(Long hospitalId, Long specialId);

    /**
     * 判断关系是否存在
     *
     * @param id 关系编号
     * @return 是否存在
     */
    boolean countSpecialRelation(Long id);

    /**
     * 判断关系是否存在
     *
     * @param id 关系编号
     * @return 是否存在
     */
    boolean countOutpatientRelation(Long id);

    /**
     * 判断医院是否存在该专科
     *
     * @param param 医院专科关系参数
     * @return 是否存在
     */
    boolean countSpecialRelation(HospitalSpecialRelationParam param);

    /**
     * 判断医院是否存在该门诊
     *
     * @param param 医院门诊关系参数
     * @return 是否存在
     */
    boolean countOutpatientRelation(HospitalOutpatientRelationParam param);
}
