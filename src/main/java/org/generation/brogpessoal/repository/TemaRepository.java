package org.generation.brogpessoal.repository;

import java.util.List;

import org.generation.brogpessoal.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemaRepository extends JpaRepository <Tema, Long> {
	public List <Tema> findAllByDescricaoContainingIgnoreCase (String descricao);

}