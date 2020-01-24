package cn.yujian95.base.common.security;

import cn.yujian95.base.entity.PowerAccount;
import cn.yujian95.base.entity.PowerPermission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 账号详情
 *
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/19
 */

public class AccountDetails implements UserDetails {

    /**
     * 账号信息
     */
    private PowerAccount account;

    /**
     * 账号拥有的所有权限
     */
    private List<PowerPermission> permissionList;

    public AccountDetails(PowerAccount account, List<PowerPermission> powerPermissions) {
        this.account = account;
        this.permissionList = powerPermissions;
    }

    /**
     * 获取当前用户所有权限集合
     *
     * @return 当前用户所有权限集合
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        //返回当前用户的权限
        return permissionList.stream()
                // 过滤调权限值为空的情况。
                // 注意权限值为“ "的情况会报错 A granted authority textual representation is required
                // 这是因为 SimpleGrantAuthority（String role）为空。
                .filter(permission -> permission.getValue() != null)
                // 将权限值 转换为 SimpleGrantedAuthority对象
                .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * 获取当前用户密码
     *
     * @return 当前用户密码
     */
    @Override
    public String getPassword() {
        return account.getPassword();
    }

    /**
     * 获取当前用户账号名称
     *
     * @return 当前账号名称
     */
    @Override
    public String getUsername() {
        return account.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /**
     * 获取账号是否可用
     *
     * @return 是否可用
     */
    @Override
    public boolean isEnabled() {
        // status： 1 可用，0 禁用
        return account.getStatus() == 1;
    }
}
