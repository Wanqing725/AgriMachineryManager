package org.agrimachinerymanager.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus配置类
 * 配置分页插件等功能
 */
@Configuration
public class MyBatisPlusConfig {

    /**
     * 配置MyBatis-Plus拦截器
     * 主要用于添加分页插件
     * @return MybatisPlusInterceptor实例
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        
        // 添加分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        
        // 设置最大单页限制数量，默认500条，-1不受限制
        paginationInnerInterceptor.setMaxLimit(500L);
        
        // 设置数据库类型为MySQL
        paginationInnerInterceptor.setDbType(com.baomidou.mybatisplus.annotation.DbType.MYSQL);
        
        // 添加到拦截器
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        
        return interceptor;
    }
}