package cn.yujian95.hospital.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger 接口文档配置类
 *
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/19
 */

@EnableSwaggerBootstrapUI
@EnableSwagger2
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {

    /**
     * 配置扫描的api控制包路径
     */
    private static final String BASE_PACKAGE = "cn.yujian95.hospital.controller";

    /**
     * 服务器路径
     */
    private static final String SERVICE_URL = "https://yujian95.cn/hospital";

    /**
     * 配置swagger文档
     *
     * @return docket 对象
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()

                // 设置扫描基础包
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))

                // 扫描使用Api注解的控制器
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()

                //添加登录认证
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public RequestMappingInfoHandlerMapping requestMapping() {
        return new RequestMappingHandlerMapping();
    }

    /**
     * 配置 api 文档信息
     *
     * @return 文档信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()

                // 页面标题
                .title("医院预约挂号系统API文档")

                // API描述
                .description("Created by yujian95."
                        + "<br/>" +
                        "<a href=\"https://github.com/YuJian95/base-service/blob/master/README.md\">See more at Readme.</a>"
                        + "<br/>" +
                        "<a href=\"https://yujian95.cn\">Contact the developer.</a>")

                // 创建路径
                .termsOfServiceUrl(SERVICE_URL)
                .version("1.0.0")
                .build();
    }

    /**
     * 解决 swagger 静态资源 404问题
     *
     * @param registry 资源路径
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 解决静态资源无法访问
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");

        // 解决swagger无法访问
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        // 解决swagger-bootstrap 无法访问
        registry.addResourceHandler("doc.html").
                addResourceLocations("classpath:/META-INF/resources/");
    }

    private List<ApiKey> securitySchemes() {
        //设置请求头信息
        List<ApiKey> result = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
        result.add(apiKey);
        return result;
    }

    private List<SecurityContext> securityContexts() {
        //设置需要登录认证的路径
        List<SecurityContext> result = new ArrayList<>();
        // 所有请求路径
        result.add(getContextByPath("/.*"));
        return result;
    }

    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        result.add(new SecurityReference("Authorization", authorizationScopes));
        return result;
    }

}
