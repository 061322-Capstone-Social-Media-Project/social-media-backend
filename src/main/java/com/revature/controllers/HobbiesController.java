package com.revature.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.models.Hobbies;
import com.revature.services.HobbiesService;

@RestController
@RequestMapping("/hobby")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class HobbiesController {

	private final HobbiesService hobbiesService;
	
	public HobbiesController(HobbiesService hobbiesService) {
		this.hobbiesService = hobbiesService;
	}
	
    @GetMapping
    public Optional<Hobbies> getAllHobbies(@RequestParam String id){
    	int idNum = Integer.parseInt(id);
		
		return this.hobbiesService.getById(idNum);
    }
	
    @PutMapping
    public ResponseEntity<Hobbies> upsertHobby(@RequestBody Hobbies hobbies) {
    	return ResponseEntity.ok(this.hobbiesService.upsert(hobbies));
    }
	
}
