package cn.yujian95.little.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author YuJian95  yujian95_cn@163.com
 * @date 2020/12/10
 */
@EnableAdminServer
@SpringBootApplication
public class LittleMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(LittleMonitorApplication.class, args);
    }
}