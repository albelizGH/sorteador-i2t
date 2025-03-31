package com.pentabyte.projects.sorteador.config.security;

import com.pentabyte.projects.sorteador.repository.IntegranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansInjector {

    private final AuthenticationConfiguration authenticationConfiguration;

    private final IntegranteRepository integranteRepository;

    @Autowired
    public SecurityBeansInjector(AuthenticationConfiguration authenticationConfiguration, IntegranteRepository integranteRepository) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.integranteRepository = integranteRepository;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider();
        authenticationStrategy.setPasswordEncoder(passwordEncoder());
        authenticationStrategy.setUserDetailsService(userDetailsService());
        return authenticationStrategy;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> integranteRepository.findByEmail(email).map(UserDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
    }


}
