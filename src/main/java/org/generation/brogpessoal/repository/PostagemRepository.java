package org.generation.brogpessoal.repository;

import java.util.List;

import org.generation.brogpessoal.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {

	public List<Postagem> findAllBytituloContainingIgnoreCase (String titulo);
	
}
