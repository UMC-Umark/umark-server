package umc.project.umark.global.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import umc.project.umark.global.exception.GlobalErrorCode;
import umc.project.umark.global.jwt.JwtFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import umc.project.umark.global.jwt.JwtTokenService;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenService jwtTokenService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final String[] allowedUrls = {
            "/",
            "/swagger-ui/**",
            "/member/login",
            "/api-docs/**",
            "/member/signup"
    };

    @Component
    public static class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

        @Override
        public void commence(
                HttpServletRequest request,
                HttpServletResponse response,
                AuthenticationException authException)
                throws IOException {
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            GlobalErrorCode unauthorizedError = GlobalErrorCode.AUTHENTICATION_REQUIRED;

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode ErrorNode = objectMapper.createObjectNode();
            ErrorNode.put("isSuccess", "false");
            ErrorNode.put("code", String.valueOf(unauthorizedError.getHttpStatus()));
            ErrorNode.put("message", unauthorizedError.getMessage());
            ErrorNode.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            String ErrorResponse = objectMapper.writeValueAsString(ErrorNode);

            response.getWriter().write(ErrorResponse);
        }
    }

    @Component
    public static class JwtAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(
                HttpServletRequest request,
                HttpServletResponse response,
                AccessDeniedException accessDeniedException)
                throws IOException, ServletException {
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            GlobalErrorCode forbiddenError = GlobalErrorCode.MEMBER_NOT_AUTHORIZED;

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode ErrorNode = objectMapper.createObjectNode();
            ErrorNode.put("isSuccess", "false");
            ErrorNode.put("code", String.valueOf(forbiddenError.getHttpStatus()));
            ErrorNode.put("message", forbiddenError.getMessage());
            ErrorNode.put("status", HttpServletResponse.SC_FORBIDDEN);
            String ErrorResponse = objectMapper.writeValueAsString(ErrorNode);

            response.getWriter().write(ErrorResponse);
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(
                        (exception) ->
                                exception
                                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                        .accessDeniedHandler(jwtAccessDeniedHandler))
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers(allowedUrls)
                                        .permitAll()
                                        .requestMatchers("/admin/**")
                                        .hasAuthority("ROLE_ADMIN")
                                        .requestMatchers(
                                                "/api/v1/members/**",
                                                "/api/v1/likes/**",
                                                "/api/v1/favorites/**")
                                        .authenticated()
                                        .anyRequest()
                                        .authenticated())
                .addFilterBefore(
                        new JwtFilter(jwtTokenService),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt Encoder 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
