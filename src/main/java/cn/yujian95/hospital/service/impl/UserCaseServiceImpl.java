package cn.yujian95.hospital.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.yujian95.hospital.dto.param.UserCaseParam;
import cn.yujian95.hospital.dto.param.UserCaseUpdateParam;
import cn.yujian95.hospital.entity.UserCase;
import cn.yujian95.hospital.entity.UserCaseExample;
import cn.yujian95.hospital.mapper.UserCaseMapper;
import cn.yujian95.hospital.service.IUserCaseService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/2
 */

@Service
public class UserCaseServiceImpl implements IUserCaseService {

    @Resource
    private UserCaseMapper caseMapper;

    /**
     * 创建病例信息
     *
     * @param param 病例参数
     * @return 是否成功
     */
    @Override
    public boolean insert(UserCaseParam param) {
        UserCase userCase = new UserCase();

        BeanUtils.copyProperties(param, userCase);

        userCase.setGmtCreate(new Date());
        userCase.setGmtModified(new Date());

        return caseMapper.insertSelective(userCase) > 0;
    }

    /**
     * 更新病例信息
     *
     * @param id    病例参数
     * @param param 病例参数
     * @return 是否成功
     */
    @Override
    public boolean update(Long id, UserCaseUpdateParam param) {
        UserCase userCase = new UserCase();

        BeanUtils.copyProperties(param, userCase);

        userCase.setId(id);
        userCase.setGmtModified(new Date());

        return caseMapper.updateByPrimaryKeySelective(userCase) > 0;
    }

    /**
     * 删除病例信息
     *
     * @param id 记录编号
     * @return 是否成功
     */
    @Override
    public boolean delete(Long id) {
        return caseMapper.deleteByPrimaryKey(id) > 0;
    }

    /**
     * 判断病例信息，是否存在
     *
     * @param id 记录编号
     * @return 是否存在
     */
    @Override
    public boolean count(Long id) {

        UserCaseExample example = new UserCaseExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return caseMapper.countByExample(example) > 0;
    }

    /**
     * 获取预约记录相关病例
     *
     * @param appointmentId 预约记录编号
     * @return 病例列表
     */
    @Override
    public List<UserCase> listByAppointment(Long appointmentId) {
        UserCaseExample example = new UserCaseExample();

        example.setOrderByClause("gmt_create desc");

        example.createCriteria()
                .andAppointmentIdEqualTo(appointmentId);

        return caseMapper.selectByExample(example);
    }

    /**
     * 获取病例信息
     *
     * @param cardId        就诊卡编号
     * @param appointmentId 预约记录
     * @return 是否存在
     */
    @Override
    public UserCase get(Long cardId, Long appointmentId) {
        UserCaseExample example = new UserCaseExample();

        example.setOrderByClause("gmt_create desc");

        example.createCriteria()
                .andCardIdEqualTo(cardId)
                .andAppointmentIdEqualTo(appointmentId);

        List<UserCase> list = caseMapper.selectByExample(example);

        if (CollUtil.isEmpty(list)) {
            return null;
        }

        return list.get(0);
    }

    /**
     * 获取病例列表
     *
     * @param cardId   就诊卡编号
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 病例列表
     */
    @Override
    public List<UserCase> list(Long cardId, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        UserCaseExample example = new UserCaseExample();

        example.createCriteria()
                .andCardIdEqualTo(cardId);

        return caseMapper.selectByExample(example);
    }
}
