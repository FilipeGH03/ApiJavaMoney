package com.algaworks.algamoney.algamoney_api.resource;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.algamoney_api.config.TokenService;
import com.algaworks.algamoney.algamoney_api.model.RefreshToken;
import com.algaworks.algamoney.algamoney_api.service.RefreshTokenService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final com.algaworks.algamoney.algamoney_api.config.property.AlgamoneyApiProperty property;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager, 
                         RefreshTokenService refreshTokenService,
                         com.algaworks.algamoney.algamoney_api.config.property.AlgamoneyApiProperty property) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.property = property;
    }

    @PostMapping("/oauth/token")
    public TokenResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        String accessToken = tokenService.generateToken(authentication);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(loginRequest.getUsername());
        
        // Adiciona o refresh token em um HttpOnly Cookie
        adicionarRefreshTokenNoCookie(refreshToken.getToken(), response);
        
        return new TokenResponse(accessToken, "Bearer", 3600L);
    }

    @PostMapping("/oauth/token/refresh")
    public TokenResponse refresh(@CookieValue(name = "refreshToken") String refreshTokenCookie, HttpServletResponse response) {
        RefreshToken refreshToken = refreshTokenService.validateRefreshToken(refreshTokenCookie);
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            refreshToken.getUsername(), 
            null, 
            null
        );
        
        String newAccessToken = tokenService.generateToken(authentication);
        
        // Renova o refresh token no cookie
        adicionarRefreshTokenNoCookie(refreshToken.getToken(), response);
        
        return new TokenResponse(newAccessToken, "Bearer", 3600L);
    }

    @PostMapping("/oauth/token/revoke")
    public void logout(@CookieValue(name = "refreshToken", required = false) String refreshTokenCookie, 
                       HttpServletResponse response) {
        if (refreshTokenCookie != null) {
            try {
                RefreshToken refreshToken = refreshTokenService.validateRefreshToken(refreshTokenCookie);
                refreshTokenService.deleteByUsername(refreshToken.getUsername());
            } catch (Exception e) {
                // Ignora erros, apenas remove o cookie
            }
        }
        removerRefreshTokenDoCookie(response);
    }

    private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);  // JavaScript não pode acessar
        cookie.setSecure(property.getSeguranca().isEnableHttps());   // HTTPS dinâmico
        cookie.setPath("/");       // Disponível em toda aplicação
        cookie.setMaxAge(30 * 24 * 60 * 60); // 30 dias em segundos
        response.addCookie(cookie);
    }

    private void removerRefreshTokenDoCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(property.getSeguranca().isEnableHttps());
        cookie.setPath("/");
        cookie.setMaxAge(0); // Remove imediatamente
        response.addCookie(cookie);
    }

    // DTOs
    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class TokenResponse {
        private String access_token;
        private String token_type;
        private Long expires_in;

        public TokenResponse(String access_token, String token_type, Long expires_in) {
            this.access_token = access_token;
            this.token_type = token_type;
            this.expires_in = expires_in;
        }

        public String getAccess_token() {
            return access_token;
        }

        public String getToken_type() {
            return token_type;
        }

        public Long getExpires_in() {
            return expires_in;
        }
    }
}
