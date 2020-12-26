package cn.yujian95.little.admin.config;

import cn.yujian95.little.common.config.BaseSwaggerConfig;
import cn.yujian95.little.common.api.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author yujian95 clj9509@163.com
 * @date 10/3/2020
 */
@EnableSwagger2
@Configuration
public class Swagger2Config extends BaseSwaggerConfig {

    /**
     * 配置扫描的api控制包路径
     */
    private static final String BASE_PACKAGE = "cn.yujian95.little.admin.modules";

    @Override
    public SwaggerProperties swaggerProperties() {

        return SwaggerProperties.builder()
                .apiBasePackage(BASE_PACKAGE)
                .title("little 项目骨架")
                .description("little 项目骨架相关接口文档")
                .contactName("yujian95_cn@163.com")
                .version("1.0.0")
                .enableSecurity(true)
                .build();
    }
}
