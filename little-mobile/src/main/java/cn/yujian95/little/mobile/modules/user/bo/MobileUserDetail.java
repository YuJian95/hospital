package cn.yujian95.little.mobile.modules.user.bo;

import cn.yujian95.little.mbg.modules.user.entity.UserInfo;
import cn.yujian95.little.mbg.modules.power.entity.PowerResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/9/30
 */

public class MobileUserDetail implements UserDetails {

    private UserInfo userInfo;

    /**
     * 生成用户详情
     *
     * @param userInfo 权限用户
     */
    public MobileUserDetail(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userInfo.getStatus().equals(1);
    }
}
