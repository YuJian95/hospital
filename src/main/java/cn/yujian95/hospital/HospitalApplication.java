package cn.yujian95.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

}
