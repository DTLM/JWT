package com.estudo.jwt.service;

import com.estudo.jwt.bean.dto.UsuarioDto;
import com.estudo.jwt.modal.Usuario;

public interface IUsuarioService {

	void createUser(UsuarioDto user) throws Exception;

	Usuario getByUsername(String username) throws Exception;

	Usuario update(UsuarioDto user) throws Exception;
}
