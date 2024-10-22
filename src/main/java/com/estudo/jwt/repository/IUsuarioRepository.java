package com.estudo.jwt.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.estudo.jwt.modal.Usuario;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends CrudRepository<Usuario, Long>{

	@Query("SELECT e FROM usuario e JOIN FETCH e.roles where e.username= (:username)")
	Optional<Usuario> findByEmail(@Param("username") String email);
	public boolean existsByUsuarioEmail(String email);
}
