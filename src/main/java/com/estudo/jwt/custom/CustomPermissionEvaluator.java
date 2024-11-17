package com.estudo.jwt.custom;

import com.estudo.jwt.exception.UserNotFoundException;
import com.estudo.jwt.model.Usuario;
import com.estudo.jwt.service.IUsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomPermissionEvaluator {

    private final IUsuarioService usuarioService;

    /**
     *
     * @param authentication
     * @param request
     * @return boolean que indica se tem permissão ou não de realizar a ação
     * @throws Exception
     */
    public boolean hasPermission(Authentication authentication, HttpServletRequest request) throws Exception {
        Usuario user = usuarioService.getByUsername(authentication.getName());
        if (user == null) {
            throw new UserNotFoundException("Usuário não encontrado: " + authentication.getName());
        }
        if (user.getRole().getNome().equalsIgnoreCase("ADM")) {
            return true;
        }
        String method = request.getMethod();
        return switch (method) {
            case "GET" -> checkPermission(authentication, "READ");
            case "POST" -> checkPermission(authentication, "CREATE");
            case "PUT" -> checkPermission(authentication, "UPDATE");
            case "PATCH" -> checkPermission(authentication, "UPDATE");
            case "DELETE" -> checkPermission(authentication, "DELETE");
            default -> false;
        };
    }
    private boolean checkPermission(Authentication authentication, String permission) throws Exception {
        Usuario user = usuarioService.getByUsername(authentication.getName());
        if (user == null || user.getRole() == null || user.getRole().getAutoridades() == null || user.getRole().getAutoridades().isEmpty()) {
            return false;
        }
        return user.getRole().getAutoridades().contains(permission.toUpperCase());
    }
}
