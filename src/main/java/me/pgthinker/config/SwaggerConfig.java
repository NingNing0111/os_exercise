package me.pgthinker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @Project: me.pgthinker.config
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/9/4 15:10
 * @Description:
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Info info(){
        Info info = new Info();
        info.setVersion("v1.0");
        info.setDescription("操作系统题库接口");
        info.setTitle("OS Exercise API");
        return info;
    }

    @Bean
    public OpenAPI openAPI(){
        OpenAPI openAPI = new OpenAPI();
        openAPI.info(info());
        return openAPI;
    }
}
