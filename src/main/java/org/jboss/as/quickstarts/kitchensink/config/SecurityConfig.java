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

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/members/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/members/all").hasRole(MemberRoleEnum.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT,"/members/**").hasRole(MemberRoleEnum.USER.name())
                        .requestMatchers(HttpMethod.GET,"/members/**").hasAnyRole(MemberRoleEnum.USER.name(),MemberRoleEnum.ADMIN.name())
                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                .securityMatcher("/members/**");
        return http.build();
    }
}

