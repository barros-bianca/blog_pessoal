package org.generation.brogpessoal.services;

import java.nio.charset.Charset;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.generation.brogpessoal.model.UserModel;
import org.generation.brogpessoal.model.dtos.UserCredentialsDTO;
import org.generation.brogpessoal.model.dtos.UserLoginDTO;
import org.generation.brogpessoal.model.dtos.UserRegisterDTO;
import org.generation.brogpessoal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BlogUserServices {

	private @Autowired UserRepository repository;
	private UserCredentialsDTO credentials;
	private UserModel user;

	private static String criptoPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

	private static String generatorToken(String email, String password) {
		String structure = email + ":" + password;
		byte[] structureBase64 = Base64.encodeBase64(structure.getBytes(Charset.forName("US-ASCII")));
		return new String(structureBase64);
	}
	
	private static String generatorTokenBasic(String email, String password) {
		String structure = email + ":" + password;
		byte[] structureBase64 = Base64.encodeBase64(structure.getBytes(Charset.forName("US-ASCII")));
		return "Basic" + new String(structureBase64);
		
		
	}

	public ResponseEntity<UserModel> registerUser(@Valid UserRegisterDTO newUser) {

		Optional<UserModel> optional = repository.findByEmail(newUser.getEmail());

		if (optional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Este email já se encontra cadastrado, favor realizar o login");
		} else {
			UserModel user = new UserModel();
			user.setName(newUser.getName());
			user.setEmail(newUser.getEmail());
			user.setToken(generatorToken(newUser.getEmail(), newUser.getPassword()));
			user.setPassword(criptoPassword(newUser.getPassword()));
			return ResponseEntity.status(201).body(repository.save(user));
		}
	}

	public ResponseEntity<UserCredentialsDTO> getCredentials(@Valid UserLoginDTO userDto) {
		return repository.findByEmail(userDto.getEmail()).map(resp -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			
			if (encoder.matches(userDto.getPassword(), resp.getPassword())) {
				
				credentials = new UserCredentialsDTO();
				credentials.setIdUser(resp.getIdUser());
				credentials.setEmail(resp.getEmail());
				credentials.setToken(resp.getToken());
				credentials.setTokenBasic(generatorTokenBasic(userDto.getEmail(), userDto.getPassword()));
				
				return ResponseEntity.status(200).body(credentials);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha incorreta!");
			}
			
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email incorreto!"));
		
	}

}

















