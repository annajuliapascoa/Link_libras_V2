package br.com.linklibras.config;

import br.com.linklibras.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
}

@Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
) throws ServletException, IOException {

    // Recupera o cabeçalho Authorization
    String authorizationHeader = request.getHeader("Authorization");

    // Verifica se existe um token Bearer
    if (authorizationHeader == null ||
            !authorizationHeader.startsWith("Bearer ")) {

        filterChain.doFilter(request, response);
        return;
    }


    // Remove o prefixo Bearer
    String token = authorizationHeader.substring(7);

    // Verifica se o token é válido
    if (jwtService.tokenValido(token)) {

        Claims claims = jwtService.extrairInformacoes(token);

        String id = claims.getSubject();
        String perfil = claims.get("perfil", String.class);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        id,
                        null,
                        Collections.emptyList()
                );

        authentication.setDetails(
                new WebAuthenticationDetailsSource()
                        .buildDetails(request)
        );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);

    }
}
