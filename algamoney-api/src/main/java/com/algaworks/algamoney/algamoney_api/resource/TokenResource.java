package com.algaworks.algamoney.algamoney_api.resource;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.algamoney_api.model.RefreshToken;
import com.algaworks.algamoney.algamoney_api.service.RefreshTokenService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/tokens")
public class TokenResource {
    
    private final RefreshTokenService refreshTokenService;
    private final com.algaworks.algamoney.algamoney_api.config.property.AlgamoneyApiProperty property;

    public TokenResource(RefreshTokenService refreshTokenService,
                        com.algaworks.algamoney.algamoney_api.config.property.AlgamoneyApiProperty property) {
        this.refreshTokenService = refreshTokenService;
        this.property = property;
    }
    
    @DeleteMapping("/revoke")
    public void revoke(@CookieValue(name = "refreshToken", required = false) String refreshTokenCookie,
                       HttpServletRequest req, 
                       HttpServletResponse resp) {
        // Remove o cookie do navegador SEMPRE
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(property.getSeguranca().isEnableHttps()); // HTTPS dinâmico
        cookie.setPath("/");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
        
        // Verifica se o cookie existe
        if (refreshTokenCookie == null || refreshTokenCookie.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        // Remove o token do banco de dados
        try {
            RefreshToken refreshToken = refreshTokenService.validateRefreshToken(refreshTokenCookie);
            refreshTokenService.deleteByUsername(refreshToken.getUsername());
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            // Token inválido ou já removido
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
