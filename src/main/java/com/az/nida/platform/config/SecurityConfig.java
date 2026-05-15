package com.az.nida.platform.config;

import com.az.nida.platform.auth.filter.JwtAuthFilter;
import com.az.nida.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers(
                                "/api/auth/**",
                                "/actuator/health",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/ws/**"
                        ).permitAll()

                        // Analytics
                        .requestMatchers("/api/analytics/**").hasAnyRole("STUDENT", "TEACHER", "PARENT", "ADMIN")

                        // Dashboard
                        .requestMatchers("/api/dashboard/student/**").hasAnyRole("STUDENT", "ADMIN")
                        .requestMatchers("/api/dashboard/teacher/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers("/api/dashboard/parent/**").hasAnyRole("PARENT", "ADMIN")

                        // Exams
                        .requestMatchers(HttpMethod.POST, "/api/exams/create/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/exams/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/exams/**").hasAnyRole("STUDENT", "TEACHER", "PARENT", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/exams/**/result").hasAnyRole("STUDENT", "ADMIN")

                        // Gamification
                        .requestMatchers("/api/gamification/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")

                        // Groups
                        .requestMatchers(HttpMethod.POST, "/api/groups/create/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/groups/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/groups/**/invite").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/groups/invites/**").hasAnyRole("STUDENT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/groups/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")

                        // Homeworks
                        .requestMatchers(HttpMethod.POST, "/api/homeworks/create/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/homeworks/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/homeworks/attempts/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/homeworks/**/attempt").hasAnyRole("STUDENT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/homeworks/**").hasAnyRole("STUDENT", "TEACHER", "PARENT", "ADMIN")

                        // Interventions
                        .requestMatchers("/api/interventions/**").hasAnyRole("TEACHER", "ADMIN")

                        // Messages
                        .requestMatchers("/api/messages/**").hasAnyRole("STUDENT", "TEACHER", "PARENT", "ADMIN")

                        // Notifications
                        .requestMatchers("/api/notifications/**").hasAnyRole("STUDENT", "TEACHER", "PARENT", "ADMIN")

                        // Parent-Child
                        .requestMatchers(HttpMethod.POST, "/api/parent-child/request/**").hasAnyRole("PARENT", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/parent-child/request/**").hasAnyRole("STUDENT", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/parent-child/disconnect").hasAnyRole("STUDENT", "PARENT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/parent-child/**").hasAnyRole("STUDENT", "TEACHER", "PARENT", "ADMIN")

                        // Payments
                        .requestMatchers("/api/payments/**").hasAnyRole("PARENT", "ADMIN")

                        // Permissions
                        .requestMatchers(HttpMethod.POST, "/api/permissions/send/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/permissions/**").hasAnyRole("STUDENT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/permissions/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")

                        // Practice Exams
                        .requestMatchers(HttpMethod.POST, "/api/practice-exams/create/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/practice-exams/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/practice-exams/**/attempt").hasAnyRole("STUDENT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/practice-exams/**").hasAnyRole("STUDENT", "TEACHER", "PARENT", "ADMIN")

                        // Reports
                        .requestMatchers(HttpMethod.POST, "/api/reports/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/reports/weekly/parent/**").hasAnyRole("PARENT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/reports/weekly/student/**").hasAnyRole("STUDENT", "TEACHER", "PARENT", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/reports/**").hasAnyRole("PARENT", "ADMIN")

                        // Tests
                        .requestMatchers(HttpMethod.POST, "/api/tests/start/**").hasAnyRole("STUDENT", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/tests/**/answer").hasAnyRole("STUDENT", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/tests/**/finish").hasAnyRole("STUDENT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/tests/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")

                        // Users
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("STUDENT", "TEACHER", "PARENT", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole("STUDENT", "TEACHER", "PARENT", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                .map(user -> org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .authorities("ROLE_" + user.getRole().name())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("İstifadəçi tapılmadı: " + email));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "https://nida-learning-demo.vercel.app"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        return source -> {
            UrlBasedCorsConfigurationSource s = new UrlBasedCorsConfigurationSource();
            s.registerCorsConfiguration("/**", config);
            return s.getCorsConfiguration(source);
        };
    }
}