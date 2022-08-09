package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.dtos.UserDTO;
import com.revature.keys.FollowerKey;
import com.revature.models.User;
import com.revature.services.FollowerService;

@RestController
public class FollowerController {
	
	private FollowerService fs;

	public FollowerController(FollowerService fs) {
		super();
		this.fs = fs;
	}

	@Authorized
	@PostMapping(value="/followed")
	public ResponseEntity<Boolean> isFollowing(@RequestBody FollowerKey fk) {
		return ResponseEntity.ok(fs.isFollowing(fk));
	}
	
	@Authorized
	@GetMapping("/following")
	public ResponseEntity<List<UserDTO>> getFollowing(HttpSession session, @RequestParam Optional<Integer> offset, @RequestParam Optional<Integer> limit) {
		User currentUser = (User) session.getAttribute("user");
		List<UserDTO> fdto = new ArrayList<>();
		Pageable p;
		if (limit.isPresent()) {
			p = PageRequest.of(offset.isPresent() ? offset.get() : 0, limit.get());
		} else {
			p = Pageable.unpaged();
		}
		fs.getFollowingByFollower(currentUser, p).forEach(u -> {
			UserDTO f = new UserDTO();
			f.setId(u.getId());
			f.setFirstName(u.getFirstName());
			f.setLastName(u.getLastName());
			fdto.add(f);
		});
		return ResponseEntity.ok(fdto);
	}
	
	@Authorized
	@GetMapping("/followers")
	public ResponseEntity<List<UserDTO>> getFollowers(HttpSession session, @RequestParam Optional<Integer> offset, @RequestParam Optional<Integer> limit) {
		User currentUser = (User) session.getAttribute("user");
		List<UserDTO> fdto = new ArrayList<>();
		Pageable p;
		if (limit.isPresent()) {
			p = PageRequest.of(offset.isPresent() ? offset.get() : 0, limit.get());
		} else {
			p = Pageable.unpaged();
		}
		fs.getFollowersByFollowing(currentUser, p).forEach(u -> {
			UserDTO f = new UserDTO();
			f.setId(u.getId());
			f.setFirstName(u.getFirstName());
			f.setLastName(u.getLastName());
			fdto.add(f);
		});
		return ResponseEntity.ok(fdto);
	}
	
	@Authorized
	@PostMapping("/following")
	public ResponseEntity<String> addFollowing(@RequestBody FollowerKey fk) {
		fs.addFollowing(fk);
		return ResponseEntity.ok("Followed");
	}
	
	@Authorized
	@DeleteMapping("/following")
	public HttpStatus removeFollowing(@RequestBody FollowerKey fk) {
		fs.removeFollowing(fk);
		return HttpStatus.OK;
	}
}
