package org.agrimachinerymanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j配置类
 * 用于配置OpenAPI文档信息
 */
@Configuration
public class Knife4jConfig {

    /**
     * 配置OpenAPI文档信息
     * @return OpenAPI对象
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // 配置文档信息
                .info(new Info()
                        // 标题
                        .title("农业机械管理系统API文档")
                        // 描述
                        .description("农业机械管理系统的接口文档，包含农机管理、维护管理、作业调度等功能")
                        // 版本
                        .version("1.0.0")
                        // 配置联系人信息
                        .contact(new Contact()
                                .name("开发团队")
                                .email("team@example.com")
                                .url("http://localhost:8080/agri-machinery"))
                        // 配置许可证信息
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}