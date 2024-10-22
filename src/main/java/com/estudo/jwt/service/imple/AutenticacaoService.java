package com.estudo.jwt.service.imple;

import com.estudo.jwt.bean.dto.TokenDto;
import com.estudo.jwt.bean.dto.UsuarioDto;
import com.estudo.jwt.exception.DataInvalidException;
import com.estudo.jwt.exception.UserNotFoundException;
import com.estudo.jwt.service.IAutenticacaoService;
import com.estudo.jwt.service.IJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Thiago Martins
 * @version 1.0
 * @apiNote this service is used to create tokens
 */
@Service
public class AutenticacaoService implements IAutenticacaoService {

	private final IJwtService jwtService;
	private UserDetailsService userDetailsServiceImple;

	@Autowired
	public AutenticacaoService(IJwtService jwtService, UserDetailsService userDetailsServiceImple) {
		this.jwtService = jwtService;
		this.userDetailsServiceImple = userDetailsServiceImple;
	}

	@Override
	public TokenDto gerarToken(UsuarioDto user) throws Exception {
		if(user == null || !user.isValidDataToCreateToken()) {
			throw new DataInvalidException("Dados invalidos");
		}
		UserDetails userDetails = this.userDetailsServiceImple.loadUserByUsername(user.getEmail());
		if(userDetails == null) {
			throw new UserNotFoundException("Username " + user.getEmail() + " not found");
		}
		return TokenDto.builder().token(jwtService.gerarToken(user.getDados(), userDetails)).build();
	}
}
