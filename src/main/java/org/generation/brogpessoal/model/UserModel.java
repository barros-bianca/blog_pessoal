package org.generation.brogpessoal.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_users")
public class UserModel {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long idUser;
	private String token;
	private String name;
	private String email;
	private String password;
	private String tokenBasic;
	private String photo;
	private String type;


	// Relations
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("UserModel")
	private List<Postagem> postagem = new ArrayList<>();

	
	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTokenBasic() {
		return tokenBasic;
	}

	public void setTokenBasic(String tokenBasic) {
		this.tokenBasic = tokenBasic;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

}
