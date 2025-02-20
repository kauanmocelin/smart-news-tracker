package dev.kauanmocelin.springbootrestapi.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        contact = @Contact(
            name = "Kauan",
            email = "kauan@gmail.com",
            url = "http://kauan.dev"
        ),
        description = "OpenApi documentation for Rest Api Springboot",
        title = "OpenApi specification - Rest Api",
        version = "1.0",
        license = @License(
            name = "MIT",
            url = "https://github.com/kauanmocelin/rest-api-springboot/blob/main/LICENSE"
        ),
        termsOfService = "http://Terms-of-service"
    ),
    servers = {
        @Server(
            description = "Local ENV",
            url = "http://localhost:8080"
        ),
        @Server(
            description = "Local PROD",
            url = "http://kauan-rest-api-springboot.dev"
        )
    },
    security = {
        @SecurityRequirement(
            name = "basicAuth"
        )
    }
)
@SecurityScheme(
    name = "basicAuth",
    description = "Basic auth description",
    scheme = "basic",
    type = SecuritySchemeType.HTTP,
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfiguration {
}
