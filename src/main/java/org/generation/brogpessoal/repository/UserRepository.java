package org.generation.brogpessoal.repository;

import java.util.Optional;

import org.generation.brogpessoal.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

	Optional<UserModel> findByToken(String token);


	Optional<UserModel> findByEmail(String email);

}

