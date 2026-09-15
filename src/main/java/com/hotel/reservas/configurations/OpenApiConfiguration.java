package com.hotel.reservas.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Hotel Reservations API",
                version = "1.0",
                description = """
                        API REST para gerenciamento de reservas de hotel.

                        A API permite realizar operações de consulta,
                        criação, atualização e exclusão de reservas,
                        além de gerenciamento de usuários e autenticação.

                        A autenticação utiliza JWT e a autorização
                        utiliza as roles USER e ADMIN.
                        """))
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT")
public class OpenApiConfiguration {}
