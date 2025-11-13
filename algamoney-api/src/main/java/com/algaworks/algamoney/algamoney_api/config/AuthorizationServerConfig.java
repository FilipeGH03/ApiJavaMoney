package com.algaworks.algamoney.algamoney_api.config;

/*
 * NOTA: Esta classe foi desabilitada porque o Authorization Server foi removido 
 * do Spring Security 6.x (Spring Boot 3.x).
 * 
 * A funcionalidade OAuth2 agora está implementada em:
 * - ResourceServerConfig: Validação de JWT tokens
 * - TokenService: Geração de JWT tokens
 * - AuthController: Endpoint /oauth/token para login
 * 
 * O fluxo atual é:
 * 1. Cliente faz POST /oauth/token com username/password
 * 2. API retorna JWT token
 * 3. Cliente usa o token em: Authorization: Bearer <token>
 * 
 * Para usar o novo Authorization Server oficial do Spring:
 * https://spring.io/projects/spring-authorization-server
 */

public class AuthorizationServerConfig {
    // Classe desabilitada - OAuth2 implementado em ResourceServerConfig + TokenService + AuthController
}
