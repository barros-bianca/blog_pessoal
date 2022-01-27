package org.generation.brogpessoal.repository;

import java.util.List;
import java.util.Optional;

import org.generation.brogpessoal.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

	public Optional<UserModel> findAllByNameContainingIgnoreCase(String name);
	
	public Optional<UserModel> findByEmail(String email);
	
	public Optional<UserModel> findByToken(String token);
	
}

