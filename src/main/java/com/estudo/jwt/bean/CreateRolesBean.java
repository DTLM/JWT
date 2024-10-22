package com.estudo.jwt.bean;

import com.estudo.jwt.modal.Role;
import com.estudo.jwt.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Configuration
@Component
public class CreateRolesBean {
    private IRoleService roleService;

    @Autowired
    public CreateRolesBean(IRoleService roleService){
        this.roleService = roleService;
    }

//    @Bean
//    public void createRoles(){
//        Role role = Role.builder().nome("ADM").autoridades(Arrays.asList("READ", "DELETE", "CREATE", "UPDATE")).build();
//        this.roleService.Save(role);
//        Role role2 = Role.builder().nome("USER").autoridades(Arrays.asList("READ", "CREATE")).build();
//        this.roleService.Save(role2);
//    }
}
