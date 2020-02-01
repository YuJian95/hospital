package cn.yujian95.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/18
 */

@SpringBootApplication
public class HospitalApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
    }

    /**
     * 继承 SpringBootServletInitializer 实现 configure()
     * 方便打 war 外部服务器部署。
     *
     * @param applicationBuilder 程序构建工具
     * @return 程序构建工具
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(HospitalApplication.class);
    }
}
