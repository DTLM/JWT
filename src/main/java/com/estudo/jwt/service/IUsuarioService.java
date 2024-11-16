package com.estudo.jwt.service;

import com.estudo.jwt.bean.dto.UsuarioDto;
import com.estudo.jwt.bean.dto.UsuarioResponse;
import com.estudo.jwt.modal.Usuario;

public interface IUsuarioService {

	UsuarioResponse createUser(UsuarioDto user) throws Exception;

	Usuario getByUsername(String username) throws Exception;

	UsuarioResponse update(UsuarioDto user) throws Exception;
}
