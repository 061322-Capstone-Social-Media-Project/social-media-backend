package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.repositories.HobbiesRepsoitory;
import com.revature.models.Hobbies;
import com.revature.models.User;


@Service
public class HobbiesService {

	private HobbiesRepsoitory hobbiesRepository;
	
	public HobbiesService(HobbiesRepsoitory hobbiesRepository) {
		this.hobbiesRepository = hobbiesRepository;
	}
	
	public List<Hobbies> getAll() {
		return this.hobbiesRepository.findAll();
	}
	
	public Hobbies upsert(Hobbies hobbies) {
		return this.hobbiesRepository.save(hobbies);
	}
	
	public Optional<Hobbies> getById(int id){
		return this.hobbiesRepository.findById(id);
	}
	
	public Optional<Hobbies> getByUserId(int userId){
		return this.hobbiesRepository.findByUserId(userId);
	}
	
}
