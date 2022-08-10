package com.revature.controllers;


import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.HobbiesRequest;
import com.revature.models.Hobbies;
import com.revature.services.HobbiesService;

@RestController
@RequestMapping("/hobby")
//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class HobbiesController {

	private final HobbiesService hobbiesService;
	
	public HobbiesController(HobbiesService hobbiesService) {
		this.hobbiesService = hobbiesService;
	}
	
    @GetMapping
    public Optional<Hobbies> getAllHobbies(@RequestParam String id){
    	int idNum = Integer.parseInt(id);
		
		return this.hobbiesService.getByUserId(idNum);
    }
	
    @PutMapping
    public ResponseEntity<Hobbies> upsertHobby(@RequestBody HobbiesRequest hobbies) {
    	
    	Optional<Hobbies> h = hobbiesService.getById(hobbies.getId());
		
		if(!h.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		Hobbies toUpdate = h.get();
		toUpdate.setHobby1(hobbies.getHobby1());
		toUpdate.setHobby2(hobbies.getHobby2());
		toUpdate.setHobby3(hobbies.getHobby3());
		hobbiesService.upsert(toUpdate);
		
		return ResponseEntity.ok(toUpdate);
    	//return ResponseEntity.ok(this.hobbiesService.upsert(hobbies));
    }
    
    @PostMapping
    public ResponseEntity<Hobbies> createHobbies(@RequestBody HobbiesRequest hobbies){
    	
    	Hobbies H = new Hobbies();
    	H.setHobby1(hobbies.getHobby1());
    	H.setHobby2(hobbies.getHobby2());
    	H.setHobby3(hobbies.getHobby3());
    	H.setUserId(hobbies.getUserId());
    	
    	hobbiesService.upsert(H);
    	
    	return ResponseEntity.ok(H);
    }
	
}
