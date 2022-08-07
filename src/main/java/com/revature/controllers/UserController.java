package com.revature.controllers;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.services.UserService;
import com.revature.dtos.UpdateRequest;
import com.revature.models.User;

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
		toUpdate.setProfilePic(updateRequest.getProfilePic());
		toUpdate.setNamePronunciation(updateRequest.getNamePronunciation());
		toUpdate.setProfessionalURL(updateRequest.getProfessionalURL());
		
		userService.save(toUpdate);
		
		return ResponseEntity.ok(u.get());
	}
	
	
	

}
