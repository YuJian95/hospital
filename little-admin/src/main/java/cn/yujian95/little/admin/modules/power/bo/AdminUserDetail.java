package cn.yujian95.little.admin.modules.power.bo;

import cn.yujian95.little.mbg.modules.power.entity.PowerAdmin;
import cn.yujian95.little.mbg.modules.power.entity.PowerResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/9/30
 */

public class AdminUserDetail implements UserDetails {
    private PowerAdmin powerAdmin;
    private List<PowerResource> resourceList;

    /**
     * 生成用户详情
     *
     * @param powerAdmin   权限用户
     * @param resourceList 资源列表
     */
    public AdminUserDetail(PowerAdmin powerAdmin, List<PowerResource> resourceList) {
        this.powerAdmin = powerAdmin;
        this.resourceList = resourceList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 返回当前用户的角色
        return resourceList.stream()
                .map(role -> new SimpleGrantedAuthority(role.getId() + ":" + role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return powerAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return powerAdmin.getUsername();
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
        return powerAdmin.getStatus().equals(1);
    }
}
