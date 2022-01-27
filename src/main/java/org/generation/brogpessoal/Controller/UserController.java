package org.generation.brogpessoal.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.brogpessoal.model.UserLogin;
import org.generation.brogpessoal.model.UserModel;
import org.generation.brogpessoal.repository.UserRepository;
import org.generation.brogpessoal.services.BlogUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario") // trata-se de um end point, ou seja, uma página principal da qual podem derivar
							// outras (usuario/experiencia)
@CrossOrigin(allowedHeaders = "*", origins = "*")

public class UserController {

	private @Autowired BlogUserServices services;
	private @Autowired UserRepository repository;
	
	@GetMapping("/all")
	public ResponseEntity <List<UserModel>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{idUser}")
	public ResponseEntity<?> getUserById(@PathVariable(value = "idUser") Long idUser) {
		return ResponseEntity.status(200).body(repository.findById(idUser));
	}
	
	@GetMapping("/{name}")
	public ResponseEntity<?> getUserByName(@PathVariable(value = "name") String name) {
		return ResponseEntity.status(200).body(repository.findAllByNameContainingIgnoreCase(name));
	}
	

		
	@PostMapping("/cadastro")
	public ResponseEntity<UserModel> save(@Valid @RequestBody UserModel newUser) {
		return services.registerUser(newUser);
	}

	@PutMapping("/credenciais")
	public ResponseEntity<UserLogin> credentials(@Valid @RequestBody UserLogin user) {
		return services.getCredentials(user);
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<UserModel> putUsuario(@Valid @RequestBody UserModel usuario){
		return services.atualizarUsuario(usuario)
			.map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
			.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@DeleteMapping("delete/{idPostagem}")
	public void delete(@PathVariable(value = "idPostagem") Long id) {
		repository.deleteById(id);
	}
}
