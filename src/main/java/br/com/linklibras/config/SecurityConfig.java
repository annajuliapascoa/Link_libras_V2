package br.com.linklibras.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                //Desativa a proteção CSRF
                .csrf(csrf -> csrf.disable())

                // Configura a aplicação para não utilizar sessões
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Define as permissões de acesso
                .authorizeHttpRequests(auth -> auth

                        // Cadastros públicos
                        .requestMatchers(HttpMethod.POST,
                                "/api/auth/usuarios/cadastro",
                                "/api/auth/interpretes/cadastro"
                        ).permitAll()

                        // Logins públicos
                        .requestMatchers(HttpMethod.POST,
                                "/api/auth/usuarios/login",
                                "/api/auth/interpretes/login"
                        ).permitAll()

                        // Demais rotas exigem autenticação
                        .anyRequest().authenticated()
                )

                // Desativa autenticação HTTP Basic
                .httpBasic(basic -> basic.disable())

                // Desativa formulário padrão de login
                .formLogin(form -> form.disable());

                // Adiciona o filtro JWT antes do filtro padrão
                http.addFilterBefore(
                        jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}

