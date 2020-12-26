package cn.yujian95.little.admin;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-11
 */
@SpringBootApplication
@EnableSwaggerBootstrapUI
@Slf4j
public class LittleAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(LittleAdminApplication.class, args);
    }

}
