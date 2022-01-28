package org.generation.brogpessoal.services;

import java.nio.charset.Charset;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.generation.brogpessoal.model.UserLogin;
import org.generation.brogpessoal.model.UserModel;
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
	private UserLogin credentials;
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

	public ResponseEntity<UserModel> registerUser(@Valid UserModel newUser) {

		Optional<UserModel> optional = repository.findAllByNameContainingIgnoreCase(newUser.getName());

		if (optional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Este email já se encontra cadastrado, favor realizar o login");
		} else {
			UserModel user = new UserModel();
			user.setName(newUser.getName());
			user.setEmail(newUser.getEmail());
			user.setToken(newUser.getToken());
			user.setPhoto(newUser.getPhoto());
			user.setType(newUser.getType());	
			
			user.setToken(generatorToken(newUser.getEmail(), newUser.getPassword()));
			user.setTokenBasic(generatorTokenBasic(newUser.getEmail(), newUser.getPassword()));
			user.setPassword(criptoPassword(newUser.getPassword()));
			return ResponseEntity.status(201).body(repository.save(user));
		}
	}

	public Optional<UserModel> atualizarUsuario(UserModel usuario) {
		if (repository.findById(usuario.getIdUser()).isPresent()) {
			Optional<UserModel> buscaUsuario = repository.findByEmail(usuario.getToken());
			if (buscaUsuario.isPresent()) {
				if (buscaUsuario.get().getIdUser() != usuario.getIdUser())
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
			}
			usuario.setPassword(criptoPassword(usuario.getPassword()));
			return Optional.of(repository.save(usuario));
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);
	}
	
	public ResponseEntity<UserLogin> getCredentials(@Valid UserLogin userDto) {
		return repository.findAllByNameContainingIgnoreCase(userDto.getName()).map(resp -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			if (encoder.matches(userDto.getPassword(), resp.getPassword())) {

				credentials = new UserLogin();
				credentials.setIdUser(resp.getIdUser());
				credentials.setName(resp.getName());
				credentials.setEmail(resp.getEmail());
				credentials.setToken(resp.getToken());
				credentials.setPhoto(resp.getPhoto());
				credentials.setType(resp.getType());				
				credentials.setTokenBasic(generatorTokenBasic(userDto.getEmail(), userDto.getPassword()));

				return ResponseEntity.status(200).body(credentials);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha incorreta!");
			}

		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email incorreto!"));

	}

}

















