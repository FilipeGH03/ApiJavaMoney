package com.algaworks.algamoney.algamoney_api.config;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.algamoney_api.model.Usuario;
import com.algaworks.algamoney.algamoney_api.repository.UsuarioRepository;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final UsuarioRepository usuarioRepository;

    public TokenService(JwtEncoder jwtEncoder, UsuarioRepository usuarioRepository) {
        this.jwtEncoder = jwtEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        long expiry = 3600L; // 1 hora

        // Defensive: em alguns fluxos (refresh) as authorities podem ser null
        String scope = Optional.ofNullable(authentication.getAuthorities())
                .orElse(Collections.emptyList())
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        // Tenta obter o nome de exibição do usuário (campo 'nome' da entidade Usuario)
        String nome = null;
        try {
            nome = usuarioRepository.findByEmail(authentication.getName())
                    .map(Usuario::getNome)
                    .orElse(null);
        } catch (Exception e) {
            // Não falhar a geração do token caso a consulta ao banco falhe por algum motivo
            nome = null;
        }

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer("algamoney-api")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("scope", scope);

        if (nome != null) {
            claimsBuilder.claim("nome", nome);
        }

        JwtClaimsSet claims = claimsBuilder.build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
