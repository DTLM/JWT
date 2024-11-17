package com.estudo.jwt.service;

import com.estudo.jwt.bean.dto.UsuarioDto;
import com.estudo.jwt.bean.dto.UsuarioResponse;
import com.estudo.jwt.model.Usuario;

public interface IUsuarioService {

	UsuarioResponse createUser(UsuarioDto user) throws Exception;

	Usuario getByUsername(String username) throws Exception;
	UsuarioResponse getUsuarioResponseByUsername(String username) throws Exception;

	UsuarioResponse update(UsuarioDto user) throws Exception;
}
