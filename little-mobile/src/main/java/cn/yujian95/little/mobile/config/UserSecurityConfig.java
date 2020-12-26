package cn.yujian95.little.mobile.config;

import cn.yujian95.little.mobile.modules.user.service.IUserInfoService;
import cn.yujian95.little.security.component.IDynamicSecurityService;
import cn.yujian95.little.security.config.SecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yujian95 clj9509@163.com
 * @date 10/3/2020
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserSecurityConfig extends SecurityConfig {

    @Resource
    private IUserInfoService userInfoService;

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        // 获取登录用户信息
        return username -> userInfoService.loadUserByUsername(username);
    }

    @Bean
    public IDynamicSecurityService dynamicSecurityService() {

        return ConcurrentHashMap::new;
    }
}