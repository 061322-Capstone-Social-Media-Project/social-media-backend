package com.revature.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Hobby;
public interface HobbyRepository extends JpaRepository<Hobby, Integer> {
	
	Optional<Hobby> findById(int id);
	Optional<Hobby> findByUserId(int userId);
	
}
