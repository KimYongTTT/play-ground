package io.kakaobank.search.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI OpenAPI() {
        Info info =
                new Info()
                        .title("Kakao Bank Swagger")
                        .version("1.0")
                        .description("API description");

        return new OpenAPI().info(info);
    }
}
