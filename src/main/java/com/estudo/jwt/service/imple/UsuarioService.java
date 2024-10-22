package com.estudo.jwt.service.imple;

import com.estudo.jwt.bean.dto.UsuarioDto;
import com.estudo.jwt.exception.UserExistsException;
import com.estudo.jwt.exception.UserNotFoundException;
import com.estudo.jwt.modal.Usuario;
import com.estudo.jwt.repository.IUsuarioRepository;
import com.estudo.jwt.service.IRoleService;
import com.estudo.jwt.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {
	
	private IUsuarioRepository repository;

	private IRoleService roleService;

	private BCryptPasswordEncoder bc;
	
	@Autowired
	public UsuarioService(IUsuarioRepository repo, BCryptPasswordEncoder bc, IRoleService roleService) {
		this.repository = repo;
		this.bc = bc;
		this.roleService = roleService;
	}
	
	@Override
	public void createUser(UsuarioDto user) throws UserExistsException {
		Optional<Usuario> op = repository.findByEmail(user.getEmail());
		if(op.isPresent()) {
			throw new UserExistsException("Usuario já cadastrado.");
		}
		Usuario usuario = Usuario.builder()
				.email(user.getEmail())
				.senha(bc.encode(user.getSenha()))
				.build();
        repository.save(usuario);
	}

	@Override
	public Usuario getByUsername(String username) throws Exception {
		return repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
	}

	@Override
	public Usuario update(UsuarioDto user) throws Exception {
		Optional<Usuario> op = repository.findByEmail(user.getEmail());
		if(op.isEmpty()){
			throw new UserNotFoundException("User " + user.getEmail() + " not found");
		}
		Usuario usuario = op.get();
		usuario.setRole(this.roleService.findById(user.getRoleId()));
		usuario.setName(user.getName());
		usuario.setEmail(user.getEmail());
		if(user.getSenha() != null && !user.getSenha().isBlank()){
			usuario.setSenha(bc.encode(user.getSenha()));
		} else {
			usuario.setSenha(usuario.getSenha());
		}
		return this.repository.save(usuario);
	}
}
