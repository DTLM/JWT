package com.estudo.jwt.repository;

import com.estudo.jwt.modal.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends CrudRepository<Role, Long>{

	Role getByNome(String nome);

}
