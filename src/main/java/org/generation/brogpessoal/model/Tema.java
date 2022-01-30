package org.generation.brogpessoal.model;



import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name = "tb_tema")


public class Tema {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTema;
	
	
	@NotNull
	private String descricao;
	
	
	@OneToMany(cascade = CascadeType.REMOVE,mappedBy = "tema")
	@JsonIgnoreProperties ("tema")
	private List<Postagem> postagem;
	
	
	
	public long getIdTema() {
		return idTema;
	}
	public void setIdTema(long idTema) {
		this.idTema = idTema;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<Postagem> getPostagem() {
		return postagem;
	}
	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}
	
	
	
}
	
	