package sideeffect.project.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sideeffect.project.security.JwtFilter;
import sideeffect.project.security.JwtTokenProvider;
import sideeffect.project.security.LoginFailureHandler;
import sideeffect.project.security.LoginSuccessHandler;
import sideeffect.project.security.RefreshTokenProvider;
import sideeffect.project.security.SecurityExceptionHandlerFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity
public class WebSecurityConfig{

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public LoginSuccessHandler loginSuccessHandler(RefreshTokenProvider refreshTokenProvider) {
        return new LoginSuccessHandler(refreshTokenProvider);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/api/user/join", "/api/user/mypage/**", "/api/user/duple/**", "/api/social/login")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/token/at-issue/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/free-boards/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/like/**").hasAnyRole("USER", "ADMIN")
                        )
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new SecurityExceptionHandlerFilter(), JwtFilter.class)
                .build();
    }
}
