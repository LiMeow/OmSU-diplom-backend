package omsu.imit.schedule.configuration

import com.google.common.collect.Lists
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("omsu.imit.schedule.controller"))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(listOf(apiKey()))
                .genericModelSubstitutes(Optional::class.java)
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return Lists.newArrayList(
                SecurityReference("JWT", authorizationScopes))
    }

    private fun apiKey(): ApiKey {
        return ApiKey("Bearer %token", "Authorization", "Header")
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("Spring Boot REST API")
                .description("Schedule Generator REST API")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("0.0.2")
                .build()
    }

}
