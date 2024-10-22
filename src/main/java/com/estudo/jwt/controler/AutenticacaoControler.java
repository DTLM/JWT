package com.estudo.jwt.controler;

import com.estudo.jwt.bean.dto.TokenDto;
import com.estudo.jwt.bean.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.jwt.service.IAutenticacaoService;

@RestController
@RequestMapping("/autenticate")
public class AutenticacaoControler {

	private final IAutenticacaoService service;

	@Autowired
	public AutenticacaoControler(IAutenticacaoService service) {
		this.service = service;
	}

	@GetMapping("/gerarToken")
	public ResponseEntity autenticar(@RequestBody UsuarioDto user) {
		try{
			TokenDto token = service.gerarToken(user);
			return ResponseEntity.ok().body(token);
		} catch (Exception e){
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}
