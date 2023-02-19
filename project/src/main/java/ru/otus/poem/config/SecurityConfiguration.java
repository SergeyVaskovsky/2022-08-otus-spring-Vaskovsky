package ru.otus.poem.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.otus.poem.service.SecurityUserDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final SecurityUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/api/auth").permitAll()
                .antMatchers("/api/users").permitAll()
                .antMatchers(HttpMethod.GET, "/api/poems/*/elements").permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/poems/**").permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/api/poems").hasAuthority("AUTHOR")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.PUT, "/api/poems/**").hasAuthority("AUTHOR")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/api/comments").permitAll()//.hasAuthority("WRITER")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/comments/**").permitAll()//.hasAuthority("READER")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.PUT, "/api/comments/**").hasAuthority("MODERATOR")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/comments/**").hasAuthority("MODERATOR")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/api/poems/*/elements").hasAuthority("AUTHOR")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.PUT, "/api/poems/text-elements/**").hasAuthority("AUTHOR")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/api/poems/picture-elements/**").hasAuthority("AUTHOR")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.PUT, "/api/poems/picture-elements/**").hasAuthority("AUTHOR")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/poems/elements/**").hasAuthority("AUTHOR")
                .anyRequest()
                .authenticated();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService);
        return authenticationManagerBuilder.build();
    }
}