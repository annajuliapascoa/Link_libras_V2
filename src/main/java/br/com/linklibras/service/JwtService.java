package br.com.linklibras.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    //Obtém a chave utilizada para assinar e validar os tokens
    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Gera um token para o usuário ou intérprete
    public String gerarToken(Integer id, String perfil) {

        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + expiration);

        return Jwts.builder()
                .subject(id.toString())
                .claim("perfil", perfil)
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(getSigninKey())
                .compact();
    }

    // Recupera as informações contidas no token
    public Claims extrairInformacoes(String token) {

        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Verifica se o token ainda está válido
    public boolean tokenValido(String token) {

        try {
            extrairInformacoes(token);
            return true;

        } catch (io.jsonwebtoken.JwtException |
                 IllegalArgumentException ex) {

            return false;
        }
    }
}
