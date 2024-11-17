package com.estudo.jwt.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.estudo.jwt.model.Usuario;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends CrudRepository<Usuario, Long>{

	@Query("SELECT e FROM Usuario e JOIN FETCH e.role r JOIN FETCH r.autoridades WHERE e.email = :email")
	Optional<Usuario> findByEmail(@Param("email") String email);
	public boolean existsByEmail(String email);
}
