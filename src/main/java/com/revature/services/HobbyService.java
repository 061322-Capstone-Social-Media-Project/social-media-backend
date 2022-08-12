package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.repositories.HobbyRepository;
import com.revature.models.Hobby;

@Service
public class HobbyService {

	private HobbyRepository hobbyRepository;
	
	public HobbyService(HobbyRepository hobbyRepository) {
		this.hobbyRepository = hobbyRepository;
	}
	
	public List<Hobby> getAll() {
		return this.hobbyRepository.findAll();
	}
	
	public Hobby upsert(Hobby hobby) {
		return this.hobbyRepository.save(hobby);
	}
	
	public Optional<Hobby> getById(int id){
		return this.hobbyRepository.findById(id);
	}
	
	public Optional<Hobby> getByUserId(int userId){
		return this.hobbyRepository.findByUserId(userId);
	}
	
}
