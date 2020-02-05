package cn.yujian95.hospital.service.impl;

import cn.yujian95.hospital.dto.param.HospitalSpecialParam;
import cn.yujian95.hospital.entity.HospitalSpecial;
import cn.yujian95.hospital.entity.HospitalSpecialExample;
import cn.yujian95.hospital.entity.HospitalSpecialRelation;
import cn.yujian95.hospital.entity.HospitalSpecialRelationExample;
import cn.yujian95.hospital.mapper.HospitalSpecialMapper;
import cn.yujian95.hospital.mapper.HospitalSpecialRelationMapper;
import cn.yujian95.hospital.service.IHospitalSpecialService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/5
 */
@Service
public class HospitalSpecialServiceImpl implements IHospitalSpecialService {

    @Resource
    private HospitalSpecialMapper specialMapper;

    @Resource
    private HospitalSpecialRelationMapper specialRelationMapper;

    /**
     * 添加专科信息
     *
     * @param param 专科信息参数
     * @return 是否成功
     */
    @Override
    public boolean insert(HospitalSpecialParam param) {
        HospitalSpecial special = new HospitalSpecial();

        BeanUtils.copyProperties(param, special);

        special.setGmtCreate(new Date());
        special.setGmtModified(new Date());

        return specialMapper.insertSelective(special) > 0;
    }

    /**
     * 更新专科信息
     *
     * @param id    专科编号
     * @param param 专科信息参数
     * @return 是否成功
     */
    @Override
    public boolean update(Long id, HospitalSpecialParam param) {
        HospitalSpecial special = new HospitalSpecial();

        BeanUtils.copyProperties(param, special);

        special.setId(id);
        special.setGmtModified(new Date());

        return specialMapper.updateByPrimaryKeySelective(special) > 0;
    }

    /**
     * 删除专科信息
     *
     * @param id 专科编号
     * @return 是否成功
     */
    @Override
    public boolean delete(Long id) {
        return specialMapper.deleteByPrimaryKey(id) > 0;
    }

    /**
     * 获取专科信息
     *
     * @param id 专科编号
     * @return 专科信息
     */
    @Override
    public Optional<HospitalSpecial> getOptional(Long id) {
        return Optional.ofNullable(specialMapper.selectByPrimaryKey(id));
    }

    /**
     * 判断专科信息是否存在
     *
     * @param id 专科信息
     * @return 是否存在
     */
    @Override
    public boolean count(Long id) {
        HospitalSpecialExample example = new HospitalSpecialExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return specialMapper.countByExample(example) > 0;
    }

    /**
     * 判断专科信息是否存在
     *
     * @param name 专科名称
     * @return 是否存在
     */
    @Override
    public boolean count(String name) {
        HospitalSpecialExample example = new HospitalSpecialExample();

        example.createCriteria()
                .andNameEqualTo(name);

        return specialMapper.countByExample(example) > 0;
    }

    /**
     * 查找专科信息
     *
     * @param name     专科名称
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 专科列表
     */
    @Override
    public List<HospitalSpecial> list(String name, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        HospitalSpecialExample example = new HospitalSpecialExample();

        if (!StringUtils.isEmpty(name)) {
            example.createCriteria()
                    .andNameLike("%" + name + "%");
        }

        return specialMapper.selectByExample(example);
    }

    /**
     * 查找医院所有，专科信息列表
     *
     * @param hospitalId 医院编号
     * @return 医院专科列表
     */
    @Override
    public List<HospitalSpecial> list(Long hospitalId) {

        HospitalSpecialRelationExample example = new HospitalSpecialRelationExample();

        example.createCriteria()
                .andHospitalIdEqualTo(hospitalId);

        List<Long> specialIdList = specialRelationMapper.selectByExample(example).stream()
                .map(HospitalSpecialRelation::getSpecialId)
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(specialIdList)) {
            return null;
        }

        HospitalSpecialExample example2 = new HospitalSpecialExample();

        example2.createCriteria()
                .andIdIn(specialIdList);

        return specialMapper.selectByExample(example2);
    }
}
