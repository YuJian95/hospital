package cn.yujian95.little.admin.modules.power.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.yujian95.little.admin.modules.power.bo.AdminUserDetail;
import cn.yujian95.little.admin.modules.power.param.PowerAdminParam;
import cn.yujian95.little.admin.modules.power.param.UpdateAdminPasswordParam;
import cn.yujian95.little.admin.modules.power.service.IPowerAdminRoleRelationService;
import cn.yujian95.little.admin.modules.power.service.IPowerAdminService;
import cn.yujian95.little.common.exception.Asserts;
import cn.yujian95.little.mbg.modules.power.entity.PowerAdmin;
import cn.yujian95.little.mbg.modules.power.entity.PowerAdminRoleRelation;
import cn.yujian95.little.mbg.modules.power.entity.PowerResource;
import cn.yujian95.little.mbg.modules.power.entity.PowerRole;
import cn.yujian95.little.mbg.modules.power.mapper.PowerAdminMapper;
import cn.yujian95.little.mbg.modules.power.mapper.PowerResourceMapper;
import cn.yujian95.little.mbg.modules.power.mapper.PowerRoleMapper;
import cn.yujian95.little.security.util.JwtTokenUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
 * <p>
 * 权限账号 接口实现类
 * </p>
 *
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-14
 */
@Service
public class PowerAdminServiceImpl extends ServiceImpl<PowerAdminMapper, PowerAdmin> implements IPowerAdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PowerAdminServiceImpl.class);

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private IPowerAdminRoleRelationService adminRoleRelationService;

    @Resource
    private PowerRoleMapper roleMapper;

    @Resource
    private PowerResourceMapper resourceMapper;

    /**
     * 判断是否，不存在该记录编号
     *
     * @param id 记录编号
     * @return 是否不存在
     */
    @Override
    public boolean isNotExist(Long id) {

        QueryWrapper<PowerAdmin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PowerAdmin::getId, id);

        return count(wrapper) == 0;
    }

    /**
     * 通过账号名，昵称查找管理员
     *
     * @param keyword  关键词
     * @param pageNum  第几页
     * @param pageSize 页大小
     * @return 管理员列表
     */
    @Override
    public Page<PowerAdmin> search(String keyword, Integer pageNum, Integer pageSize) {
        Page<PowerAdmin> page = new Page<>(pageNum, pageSize);

        QueryWrapper<PowerAdmin> wrapper = new QueryWrapper<>();

        if (StrUtil.isNotEmpty(keyword)) {
            LambdaQueryWrapper<PowerAdmin> lambdaQueryWrapper = wrapper.lambda();
            lambdaQueryWrapper.like(PowerAdmin::getUsername, keyword);
            lambdaQueryWrapper.or().like(PowerAdmin::getNickname, keyword);
        }

        return page(page, wrapper);
    }

    /**
     * 更新用户角色
     *
     * @param adminId    管理员编号
     * @param roleIdList 角色编号列表
     * @return 是否成功
     */
    @Override
    public boolean updateRole(Long adminId, List<Long> roleIdList) {

        if (CollUtil.isEmpty(roleIdList)) {
            return true;
        }

        // 先删除原来的关系
        QueryWrapper<PowerAdminRoleRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PowerAdminRoleRelation::getAdminId, adminId);
        adminRoleRelationService.remove(wrapper);

        // 建立新关系
        if (!CollectionUtils.isEmpty(roleIdList)) {
            List<PowerAdminRoleRelation> list = new ArrayList<>();

            for (Long roleId : roleIdList) {

                PowerAdminRoleRelation roleRelation = new PowerAdminRoleRelation();

                roleRelation.setAdminId(adminId);
                roleRelation.setRoleId(roleId);

                list.add(roleRelation);
            }

            adminRoleRelationService.saveBatch(list);
        }

        return true;
    }

    /**
     * 更新用户密码
     *
     * @param param 更新密码参数
     * @return 是否成功
     */
    @Override
    public boolean updatePassword(UpdateAdminPasswordParam param) {
        QueryWrapper<PowerAdmin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PowerAdmin::getUsername, param.getUsername());
        List<PowerAdmin> adminList = list(wrapper);

        if (CollUtil.isEmpty(adminList)) {
            Asserts.fail("该用户名，不存在！");
        }

        PowerAdmin admin = adminList.get(0);

        if (!passwordEncoder.matches(param.getOldPassword(), admin.getPassword())) {
            Asserts.fail("原密码，不正确！");
        }

        admin.setPassword(passwordEncoder.encode(param.getNewPassword()));
        updateById(admin);

        return true;
    }

    /**
     * 通过管理员编号，获取角色列表
     *
     * @param adminId 管理员编号
     * @return 角色列表
     */
    @Override
    public List<PowerRole> listRole(Long adminId) {
        return roleMapper.listRoleByAdminId(adminId);
    }

    /**
     * 通过管理员编号，获取资源列表
     *
     * @param adminId 管理员编号
     * @return 资源列表
     */
    @Override
    public List<PowerResource> listResource(Long adminId) {
        return resourceMapper.listResourceByAdminId(adminId);
    }

    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    @Override
    public boolean isExistAdmin(String username) {
        QueryWrapper<PowerAdmin> wrapper = new QueryWrapper<>();

        wrapper.lambda().eq(PowerAdmin::getUsername, username);

        return count(wrapper) > 0;
    }

    /**
     * 管理员登录
     *
     * @param username 管理员名称
     * @param password 密码（MD5加密）
     * @return JWT 验证
     */
    @Override
    public String loginAdmin(String username, String password) {

        String token = null;

        try {
            UserDetails userDetails = loadAdminByUsername(username);

            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                Asserts.fail("密码不正确");
            }

            if (!userDetails.isEnabled()) {
                Asserts.fail("帐号已被禁用");
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);

        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }

        return token;
    }

    /**
     * 管理员注册
     *
     * @param param 管理员注册参数
     * @return 是否成功
     */
    @Override
    public boolean registerAdmin(PowerAdminParam param) {

        PowerAdmin admin = new PowerAdmin();

        BeanUtils.copyProperties(param, admin);

        String encodePassword = passwordEncoder.encode(param.getPassword());
        admin.setPassword(encodePassword);
        Date now = new Date();
        admin.setGmtCreate(now);

        return save(admin);
    }

    /**
     * 刷新token的功能
     *
     * @param oldToken 旧的 Token
     * @return 新的 Token
     */
    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    /**
     * 根据用户名，获取后台管理员
     *
     * @param username 用户名称
     * @return 管理员信息
     */
    @Override
    public UserDetails loadAdminByUsername(String username) {

        Optional<PowerAdmin> optional = getAdminByUsername(username);

        if (optional.isPresent()) {
            return new AdminUserDetail(optional.get(), resourceMapper.listResourceByAdminId(optional.get().getId()));
        }

        throw new UsernameNotFoundException("用户名或密码错误！");
    }

    /**
     * 根据用户名，获取后台管理员
     *
     * @param username 用户名称
     * @return 管理员信息
     */
    @Override
    public Optional<PowerAdmin> getAdminByUsername(String username) {

        QueryWrapper<PowerAdmin> wrapper = new QueryWrapper<>();

        wrapper.lambda().eq(PowerAdmin::getUsername, username);
        List<PowerAdmin> adminList = list(wrapper);

        if (CollUtil.isNotEmpty(adminList)) {
            return Optional.ofNullable(adminList.get(0));
        }

        return Optional.empty();
    }
}
