package com.revature.controllers;


import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.HobbyRequest;
import com.revature.models.Hobby;
import com.revature.services.HobbyService;

@RestController
@RequestMapping("/hobby")
public class HobbyController {

	private final HobbyService hobbyService;
	
	public HobbyController(HobbyService hobbyService) {
		this.hobbyService = hobbyService;
	}
	
    @GetMapping
    public ResponseEntity<Hobby> getHobbies(@RequestParam int id){
    	Optional<Hobby> h = hobbyService.getByUserId(id);
		return h.isPresent() ? ResponseEntity.ok(h.get()) : ResponseEntity.ok().build();
    }
	
    @PutMapping
    public ResponseEntity<Hobby> upsertHobby(@RequestBody HobbyRequest hobbies) {
    	
    	Optional<Hobby> h = hobbyService.getById(hobbies.getId());
		
		if(!h.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		Hobby toUpdate = h.get();
		toUpdate.setHobby1(hobbies.getHobby1());
		toUpdate.setHobby2(hobbies.getHobby2());
		toUpdate.setHobby3(hobbies.getHobby3());
		hobbyService.upsert(toUpdate);
		
		return ResponseEntity.ok(toUpdate);
    }
    
    @PostMapping
    public ResponseEntity<Hobby> createHobby(@RequestBody HobbyRequest hobby){
    	
    	Hobby h = new Hobby();
    	h.setHobby1(hobby.getHobby1());
    	h.setHobby2(hobby.getHobby2());
    	h.setHobby3(hobby.getHobby3());
    	h.setUserId(hobby.getUserId());
    	
    	h = hobbyService.upsert(h);
    	
    	return ResponseEntity.ok(h);
    }
	
}
