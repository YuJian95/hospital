package cn.yujian95.hospital.service;

import cn.yujian95.hospital.dto.param.HospitalInfoParam;
import cn.yujian95.hospital.entity.HospitalInfo;

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
}
