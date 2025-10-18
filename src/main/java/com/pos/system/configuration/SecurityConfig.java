package com.pos.system.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.annotations.ConcreteProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.sessionManagement(management ->
                management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authorizeHttpRequests(Authorize -> Authorize
                                .requestMatchers("/auth/signup", "/auth/login").permitAll()
                                .requestMatchers("/api/**").authenticated()
                                .requestMatchers("api/super-admin/**").hasRole("ADMIN")
                                .anyRequest().permitAll()
                ).addFilterBefore(new JwtValidator(),
                        BasicAuthenticationFilter.class
                ).csrf(AbstractHttpConfigurer::disable
                ).cors(
                        cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cors = new CorsConfiguration();
                cors.setAllowedOrigins(Arrays.asList(
                        "http://localhost:3000",
                        "http://localhost:5173"
                ));
                cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                cors.setAllowCredentials(true);
                cors.setAllowedHeaders(Collections.singletonList("*"));
                cors.setExposedHeaders(List.of("Authorization"));
                cors.setMaxAge(3600L);
                return cors;
            }
        };
    }

}
