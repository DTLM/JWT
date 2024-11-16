package com.estudo.jwt.custom;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @author Thiago Martins
 * @implNote Essa classe é para criar um authorizationManager customizado, pois ele quem acessa o avaliador de permissões personalizadas
 */
@Component
@RequiredArgsConstructor
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext>{

    private final CustomPermissionEvaluator customPermissionEvaluator;

    public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext context) {
        try {
            var request = context.getRequest();
            boolean hasPermission = customPermissionEvaluator.hasPermission(authenticationSupplier.get(),request);
            return new AuthorizationDecision(hasPermission);
        } catch (Exception e){
            e.printStackTrace();
            return new AuthorizationDecision(false);
        }
    }
}
