package cn.yujian95.hospital.service.impl;

import cn.yujian95.hospital.common.security.AccountDetails;
import cn.yujian95.hospital.common.security.JwtTokenUtil;
import cn.yujian95.hospital.dto.param.PowerAccountRegisterParam;
import cn.yujian95.hospital.dto.param.PowerAccountStatusParam;
import cn.yujian95.hospital.dto.param.UserRegisterParam;
import cn.yujian95.hospital.entity.*;
import cn.yujian95.hospital.mapper.PowerAccountMapper;
import cn.yujian95.hospital.mapper.PowerAccountRoleRelationMapper;
import cn.yujian95.hospital.mapper.dao.PowerAccountRoleRelationDao;
import cn.yujian95.hospital.service.ILogAccountLoginService;
import cn.yujian95.hospital.service.IPowerAccountService;
import cn.yujian95.hospital.service.IUserBasicInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/20
 */

@Service
public class PowerAccountServiceImpl implements IPowerAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PowerAccountServiceImpl.class);

    /**
     * 一个空格字符
     */
    private static final String BLANK_SPACE = " ";

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PowerAccountMapper accountMapper;

    @Resource
    private PowerAccountRoleRelationDao accountRoleRelationDao;

    @Resource
    private PowerAccountRoleRelationMapper accountRoleRelationMapper;

    @Resource
    private ILogAccountLoginService logAccountLoginService;

    @Resource
    private IUserBasicInfoService userBasicInfoService;

    /**
     * 获取帐号信息
     *
     * @param name 帐号名
     * @return 帐号信息
     */
    @Override
    public Optional<PowerAccount> getByName(String name) {

        PowerAccountExample example = new PowerAccountExample();

        example.createCriteria()
                .andNameEqualTo(name);

        return Optional.ofNullable(accountMapper.selectByExample(example).get(0));
    }

    /**
     * 刷新token
     *
     * @param oldToken 原来的token
     * @return 新token
     */
    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    /**
     * 判断用户名是否已使用
     *
     * @param name 用户名
     * @return 是否存在
     */
    @Override
    public boolean count(String name) {
        PowerAccountExample example = new PowerAccountExample();

        example.createCriteria()
                .andNameEqualTo(name);

        return accountMapper.countByExample(example) > 0;
    }

    /**
     * 判断用户是否存在
     *
     * @param id 用户编号
     * @return 是否存在
     */
    @Override
    public boolean count(Long id) {
        PowerAccountExample example = new PowerAccountExample();

        example.createCriteria()
                .andIdEqualTo(id);

        return accountMapper.countByExample(example) > 0;
    }

    /**
     * 注册普通用户账号
     *
     * @param param 用户账号注册参数（账号，密码）
     * @return 是否成功
     */
    @Override
    public boolean registerUser(UserRegisterParam param) {
        PowerAccount account = new PowerAccount();

        account.setName(param.getPhone());
        account.setPassword(passwordEncoder.encode(param.getPassword()));
        account.setStatus(1);

        account.setGmtCreate(new Date());
        account.setGmtModified(new Date());

        if (accountMapper.insertSelective(account) > 0) {

            if (userBasicInfoService.insert(param)) {
                return true;
            } else {
                // 注册失败删除账号信息
                delete(param.getPhone());
                return false;
            }
        }

        return false;
    }

    /**
     * 管理账号注册功能
     *
     * @param param 账号注册参数（账号，密码）
     * @return 是否成功
     */
    @Override
    public boolean registerAdmin(PowerAccountRegisterParam param) {
        PowerAccount account = new PowerAccount();

        account.setName(param.getName());
        account.setPassword(passwordEncoder.encode(param.getPassword()));
        account.setStatus(1);

        account.setGmtCreate(new Date());
        account.setGmtModified(new Date());

        return accountMapper.insertSelective(account) > 0;
    }

    /**
     * 登录后返回 jwt 字符串
     *
     * @param name     帐号名
     * @param password 密码
     * @return 生成的JWT的token
     */
    @Override
    public Optional<String> login(String name, String password) {

        String jwt = null;

        // 客户端加密后传递账号密码
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(name);

            // 密码不正确
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {

                LOGGER.info("user :{} login fail , wrong password .", name);
                throw new BadCredentialsException("密码不正确");
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenUtil.generateToken(userDetails);

            // 这里需要记录登录时间
            updateLoginTime(name);
            logAccountLoginService.insert(name);

            // 注意中间用空格隔开
            jwt = tokenHead + BLANK_SPACE + token;

        } catch (AuthenticationException e) {
            LOGGER.warn("user :{} , login fail :{}", name, e.getMessage());
        }


        return Optional.ofNullable(jwt);
    }

    /**
     * 检查密码是否正确
     *
     * @param id       账号编号
     * @param password 账号密码
     * @return 是否正确
     */
    @Override
    public boolean checkPassword(Long id, String password) {

        PowerAccount account = accountMapper.selectByPrimaryKey(id);

        return passwordEncoder.matches(password, account.getPassword());
    }

    /**
     * 删除账号
     *
     * @param name 账号名
     * @return 是否成功
     */
    @Override
    public boolean delete(String name) {
        PowerAccountExample example = new PowerAccountExample();

        example.createCriteria()
                .andNameEqualTo(name);

        return accountMapper.deleteByExample(example) > 0;
    }

    /**
     * 更新账号状态
     *
     * @param param 账号编号、账号状态（1：开启，0：关闭）
     * @return 是否成功
     */
    @Override
    public boolean updateStatus(PowerAccountStatusParam param) {
        PowerAccount account = new PowerAccount();

        account.setId(param.getAccountId());
        account.setStatus(param.getStatus());

        account.setPassword(null);
        account.setGmtModified(new Date());

        return accountMapper.updateByPrimaryKeySelective(account) > 0;
    }

    /**
     * 修改用户角色关系
     *
     * @param accountId  帐号id
     * @param roleIdList 角色id列表
     * @return 成功记录
     */
    @Override
    public int updateRole(Long accountId, List<Long> roleIdList) {
        int count = roleIdList == null ? 0 : roleIdList.size();

        //先删除原来的关系
        PowerAccountRoleRelationExample accountRoleRelationExample = new PowerAccountRoleRelationExample();

        accountRoleRelationExample.createCriteria()
                .andAccountIdEqualTo(accountId);

        accountRoleRelationMapper.deleteByExample(accountRoleRelationExample);

        //建立新关系
        if (!CollectionUtils.isEmpty(roleIdList)) {

            List<PowerAccountRoleRelation> list = new ArrayList<>();

            for (Long roleId : roleIdList) {
                PowerAccountRoleRelation roleRelation = new PowerAccountRoleRelation();
                roleRelation.setAccountId(accountId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }

            accountRoleRelationDao.insertList(list);
        }

        return count;
    }

    /**
     * 更新密码
     *
     * @param accountId 帐号编号
     * @param password  密码
     * @return 更新结果
     */
    @Override
    public boolean updatePassword(Long accountId, String password) {
        PowerAccount account = new PowerAccount();

        account.setId(accountId);
        account.setPassword(passwordEncoder.encode(password));
        account.setGmtModified(new Date());

        return accountMapper.updateByPrimaryKeySelective(account) > 0;
    }

    /**
     * 通过用户账号名称，获取用户详情
     *
     * @param userName 用户账号名称
     * @return 用户详情
     */
    @Override
    public UserDetails loadUserByUserName(String userName) {
        Optional<PowerAccount> accountOptional = getByName(userName);

        if (accountOptional.isPresent()) {
            PowerAccount account = accountOptional.get();

            List<PowerResource> resourceList = listResource(account.getId());

            return new AccountDetails(account, resourceList);
        }

        throw new UsernameNotFoundException("用户名或密码错误！");
    }

    /**
     * 通过账号编号，获取资源列表
     *
     * @param accountId 账号编号
     * @return 资源列表
     */
    @Override
    public List<PowerResource> listResource(Long accountId) {
        return accountRoleRelationDao.getResourceList(accountId);
    }

    /**
     * 更新最后登录时间
     *
     * @param name 用户名称
     */
    @Override
    public void updateLoginTime(String name) {
        Optional<PowerAccount> accountOptional = getByName(name);

        // 账号存在
        if (accountOptional.isPresent()) {
            PowerAccount account = accountOptional.get();

            account.setPassword(null);
            account.setLoginTime(new Date());

            accountMapper.updateByPrimaryKeySelective(account);
        }
    }

}
