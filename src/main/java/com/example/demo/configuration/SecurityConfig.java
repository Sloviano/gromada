package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;
    private final AiApiKeyFilter aiApiKeyFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            com.example.demo.entities.User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole().name())
                    .disabled(!user.isActive())
                    .build();
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Static resources
                .requestMatchers("/favicon.ico", "/webjars/**", "/js/**", "/css/**", "/images/**").permitAll()
                // WebSocket endpoint
                .requestMatchers("/ws/**").permitAll()
                // Public API endpoints
                .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/businesses/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/classifieds/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/jobs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/announcements/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/reviews/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/sell-out-events/active").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/ad-campaigns/active").permitAll()
                // AI agent endpoints - secured by API key header filter
                .requestMatchers("/api/ai/**").hasRole("AI_AGENT")
                // Admin endpoints
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                // All other API endpoints require authentication
                .requestMatchers("/api/**").authenticated()
                // Thymeleaf pages
                .requestMatchers("/businesses/**").authenticated()
                .requestMatchers("/chat").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(formLogin -> formLogin
                .defaultSuccessUrl("/businesses/list", true)
                .permitAll()
            )
            .httpBasic(httpBasic -> {})
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})
            .addFilterBefore(aiApiKeyFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
