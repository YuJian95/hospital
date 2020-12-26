package cn.yujian95.little.monitor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author YuJian95  yujian95_cn@163.com
 * @date 2020/12/10
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 跨域设置，SpringBootAdmin客户端通过instances注册，见InstancesController
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers("/instances");

        // 静态资源
        http.authorizeRequests().antMatchers("/assets/**", "/notifications/**").permitAll();
        // 所有请求必须通过认证
        http.authorizeRequests().anyRequest().authenticated();

        // 整合spring-boot-admin-server-ui
        http.formLogin().loginPage("/login").permitAll();
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login");

        // 启用basic认证
        http.httpBasic();
    }
}
