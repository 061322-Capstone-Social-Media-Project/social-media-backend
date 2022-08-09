package com.revature.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Hobbies;
public interface HobbiesRepsoitory extends JpaRepository<Hobbies, Integer> {
	
	Optional<Hobbies> getById(int id);
}
