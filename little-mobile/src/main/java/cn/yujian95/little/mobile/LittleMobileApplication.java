package cn.yujian95.little.mobile;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yujian95 yujian95_cn@163.com
 * @since 2020-10-19
 */
@SpringBootApplication
@EnableSwaggerBootstrapUI
@Slf4j
public class LittleMobileApplication {

    public static void main(String[] args) {
        SpringApplication.run(LittleMobileApplication.class, args);
    }

}
