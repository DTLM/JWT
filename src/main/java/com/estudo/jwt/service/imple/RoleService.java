package com.estudo.jwt.service.imple;

import com.estudo.jwt.model.Role;
import com.estudo.jwt.repository.IRoleRepository;
import com.estudo.jwt.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{
	
	private final IRoleRepository repository;


	@Override
	public Role getByNome(String nome) {
		return repository.getByNome(nome);
	}

	@Override
	public Role findById(Long id) throws Exception{
		Optional<Role> role = repository.findById(id);
		if (role.isPresent()){
			return role.get();
		}
		throw new Exception("Role não encontrada");
    }

	@Override
	public void Save(Role role) {
		Role aux = repository.getByNome(role.getNome());
		if(aux == null){
			repository.save(role);
		} else {
			aux.setNome(role.getNome());
			aux.setAutoridades(role.getAutoridades());
			this.repository.save(aux);
		}
	}

}
