package com.example.ssoweb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.client.provider.auth0.issuer-uri}")
    private String issueUrl;

    @Value("${spring.security.oauth2.client.registration.auth0.client-id}")
    private String clientId;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/error", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/auth0")
                        .defaultSuccessUrl("/home", true)
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(request ->
                                "GET".equals(request.getMethod()) && "/logout".equals(request.getServletPath())
                        )
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID", "SESSION")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            String logoutUrl = issueUrl.endsWith("/")
                                    ? issueUrl + "v2/logout"
                                    : issueUrl + "/v2/logout";
                            logoutUrl += "?client_id=" + clientId + "&returnTo=http://localhost:8080/";
                            response.sendRedirect(logoutUrl);
                        })
                );
        return http.build();
    }
}
