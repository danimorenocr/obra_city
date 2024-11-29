package com.usta.Galeria.security;

import com.usta.Galeria.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/crearArtista", "/eliminarArtista/", "/editarArtista/", "/crearObra",
                                "/editarObra/",
                                "/eliminarObra/", "/crearExposicion", "/editarExposicion/", "/eliminarExposicion/")
                        .hasRole("ADMINISTRADOR")
                        .requestMatchers("/artistas", "/exposiciones", "/obra*", "/detalleObra/")
                        .hasAnyRole("ADMINISTRADOR", "VISITANTE")
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error") // Corregido error en la URL
                        .successHandler(loginSuccessHandler) // Asegúrate de definir 'loginSuccessHandler' si lo usas
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll())
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedPage("/error404"));

        return http.build();
    }

}