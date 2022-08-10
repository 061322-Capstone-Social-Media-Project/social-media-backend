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
    public Optional<Hobby> getAllHobbies(@RequestParam String id){
    	int idNum = Integer.parseInt(id);
		
		return this.hobbyService.getByUserId(idNum);
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
    	
    	Hobby H = new Hobby();
    	H.setHobby1(hobby.getHobby1());
    	H.setHobby2(hobby.getHobby2());
    	H.setHobby3(hobby.getHobby3());
    	H.setUserId(hobby.getUserId());
    	
    	hobbyService.upsert(H);
    	
    	return ResponseEntity.ok(H);
    }
	
}
