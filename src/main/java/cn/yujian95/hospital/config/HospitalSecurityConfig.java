package cn.yujian95.hospital.config;

import cn.yujian95.hospital.common.security.IDynamicSecurityService;
import cn.yujian95.hospital.common.security.SecurityConfig;
import cn.yujian95.hospital.entity.PowerResource;
import cn.yujian95.hospital.service.IPowerAccountService;
import cn.yujian95.hospital.service.IPowerResourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/3/14
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HospitalSecurityConfig extends SecurityConfig {

    @Resource
    private IPowerAccountService accountService;

    @Resource
    private IPowerResourceService resourceService;

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        // 获取登录用户信息
        return username -> accountService.loadUserByUserName(username);
    }

    @Bean
    public IDynamicSecurityService dynamicSecurityService() {

        return () -> {

            Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();

            // TODO fix resultMap error.
//            List<PowerResource> resourceList = resourceService.listAll();
            List<PowerResource> resourceList = new ArrayList<>();

            for (PowerResource resource : resourceList) {
                map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
            }

            return map;
        };
    }
}
