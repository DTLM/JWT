package com.estudo.jwt.controler;

import com.estudo.jwt.bean.dto.UsuarioDto;
import com.estudo.jwt.bean.dto.UsuarioResponse;
import com.estudo.jwt.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Secured({"ROLE_ADM","ROLE_MANAGER"})
public class UsuarioControler {

	private final IUsuarioService service;
	
	@PostMapping("/createUsuario")
	public ResponseEntity createUsuario(@RequestBody UsuarioDto usuario) {
		try{
			if(usuario == null) {
				return ResponseEntity.badRequest().body("Usuario Nulo");
			}
			UsuarioResponse user = this.service.createUser(usuario);
			return ResponseEntity.ok().body(user);
		} catch (Exception e){
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@PatchMapping("/updateUsuario")
	public ResponseEntity updateUsuario(@RequestBody UsuarioDto usuario) {
		try{
			if(usuario == null) {
				return ResponseEntity.badRequest().body("Usuario Nulo");
			}
			UsuarioResponse user = this.service.update(usuario);
			return ResponseEntity.ok().body(user);
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
