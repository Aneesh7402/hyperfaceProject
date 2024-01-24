package com.project.hyperface_project.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfig{
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    @Autowired
    public SecurityConfig(JwtAuthEntryPoint jwtAuthEntryPoint){
        this.jwtAuthEntryPoint=jwtAuthEntryPoint;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception,AuthenticationCredentialsNotFoundException, ExpiredJwtException {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling((exception)->exception.authenticationEntryPoint(jwtAuthEntryPoint))
                .sessionManagement((sessionManagement)->sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(HttpMethod.POST,"/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/dept/insertDepartment").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET).permitAll()
                        .anyRequest().authenticated()
                );
        httpSecurity.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return  httpSecurity.build();
    }

//    @Bean
//    public UserDetailsService users(){
//        UserDetails user=User.builder().username("user").password("password").roles("user").build();
//        UserDetails admin=User.builder().username("admin").password("password").roles("user").build();
//        return new InMemoryUserDetailsManager(user,admin);
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter();
    }
}
