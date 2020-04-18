package cn.yujian95.hospital.service.impl;

import cn.yujian95.hospital.dto.UserMedicalCardDTO;
import cn.yujian95.hospital.dto.param.UserMedicalCardParam;
import cn.yujian95.hospital.dto.param.UserMedicalCardUpdateParam;
import cn.yujian95.hospital.entity.UserMedicalCard;
import cn.yujian95.hospital.entity.UserMedicalCardExample;
import cn.yujian95.hospital.entity.UserMedicalCardRelation;
import cn.yujian95.hospital.entity.UserMedicalCardRelationExample;
import cn.yujian95.hospital.mapper.UserMedicalCardMapper;
import cn.yujian95.hospital.mapper.UserMedicalCardRelationMapper;
import cn.yujian95.hospital.service.IUserMedicalCardService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/2/3
 */

@Service
public class UserMedicalCardServiceImpl implements IUserMedicalCardService {

    private static final int BOY = 1;

    private static final int GIRL = 2;

    @Resource
    private UserMedicalCardMapper medicalCardMapper;

    @Resource
    private UserMedicalCardRelationMapper relationMapper;

    /**
     * 添加医疗卡
     *
     * @param accountId 账号编号
     * @param param     就诊卡参数
     * @return 是否成功
     */
    @Override
    public boolean insert(Long accountId, UserMedicalCardParam param) {

        // 就诊卡，存在
        if (countIdentificationNumber(param.getIdentificationNumber())) {

            Optional<UserMedicalCard> cardOptional = getOptional(param.getIdentificationNumber());

            if (cardOptional.isPresent()) {
                return insertRelation(accountId, param.getType(), cardOptional.get().getId());
            }
        }

        // 就诊卡，不存在
        if (insertCard(param)) {
            // 获取就诊卡信息
            Optional<UserMedicalCard> cardOptional2 = getOptional(param.getIdentificationNumber());

            // 插入关系
            return cardOptional2.filter(userMedicalCard -> insertRelation(accountId, param.getType(), userMedicalCard.getId()))
                    .isPresent();
        }

        return false;
    }

    /**
     * 更新医疗卡（关系类型、姓名、电话）
     *
     * @param relationId 关系编号
     * @param param      就诊卡更新参数
     * @return 是否成功
     */
    @Override
    public boolean update(Long relationId, UserMedicalCardUpdateParam param) {

        int count = 0;

        // 更新关系
        if (param.getType() != null) {
            UserMedicalCardRelation relation = new UserMedicalCardRelation();

            BeanUtils.copyProperties(param, relation);

            relation.setId(relationId);
            relation.setGmtModified(new Date());

            count = relationMapper.updateByPrimaryKeySelective(relation);
        }


        // 更新信息参数
        UserMedicalCard card = new UserMedicalCard();

        BeanUtils.copyProperties(param, card);

        card.setId(relationMapper.selectByPrimaryKey(relationId).getCardId());
        card.setGmtModified(new Date());

        count += medicalCardMapper.updateByPrimaryKeySelective(card);

        return count > 0;
    }

    /**
     * 获取患者名称
     *
     * @param id 就诊卡编号
     * @return 患者名称，或未知
     */
    @Override
    public String getName(Long id) {
        return getOptional(id).map(UserMedicalCard::getName).orElse("未知");
    }

    /**
     * 获取就诊卡信息
     *
     * @param id 就诊卡编号
     * @return 就诊卡信息
     */
    @Override
    public Optional<UserMedicalCard> getOptional(Long id) {

        return Optional.ofNullable(medicalCardMapper.selectByPrimaryKey(id));
    }

    /**
     * 通过身份证号码，获取就诊卡信息
     *
     * @param identificationNumber 身份证号码
     * @return 就诊卡信息
     */
    private Optional<UserMedicalCard> getOptional(String identificationNumber) {

        UserMedicalCardExample example = new UserMedicalCardExample();

        example.createCriteria()
                .andIdentificationNumberEqualTo(identificationNumber);

        return Optional.ofNullable(medicalCardMapper.selectByExample(example).get(0));
    }

    /**
     * 删除医疗卡
     *
     * @param relationId 关系编号
     * @return 是否成功
     */
    @Override
    public boolean delete(Long relationId) {
        return relationMapper.deleteByPrimaryKey(relationId) > 0;
    }

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
    @Override
    public List<UserMedicalCard> list(String name, String phone, Integer gender, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        UserMedicalCardExample example = new UserMedicalCardExample();

        UserMedicalCardExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (!StringUtils.isEmpty(phone)) {
            criteria.andPhoneLike("%" + phone + "%");
        }

        if (gender == BOY || gender == GIRL) {
            criteria.andGenderEqualTo(gender);
        }

        return medicalCardMapper.selectByExample(example);
    }

    /**
     * 根据就诊卡编号
     *
     * @param idList 就诊卡编号
     * @return 用户就诊信息
     */
    @Override
    public List<UserMedicalCard> list(List<Long> idList) {

        UserMedicalCardExample example = new UserMedicalCardExample();

        example.createCriteria()
                .andIdIn(idList);

        return medicalCardMapper.selectByExample(example);
    }

    /**
     * 通过账号编号，获取就诊卡信息
     *
     * @param accountId 账号编号
     * @return 就诊卡列表
     */
    @Override
    public List<UserMedicalCardDTO> list(Long accountId) {

        UserMedicalCardRelationExample example = new UserMedicalCardRelationExample();

        example.createCriteria()
                .andAccountIdEqualTo(accountId);

        return relationMapper.selectByExample(example).stream()
                .map(this::covert)
                .collect(Collectors.toList());

    }

    private UserMedicalCardDTO covert(UserMedicalCardRelation relation) {
        UserMedicalCardDTO dto = new UserMedicalCardDTO();

        // 获取就诊卡信息
        Optional<UserMedicalCard> cardOptional = getOptional(relation.getCardId());

        // 复制就诊卡信息
        cardOptional.ifPresent(card -> BeanUtils.copyProperties(card, dto));

        // 复制关系相关字段
        BeanUtils.copyProperties(relation, dto);
        // 补充卡号
        dto.setId(relation.getCardId());
        dto.setRelationId(relation.getId());

        return dto;
    }

    /**
     * 判断关系编号是否存在
     *
     * @param relationId 关系编号
     * @return 是否存在
     */
    @Override
    public boolean countRelation(Long relationId) {

        UserMedicalCardRelationExample example = new UserMedicalCardRelationExample();

        example.createCriteria()
                .andIdEqualTo(relationId);


        return relationMapper.countByExample(example) > 0;
    }

    /**
     * 统计用户绑定的就诊卡数量
     *
     * @param accountId 账号编号
     * @return 就诊卡数量
     */
    @Override
    public long count(Long accountId) {
        UserMedicalCardRelationExample example = new UserMedicalCardRelationExample();

        example.createCriteria()
                .andAccountIdEqualTo(accountId);


        return relationMapper.countByExample(example);
    }

    /**
     * 判断就诊卡信息是否存在
     *
     * @param identificationNumber 身份证编号
     * @return 是否存在
     */
    @Override
    public boolean countIdentificationNumber(String identificationNumber) {
        UserMedicalCardExample example = new UserMedicalCardExample();

        example.createCriteria()
                .andIdentificationNumberEqualTo(identificationNumber);

        return medicalCardMapper.countByExample(example) > 0;
    }

    /**
     * 判断就诊卡号是否存在
     *
     * @param cardId 就诊卡号
     * @return 是否存在
     */
    @Override
    public boolean countCardId(Long cardId) {
        UserMedicalCardExample example = new UserMedicalCardExample();

        example.createCriteria()
                .andIdEqualTo(cardId);

        return medicalCardMapper.countByExample(example) > 0;
    }

    /**
     * 插入就诊卡关系
     *
     * @param accountId 用户账号编号
     * @param type      关系类型 0：本人，1：父母，2：兄弟/姐妹，3：伴侣，4：子女，5：同事/朋友，6：其他
     * @param cardId    就诊卡
     * @return 是否成功
     */
    private boolean insertRelation(Long accountId, Integer type, Long cardId) {
        UserMedicalCardRelation relation = new UserMedicalCardRelation();

        relation.setCardId(cardId);
        relation.setType(type);
        relation.setAccountId(accountId);

        relation.setGmtCreate(new Date());
        relation.setGmtModified(new Date());

        return relationMapper.insertSelective(relation) > 0;
    }

    /**
     * 添加医疗卡信息
     *
     * @param param 就诊卡参数
     * @return 是否成功
     */
    private boolean insertCard(UserMedicalCardParam param) {
        UserMedicalCard card = new UserMedicalCard();

        BeanUtils.copyProperties(param, card);

        card.setGmtCreate(new Date());
        card.setGmtModified(new Date());

        return medicalCardMapper.insertSelective(card) > 0;
    }
}
