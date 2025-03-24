package org.jboss.as.quickstarts.kitchensink.config;

import lombok.AllArgsConstructor;
import org.jboss.as.quickstarts.kitchensink.constants.MemberRoleEnum;
import org.jboss.as.quickstarts.kitchensink.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/auth/**").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/members/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/members/all").hasRole(MemberRoleEnum.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT,"/members/**").hasAnyRole(MemberRoleEnum.USER.name(), MemberRoleEnum.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/members/**").hasAnyRole(MemberRoleEnum.USER.name(),MemberRoleEnum.ADMIN.name())
                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // Allow all origins
        configuration.setAllowedMethods(List.of("*")); // Allow all HTTP methods
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        source.registerCorsConfiguration("/auth/login", configuration);
        return source;
    }
}

