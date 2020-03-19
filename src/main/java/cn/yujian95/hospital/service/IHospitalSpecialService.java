package cn.yujian95.hospital.service;

import cn.yujian95.hospital.dto.HospitalSpecialOutpatientDTO;
import cn.yujian95.hospital.dto.param.HospitalSpecialParam;
import cn.yujian95.hospital.entity.HospitalOutpatient;
import cn.yujian95.hospital.entity.HospitalSpecial;

import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/5
 */

public interface IHospitalSpecialService {

    /**
     * 添加专科信息
     *
     * @param param 专科信息参数
     * @return 是否成功
     */
    boolean insert(HospitalSpecialParam param);

    /**
     * 更新专科信息
     *
     * @param id    专科编号
     * @param param 专科信息参数
     * @return 是否成功
     */
    boolean update(Long id, HospitalSpecialParam param);

    /**
     * 删除专科信息
     *
     * @param id 专科编号
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 获取专科信息
     *
     * @param id 专科编号
     * @return 专科信息
     */
    Optional<HospitalSpecial> getOptional(Long id);

    /**
     * 获取专科名称
     *
     * @param id 专科编号
     * @return 专科名称
     */
    String getName(Long id);

    /**
     * 判断专科信息是否存在
     *
     * @param id 专科信息
     * @return 是否存在
     */
    boolean count(Long id);

    /**
     * 判断专科信息是否存在
     *
     * @param name 专科名称
     * @return 是否存在
     */
    boolean count(String name);

//    /**
//     * 查找医院所有，专科信息列表
//     *
//     * @param hospitalId 医院编号
//     * @return 医院专科列表
//     */
//    List<HospitalSpecialOutpatientDTO> list(Long hospitalId);


    /**
     * 通过专科编号，查找医院门诊列表
     *
     * @param specialId 专科编号
     * @param pageNum   第几页
     * @param pageSize  页大小
     * @return 门诊列表
     */
    List<HospitalOutpatient> listOutpatient(Long specialId, Integer pageNum, Integer pageSize);

    /**
     * 查找专科信息
     *
     * @param name     专科名称
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 专科列表
     */
    List<HospitalSpecial> list(String name, Integer pageNum, Integer pageSize);

    /**
     * 查找医院，所属专科信息
     *
     * @param hospitalId 医院编号
     * @param pageNum    第几页
     * @param pageSize   页大小
     * @return 专科列表
     */
    List<HospitalSpecial> list(Long hospitalId, Integer pageNum, Integer pageSize);
}
