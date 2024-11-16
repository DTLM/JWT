package com.estudo.jwt.controler;

import com.estudo.jwt.bean.dto.AutenticacaoDto;
import com.estudo.jwt.bean.dto.AutenticacaoResponse;
import com.estudo.jwt.bean.dto.TokenDto;
import com.estudo.jwt.bean.dto.UsuarioDto;
import com.estudo.jwt.service.IAutenticacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class AutenticacaoControler {

	private final IAutenticacaoService service;


	@PostMapping("/gerarToken")
	public ResponseEntity autenticar(@RequestBody UsuarioDto user) {
		try{
			TokenDto token = service.gerarToken(user);
			if(token == null){
				return new ResponseEntity("Erro ao gerar o token, verifique os dados e tente novamente.", HttpStatus.BAD_REQUEST);
			}
			return ResponseEntity.ok().body(token);
		} catch (Exception e){
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@PostMapping("/autenticar")
	public ResponseEntity autenticar(@RequestBody AutenticacaoDto autenticacaoDto){
		try{
			AutenticacaoResponse response = service.autenticar(autenticacaoDto);
			if(response == null){
				return new ResponseEntity("Erro ao autenticar o token, verifique os dados e tente novamente.", HttpStatus.BAD_REQUEST);
			}
			return ResponseEntity.ok().body(response);
		} catch (Exception e){
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}
