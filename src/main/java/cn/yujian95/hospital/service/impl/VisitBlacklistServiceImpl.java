package cn.yujian95.hospital.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.yujian95.hospital.entity.VisitBlacklist;
import cn.yujian95.hospital.entity.VisitBlacklistExample;
import cn.yujian95.hospital.mapper.VisitBlacklistMapper;
import cn.yujian95.hospital.service.IVisitBlacklistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/19
 */

@Service
public class VisitBlacklistServiceImpl implements IVisitBlacklistService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VisitBlacklistServiceImpl.class);

    private static final int LOCK = 1;
    private static final int UNLOCK = 2;

    @Resource
    private VisitBlacklistMapper blacklistMapper;


    /**
     * 插入黑名单
     *
     * @param cardId 就诊卡号
     * @return 是否成功
     */
    @Override
    public boolean insert(Long cardId) {

        VisitBlacklist blacklist = new VisitBlacklist();

        blacklist.setCardId(cardId);
        blacklist.setStatus(LOCK);
        blacklist.setGmtCreate(new Date());
        blacklist.setGmtModified(new Date());

        return blacklistMapper.insertSelective(blacklist) > 0;
    }

    /**
     * 判断是否被禁用
     *
     * @param cardId 就诊卡号
     * @return 是否禁用
     */
    @Override
    public boolean isForbid(Long cardId) {

        VisitBlacklistExample example = new VisitBlacklistExample();

        example.createCriteria()
                .andStatusEqualTo(LOCK)
                .andCardIdEqualTo(cardId);

        return blacklistMapper.countByExample(example) > 0;
    }

    /**
     * 自动解封，到期的用户
     */
    @Override
    public void autoUnlock() {
        LOGGER.info("auto unlock blacklist start.");

        // 筛选一个月前的记录
        VisitBlacklistExample example = new VisitBlacklistExample();

        example.createCriteria()
                // 上个月
                .andGmtCreateLessThanOrEqualTo(DateUtil.lastMonth())
                .andStatusEqualTo(LOCK);

        VisitBlacklist blacklist = new VisitBlacklist();

        blacklist.setStatus(UNLOCK);
        blacklist.setGmtModified(new Date());

        int result = blacklistMapper.updateByExampleSelective(blacklist, example);

        LOGGER.info("auto unlock blacklist end, update: {} records.", result);
    }
}
