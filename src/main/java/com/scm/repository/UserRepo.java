package com.scm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scm.entities.User;

public interface UserRepo   extends JpaRepository<User, String > {
	
	//  we can write extra db related operations 
	// also write custom querry  methods 
	// also we can add customs finder  methods  here 
	Optional<User> findByEmail(String email);
	
	Optional<User> findByEmailAndPassword(String email , String password);
	Optional<User> findByEmailToken(String id);

}
