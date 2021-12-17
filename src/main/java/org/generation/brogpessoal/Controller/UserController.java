package org.generation.brogpessoal.Controller;

import javax.validation.Valid;

import org.generation.brogpessoal.model.UserModel;
import org.generation.brogpessoal.model.dtos.UserCredentialsDTO;
import org.generation.brogpessoal.model.dtos.UserLoginDTO;
import org.generation.brogpessoal.model.dtos.UserRegisterDTO;
import org.generation.brogpessoal.repository.UserRepository;
import org.generation.brogpessoal.services.BlogUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

	@Autowired
	private UserRepository repository;

	@Autowired
	BlogUserServices service;

	@PostMapping("/cadastro")
	public ResponseEntity<UserModel> save(@Valid @RequestBody UserRegisterDTO newUser) {
		return service.registerUser(newUser);
	}

	@PutMapping("/credenciais")
	public ResponseEntity<UserCredentialsDTO> credentials(@Valid @RequestBody UserLoginDTO user) {
		return service.getCredentials(user);
	}

	@GetMapping("/{id_user}")
	public ResponseEntity<?> getUserById(@PathVariable(value = "id_user") Long id) {
		return ResponseEntity.status(200).body(repository.findById(id));
	}

}
