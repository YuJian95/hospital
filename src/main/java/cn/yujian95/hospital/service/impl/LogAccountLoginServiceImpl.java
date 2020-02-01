package cn.yujian95.hospital.service.impl;

import cn.yujian95.hospital.entity.LogAccountLogin;
import cn.yujian95.hospital.entity.LogAccountLoginExample;
import cn.yujian95.hospital.entity.PowerAccount;
import cn.yujian95.hospital.mapper.LogAccountLoginMapper;
import cn.yujian95.hospital.service.ILogAccountLoginService;
import cn.yujian95.hospital.service.IPowerAccountService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/21
 */

@Service
public class LogAccountLoginServiceImpl implements ILogAccountLoginService {

    @Resource
    private IPowerAccountService powerAccountService;

    @Resource
    private LogAccountLoginMapper accountLoginMapper;

    /**
     * 插入登录记录
     *
     * @param name 登录账号
     * @return 是否成功
     */
    @Override
    public boolean insert(String name) {
        Optional<PowerAccount> accountOptional = powerAccountService.getByName(name);

        if (accountOptional.isPresent()) {
            PowerAccount account = accountOptional.get();

            LogAccountLogin accountLogin = new LogAccountLogin();

            accountLogin.setAccountId(account.getId());
            accountLogin.setAccountName(account.getName());

            // 设置 ip地址
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request;

            if (attributes != null) {
                request = attributes.getRequest();
                accountLogin.setIpAddress(request.getRemoteAddr());
            }

            Date date = new Date();
            accountLogin.setGmtCreate(date);
            accountLogin.setGmtModified(date);

            return accountLoginMapper.insertSelective(accountLogin) > 0;
        }

        return false;
    }

    /**
     * 查找登录记录
     *
     * @param accountName 账号名称
     * @param pageNum     第几页
     * @param pageSize    页大小
     * @return 登录记录
     */
    @Override
    public List<LogAccountLogin> search(String accountName, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        LogAccountLoginExample example = new LogAccountLoginExample();

        // 从近到远
        example.setOrderByClause("gmt_create desc");

        LogAccountLoginExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(accountName)) {
            criteria.andAccountNameEqualTo(accountName);
        }

        return accountLoginMapper.selectByExample(example);
    }
}
