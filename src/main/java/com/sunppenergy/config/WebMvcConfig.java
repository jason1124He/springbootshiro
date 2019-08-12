package com.sunppenergy.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 解决swaggerUI访问时
 * Whitelabel Error Page
 * This application has no explicit mapping for /error, so you are seeing this as a fallback.
 * There was an unexpected error (type=Not Found, status=404).
 * No message available
 * 的问题
 * 因为swagger-ui.html 是在springfox-swagger-ui.jar里的，
 * 因为修改了路径Spring Boot不会自动把/swagger-ui.html这个路径映射到对应的目录META-INF/resources/下面
 * <p>
 * 为swagger建立新的静态文件路径映射
 *
 * @Author: hej
 * @Date: 2019/7/25 9:28
 * @Description:
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    /**
     * 请求跨域设置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowCredentials(true);
    }


}
