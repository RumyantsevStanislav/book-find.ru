package server.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// TODO: 17.11.2022 configure swagger
@EnableWebMvc //see matching-strategy: ant_path_matcher in .yaml
//@Configuration
//@EnableSwagger2
// Type javax.servlet.http.HttpServletRequest not present.
// Solution - springdoc-openapi or wait swagger update.
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("backend.controllers")) /*тут лежат контроллеры*/
                .paths(PathSelectors.regex("/api.*")) /*для каких путей нужна документация*/
                .build();
    }
}
