package org.example.restaurantmanagementapplication.common;

import static java.util.Collections.singletonList;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

  public static final String ADMIN_ROLE = "ADMIN";
  public static final String CHEF_ROLE = "CHEF";
  public static final String WAITER_ROLE = "WAITER";
  private final CustomUserDetailsService customUserDetailsService;

  @Bean
  public AuthenticationManager authenticationManager() {
    return new ProviderManager(singletonList(authProvider()));
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authProvider() {
    var authProvider = new DaoAuthenticationProvider();
    authProvider.setPasswordEncoder(passwordEncoder());
    authProvider.setUserDetailsService(customUserDetailsService);
    return authProvider;
  }

  @Bean
  @Profile("!test")
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authenticationProvider(authProvider())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/login", "/me").permitAll()
            .requestMatchers("/admin").hasRole(ADMIN_ROLE)
            .requestMatchers("/orders/*/confirm").hasAnyRole(ADMIN_ROLE, WAITER_ROLE)
            .requestMatchers("/orders/*/start-preparation").hasAnyRole(ADMIN_ROLE, CHEF_ROLE)
            .requestMatchers("/orders/*/mark-as-ready").hasAnyRole(ADMIN_ROLE, CHEF_ROLE)
            .requestMatchers("/orders/*/cancel").hasAnyRole(ADMIN_ROLE, WAITER_ROLE)
            .requestMatchers("/orders/*/update").hasAnyRole(ADMIN_ROLE, WAITER_ROLE)
            .requestMatchers("/bill/*").hasAnyRole(ADMIN_ROLE, WAITER_ROLE)
            .anyRequest().authenticated()
        ).sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .sessionConcurrency(concurrency -> concurrency.maximumSessions(1))
        ).logout(logout -> logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
        );

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOriginPatterns(List.of(
        "http://localhost:*",
        "https://localhost:*",
        "http://127.0.0.1:*",
        "https://127.0.0.1:*"
    ));

    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));

    configuration.setAllowedHeaders(List.of("*"));

    configuration.setExposedHeaders(List.of(
        "Authorization",
        "Cache-Control",
        "Content-Type",
        "Set-Cookie"
    ));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}