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
            email = "kauan@gmail.com"
        ),
        description = "OpenApi documentation for SmartNews Tracker",
        title = "OpenApi specification - SmartNews Tracker Api",
        version = "1.0",
        license = @License(
            name = "MIT",
            url = "https://github.com/kauanmocelin/rest-api-springboot/blob/main/LICENSE"
        )
    ),
    servers = {
        @Server(
            description = "Local Environment",
            url = "http://localhost:8080"
        ),
    },
    security = {
        @SecurityRequirement(
            name = "bearerAuth"
        )
    }
)
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT Bearer Token Authentication",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    in = SecuritySchemeIn.HEADER,
    bearerFormat = "JWT"
)
public class OpenApiConfiguration {
}
