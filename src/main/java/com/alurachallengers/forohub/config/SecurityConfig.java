package com.alurachallengers.forohub.config;

import com.alurachallengers.forohub.config.filter.JwtTokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity //permite hace lo mismo pero con anotaciones.
public class SecurityConfig {

    @Autowired
    private JwtTokenValidator SecurityFilter;

    //manera normal:
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    //configurar los publicos primeros.
                    http.requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/auth/loging").permitAll();
                    //luego los privados.
                    http.requestMatchers(HttpMethod.GET, "/api/topicos/**").hasRole("USER");
                    http.requestMatchers(HttpMethod.POST, "/api/topicos/**").hasRole("USER");
                    http.requestMatchers(HttpMethod.PUT, "/api/topicos/**").hasRole("USER");
                    http.requestMatchers(HttpMethod.DELETE, "/api/topicos/**").hasRole("USER");

                    //el resto de endpoints - no especificado (siempre y cuando tengas acceso).

                    /*authenticated(): con credenciales correctas to-do ok
                     *denyAll()si no esta especificado en los endpoints de arriba no hay trato.*/
                    http.anyRequest().denyAll();
                })
                .addFilterBefore(SecurityFilter, BasicAuthenticationFilter.class)
                .build();
    }


    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }*/

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*Spring Security usa uno muy similar a este por defecto con un Dao de provider
     * si planeas usar Dao como provider es mejor dejar que SpringSecurity lo haga por vos.*/
    /*@Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }*/

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
