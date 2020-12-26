package cn.yujian95.little.admin.config;

import cn.yujian95.little.admin.modules.power.service.IPowerAdminService;
import cn.yujian95.little.admin.modules.power.service.IPowerResourceService;
import cn.yujian95.little.security.component.IDynamicSecurityService;
import cn.yujian95.little.security.config.SecurityConfig;
import cn.yujian95.little.mbg.modules.power.entity.PowerResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yujian95 clj9509@163.com
 * @date 10/3/2020
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminSecurityConfig extends SecurityConfig {

    @Resource
    private IPowerAdminService adminService;

    @Resource
    private IPowerResourceService resourceService;

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        // 获取登录用户信息
        return username -> adminService.loadAdminByUsername(username);
    }

    @Bean
    public IDynamicSecurityService dynamicSecurityService() {

        return () -> {

            Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();

            List<PowerResource> resourceList = resourceService.list();

            for (PowerResource resource : resourceList) {
                map.put(resource.getUrl(), new org.springframework.security.access
                        .SecurityConfig(resource.getId() + ":" + resource.getName()));
            }

            return map;
        };
    }
}