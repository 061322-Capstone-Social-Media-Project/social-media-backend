package com.revature.controllers;

import java.util.List;
import java.util.Optional;

import com.revature.dtos.SearchRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.UpdateRequest;
import com.revature.models.User;
import com.revature.services.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserController {
	private final UserService userService;
	
	public UserController(UserService service) {
		this.userService = service;
	}
	
	@GetMapping("/user-profile")
	@ResponseBody
	public Optional<User> getUser(@RequestParam String id) {
		int idNum = Integer.parseInt(id);
		return userService.findById(idNum);
	}
	
	@PutMapping("/update")
	public ResponseEntity<User> updateUser(@RequestBody UpdateRequest updateRequest){
		Optional<User> u = userService.findById(updateRequest.getId());
		
		if(!u.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		User toUpdate = u.get();
		toUpdate.setEmail(updateRequest.getEmail());
		toUpdate.setUsername(updateRequest.getUsername());
		toUpdate.setPassword(updateRequest.getPassword());
		toUpdate.setFirstName(updateRequest.getFirstName());
		toUpdate.setLastName(updateRequest.getLastName());
		toUpdate.setNamePronunciation(updateRequest.getNamePronunciation());
		toUpdate.setProfessionalURL(updateRequest.getProfessionalURL());
		toUpdate.setLocation(updateRequest.getLocation());	
		userService.save(toUpdate);
		
		return ResponseEntity.ok(toUpdate);
	}
	
	@GetMapping("/search")
    public ResponseEntity<List<SearchRequest>> searchUser(@RequestParam String user){
        return ResponseEntity.ok(userService.searchUserByFirstNameOrLastName(user));
    }

}
