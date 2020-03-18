package cn.yujian95.hospital.service;

import cn.yujian95.hospital.dto.param.HospitalClinicParam;
import cn.yujian95.hospital.entity.HospitalClinic;

import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/12
 */

public interface IHospitalClinicService {

    /**
     * 插入诊室信息
     *
     * @param param 诊室信息参数
     * @return 是否成功
     */
    boolean insert(HospitalClinicParam param);

    /**
     * 更新诊室信息
     *
     * @param id    诊室编号
     * @param param 诊室信息参数
     * @return 是否成功
     */
    boolean update(Long id, HospitalClinicParam param);

    /**
     * 获取诊室名称
     *
     * @param id 诊室编号
     * @return 诊室地址，空则返回，未知
     */
    String getAddress(Long id);

    /**
     * 获取诊室信息
     *
     * @param id 诊室编号
     * @return 诊室信息
     */
    Optional<HospitalClinic> getOptional(Long id);

    /**
     * 删除诊室信息
     *
     * @param id 诊室编号
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 判断诊室是否存在
     *
     * @param id 诊室编号
     * @return 是否存在
     */
    boolean count(Long id);

    /**
     * 获取门诊下属的诊室
     *
     * @param outpatientId 门诊编号
     * @param pageNum      第几页
     * @param pageSize     页大小
     * @return 诊室列表
     */
    List<HospitalClinic> list(Long outpatientId, Integer pageNum, Integer pageSize);
}
