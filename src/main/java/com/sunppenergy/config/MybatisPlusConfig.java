package com.sunppenergy.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatisPlus 配置类
 *
 * @Author: admin
 * @Date: 2019/7/29 9:59
 * @Description:
 */
@Configuration
@MapperScan("com.baomidou.mybatisplus.samples.pagination.mapper")
public class MybatisPlusConfig {

    /**
     * 分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
