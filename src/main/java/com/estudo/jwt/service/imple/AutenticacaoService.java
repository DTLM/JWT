package com.estudo.jwt.service.imple;

import com.estudo.jwt.bean.dto.AutenticacaoDto;
import com.estudo.jwt.bean.dto.AutenticacaoResponse;
import com.estudo.jwt.bean.dto.TokenDto;
import com.estudo.jwt.bean.dto.UsuarioDto;
import com.estudo.jwt.exception.DataInvalidException;
import com.estudo.jwt.exception.UserNotFoundException;
import com.estudo.jwt.model.Usuario;
import com.estudo.jwt.service.IAutenticacaoService;
import com.estudo.jwt.service.IJwtService;
import com.estudo.jwt.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author Thiago Martins
 * @version 1.0
 * @apiNote this service is used to create tokens
 */
@Service
public class AutenticacaoService implements IAutenticacaoService {

	private final IJwtService jwtService;
	private final IUsuarioService usuarioServiceImple;
	private final AuthenticationManager authenticationManager;

	@Autowired
	public AutenticacaoService(IJwtService jwtService, UsuarioService usuarioService,AuthenticationManager authenticationManager) {
		this.jwtService = jwtService;
		this.usuarioServiceImple = usuarioService;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public TokenDto gerarToken(UsuarioDto user) throws Exception {
		if(user == null || !user.isValidDataToCreateToken()) {
			throw new DataInvalidException("Dados invalidos");
		}
		UserDetails userDetails = this.usuarioServiceImple.getByUsername(user.getEmail());
		if(userDetails == null) {
			throw new UserNotFoundException("Username " + user.getEmail() + " not found");
		}
		Usuario userAux = usuarioServiceImple.getByUsername(userDetails.getUsername());
		HashMap<String,Object> dados = new HashMap<>();
		dados.put("codigoSeguranca", userAux.getCodigoSeguranca());
		return TokenDto.builder().token(jwtService.gerarToken(dados, userDetails)).build();
	}

	@Override
	public AutenticacaoResponse autenticar(AutenticacaoDto autenticacaoDto) throws Exception {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(autenticacaoDto.getEmail(), autenticacaoDto.getSenha()));

		var user = usuarioServiceImple.getByUsername(autenticacaoDto.getEmail());
		if(user == null){
			throw new UserNotFoundException("usuario com o email: " + autenticacaoDto.getEmail() + " não encontrado.");
		}
		HashMap<String,Object> dados = new HashMap<>();
		dados.put("codigoSeguranca", user.getCodigoSeguranca());
		return AutenticacaoResponse.builder().token(jwtService.gerarToken(dados, user)).build();
	}
}
