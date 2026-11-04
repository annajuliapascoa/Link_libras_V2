package br.com.linklibras.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)
                throws Exception {

            http
                    .csrf(csrf -> csrf.disable())

                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )

                    .authorizeHttpRequests(auth -> auth

                            // Cadastros públicos
                            .requestMatchers(HttpMethod.POST,
                                    "/usuarios/cadastro",
                                    "/interpretes/cadastro"
                            ).permitAll()

                            // Logins públicos
                            .requestMatchers(HttpMethod.POST,
                                    "/usuarios/login",
                                    "/interpretes/login"
                            ).permitAll()

                            // Demais rotas exigem autenticação
                            .anyRequest().authenticated()
                    )

                    .httpBasic(basic -> basic.disable())
                    .formLogin(form -> form.disable());

            return http.build();
        }
    }

