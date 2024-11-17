package com.estudo.jwt.service.imple;

import com.estudo.jwt.bean.dto.UsuarioDto;
import com.estudo.jwt.bean.dto.UsuarioResponse;
import com.estudo.jwt.exception.UserExistsException;
import com.estudo.jwt.exception.UserNotFoundException;
import com.estudo.jwt.exception.WrongPasswordException;
import com.estudo.jwt.model.Usuario;
import com.estudo.jwt.repository.IUsuarioRepository;
import com.estudo.jwt.service.IAutenticacaoService;
import com.estudo.jwt.service.IRoleService;
import com.estudo.jwt.service.IUsuarioService;
import com.estudo.jwt.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {
	
	private final IUsuarioRepository repository;

	private final IRoleService roleService;

	private final BCryptPasswordEncoder bc;

	@Override
	public UsuarioResponse createUser(UsuarioDto user) throws Exception {
		Optional<Usuario> op = repository.findByEmail(user.getEmail());
		if(op.isPresent()) {
			throw new UserExistsException("Usuario já cadastrado.");
		}
		Usuario usuario = Usuario.builder()
				.email(user.getEmail())
				.senha(bc.encode(user.getSenha()))
				.codigoSeguranca(Util.gerarCodigoSeguranca())
				.role(this.roleService.findById(user.getRoleId()))
				.build();
        usuario = repository.save(usuario);
		return UsuarioResponse.builder().nome(usuario.getName()).email(usuario.getEmail()).role(usuario.getRole().getNome()).build();
	}

	@Override
	public Usuario getByUsername(String username) throws Exception {
		return repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
	}

	@Override
	public UsuarioResponse getUsuarioResponseByUsername(String username) throws Exception {
		Usuario user = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
		return UsuarioResponse.builder().role(user.getRole().getNome()).nome(user.getName()).email(user.getEmail()).build();
	}

	@Override
	public UsuarioResponse update(UsuarioDto user) throws Exception {
		Optional<Usuario> op = repository.findByEmail(user.getEmail());
		if(op.isEmpty()){
			throw new UserNotFoundException("User " + user.getEmail() + " not found");
		}
		Usuario usuario = op.get();
		if(user.getSenhaNova() != null && !user.getSenhaNova().isBlank()){
			if(usuario.isPasswordCorrect(user.getSenha())){
				usuario.setSenha(bc.encode(user.getSenhaNova()));
			} else {
				throw new WrongPasswordException("Senha atual incorreta.");
			}
		} else {
			usuario.setSenha(usuario.getSenha());
		}
		usuario.setRole(this.roleService.findById(user.getRoleId()));
		usuario.setName(user.getName());
		usuario.setEmail(user.getEmail());
		usuario = this.repository.save(usuario);
		return UsuarioResponse.builder().nome(usuario.getName()).email(usuario.getEmail()).role(usuario.getRole().getNome()).build();
	}
}
