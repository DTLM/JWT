package com.estudo.jwt.controler;

import com.estudo.jwt.bean.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.estudo.jwt.modal.Usuario;
import com.estudo.jwt.service.IUsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioControler {

	private final IUsuarioService service;
	
	@Autowired
	public UsuarioControler(IUsuarioService service) {
		this.service = service;
	}
	
	@PostMapping("/createUsuario")
	public ResponseEntity createUsuario(@RequestBody UsuarioDto usuario) {
		try{
			if(usuario == null) {
				return ResponseEntity.badRequest().body("Usuario Nulo");
			}
			return ResponseEntity.ok().body(usuario);
		} catch (Exception e){
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	@GetMapping("/hello")
	public ResponseEntity<String> getMessage(){
		return ResponseEntity.ok().body("Hello from private API, protected");
	}
}
