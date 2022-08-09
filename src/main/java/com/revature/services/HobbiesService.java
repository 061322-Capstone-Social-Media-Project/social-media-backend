package com.revature.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.repositories.HobbiesRepsoitory;
import com.revature.models.Hobbies;


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
}
