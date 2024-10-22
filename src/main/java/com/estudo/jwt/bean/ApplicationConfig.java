package com.estudo.jwt.bean;

import com.estudo.jwt.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final IUsuarioRepository repo;

    /**
     * Função: Criação de UserDetailsService. Utilizasse para encontrar o user details que sera carregado no
     * securityContextHolder, que por sua vez é quem guarda a informação do usuario autenticado.
     * Obs: Naturalmente se cria com new UserDetailsService e teria que implementar o loadUserByUsername, mas dessa forma
     * criamos em um formato mais simplificado.Com Lambda.
     * @throws UsernameNotFoundException
     */
    @Bean
    public UserDetailsService userDetailsService() throws UsernameNotFoundException {
        return username -> repo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    }

    /**
     * Função: Criação de um encoder
     */
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Função: É o objeto que tem como objetivo carregar o userDetails e o encoder.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    /**
     * Função: Como o nome já especifica, eu um objeto do spring com a funcionalidade de cuidar e manusear o serviço de
     * autenticação
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
