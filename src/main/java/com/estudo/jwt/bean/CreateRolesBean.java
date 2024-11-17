package com.estudo.jwt.bean;

import com.estudo.jwt.model.Role;
import com.estudo.jwt.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CreateRolesBean {
    private final IRoleService roleService;

    // para facilitar poderia criar uma factory Role para gerar, mas pra aprendizado, vou manter a mostra.
//    @Bean
//    public List<Role> createRoles(){
//        List<Role> roles = new ArrayList<>();
//        Role role = Role.builder().nome("ADM").autoridades(new HashSet<>(Arrays.asList("READ", "DELETE", "CREATE", "UPDATE"))).build();
//        this.roleService.Save(role);
//        Role role2 = Role.builder().nome("MANAGER").autoridades(new HashSet<>(Arrays.asList("READ", "CREATE","UPDATE","DELETE"))).build();
//        this.roleService.Save(role2);
//        Role role3 = Role.builder().nome("USER").autoridades(new HashSet<>(List.of("READ"))).build();
//        this.roleService.Save(role3);
//        roles.add(role);
//        roles.add(role2);
//        roles.add(role3);
//        return roles;
//    }
}
