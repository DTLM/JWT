package com.estudo.jwt.security;

import com.estudo.jwt.model.Usuario;
import com.estudo.jwt.service.IJwtService;
import com.estudo.jwt.service.IUsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {


    private final IJwtService jwtService;
    private final IUsuarioService usuarioService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String email;
        /**
         * Etapa 1 - Pegar o token
         */
        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authHeader.substring(7);
        if (!jwtToken.isBlank()) {
            email = jwtService.extractEmail(jwtToken);
            /**
             * Etapa 2 - verifica se o usuario já está autenticado
             */
            if (!email.isBlank() && SecurityContextHolder.getContext().getAuthentication() == null) {
                /**
                 * Etapa 3 - No caso de não estar autenticado, então é necessario ver se esse email existe.
                 */
                Usuario user = null;
                try {
                    user = usuarioService.getByUsername(email);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                /**
                 * Etapa 4 - Verificar se o token criado é valido.
                 */
                if (user != null && jwtService.tokenIsValid(jwtToken, user)) {
                    /**
                     * Etapa 5 - Caso valido, apenas cadastrar no securityContextHolder
                     */
                    UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getRole().getAutoridades().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                    );
                    userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(userToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
