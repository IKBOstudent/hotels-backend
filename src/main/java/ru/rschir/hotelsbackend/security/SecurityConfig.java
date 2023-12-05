package ru.rschir.hotelsbackend.security;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.rschir.hotelsbackend.entities.User;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final AuthFilter authFilter;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(configurer -> configurer
                .requestMatchers("/api/v1/auth/**").permitAll()

                .requestMatchers(HttpMethod.GET, "/api/v1/hotels/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/hotels/**").hasAnyRole(User.UserRole.PARTNER.name(),
                                                                                  User.UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/api/v1/hotels/**").hasAnyRole(User.UserRole.PARTNER.name(),
                                                                                    User.UserRole.ADMIN.name())

                .requestMatchers(HttpMethod.GET, "/api/v1/rooms/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/rooms/**").hasAnyRole(User.UserRole.PARTNER.name(),
                                                                                 User.UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/api/v1/rooms/**").hasAnyRole(User.UserRole.PARTNER.name(),
                                                                                   User.UserRole.ADMIN.name())

                .requestMatchers("/api/v1/reservation/**").authenticated()

                .anyRequest().permitAll()
        );

        http.exceptionHandling(configurer -> configurer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        log.info("creating http security filter chain");
        return http.build();
    }
}