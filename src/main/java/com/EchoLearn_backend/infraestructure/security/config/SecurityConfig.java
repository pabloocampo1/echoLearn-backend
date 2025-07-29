package com.EchoLearn_backend.infraestructure.security.config;

import com.EchoLearn_backend.infraestructure.security.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private static final String roleAdmin = "ADMIN";
    private static final String roleSuperAdmin = "SUPERADMIN";
    private static final String roleUser = "USER";
    @Autowired
    private CorsConfig corsConfig;
    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(); //
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfig.configurationSource()))
                .authorizeHttpRequests(request -> {
                    // auth
                    request.requestMatchers("/api/auth/**").permitAll();

                    // user
                    request.requestMatchers(HttpMethod.GET, "/api/user").hasRole(roleAdmin);
                    request.requestMatchers(HttpMethod.GET, "/api/user/getAll").hasRole(roleAdmin);

                    // category
                    request.requestMatchers(HttpMethod.GET, "/api/category/public").permitAll();
                    request.requestMatchers( "/api/category/**").hasRole(roleAdmin);

                    // subCategory
                    request.requestMatchers(HttpMethod.GET,"/api/subcategory/getByCategory/**").permitAll();
                    request.requestMatchers("/api/subcategory/**").hasAnyRole(roleAdmin, roleSuperAdmin);

                    // exam
                    request.requestMatchers(HttpMethod.POST, "/api/exam/create/**").hasAnyRole( roleSuperAdmin ,roleAdmin);
                    request.requestMatchers(HttpMethod.GET, "/api/exam/getAll/home").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/api/exam/**").hasAnyRole(roleAdmin, roleSuperAdmin);

                    request.anyRequest().authenticated();
                })
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
