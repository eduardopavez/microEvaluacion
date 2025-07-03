package com.evaluacion.microEvaluacion.SwagConfig;

import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

public class SwagerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Microservicio Evaluación API")
                .version("1.0")
                .description("Documentación API para el microservicio de evaluación"));
    }

}
