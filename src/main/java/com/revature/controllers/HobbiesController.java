package com.revature.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.models.Hobbies;
import com.revature.services.HobbiesService;

@RestController
@RequestMapping("/hobby")
public class HobbiesController {

	private final HobbiesService hobbiesService;
	
	public HobbiesController(HobbiesService hobbiesService) {
		this.hobbiesService = hobbiesService;
	}
	
	@Authorized
    @GetMapping
    public ResponseEntity<List<Hobbies>> getAllPosts() {
    	return ResponseEntity.ok(this.hobbiesService.getAll());
    }
	
    @Authorized
    @PutMapping
    public ResponseEntity<Hobbies> upsertPost(@RequestBody Hobbies hobbies) {
    	return ResponseEntity.ok(this.hobbiesService.upsert(hobbies));
    }
	
}
