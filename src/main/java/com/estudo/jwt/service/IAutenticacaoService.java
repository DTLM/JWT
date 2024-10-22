package com.estudo.jwt.service;

import com.estudo.jwt.bean.dto.TokenDto;
import com.estudo.jwt.bean.dto.UsuarioDto;

public interface IAutenticacaoService {
	TokenDto gerarToken(UsuarioDto user) throws Exception;
}
