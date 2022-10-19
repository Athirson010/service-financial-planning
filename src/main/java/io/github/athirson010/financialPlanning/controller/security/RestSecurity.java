package io.github.athirson010.financialPlanning.controller.security;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@ApiResponse(responseCode = "404", description = "Objeto não encontrado")
@ApiResponse(responseCode = "204", description = "Nenhum conteudo encontrado")
@ApiResponse(responseCode = "400", description = "Parametros de requisição invalido")
@ApiResponse(responseCode = "403", description = "Verifique o token informado na requisição")
@SecurityRequirement(name = "bearerAuth")
public class RestSecurity {
}
