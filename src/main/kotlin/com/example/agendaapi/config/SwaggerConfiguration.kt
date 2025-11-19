package com.example.agendaapi.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Api Agenda")
                    .description("Api Definition")
                    .version("1.0.0")
                    .contact(
                        Contact().apply {
                            name = "Rafael Moura"
                            url = "https://rafaelmoura.dev"
                            email = "antoninocs@gmail.com"
                        }
                    )
                    .license(
                        License().apply {
                            name = "Apache 2.0"
                            url = "http://www.apache.org/licenses/LICENSE-2.0"
                        }
                    )
            )
    }
}