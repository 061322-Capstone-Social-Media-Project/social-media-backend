package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.dtos.SearchRequest;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Optional<User> findByCredentials(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	public Optional<User> findById(int id) {
		return userRepository.findById(id);
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public List<SearchRequest> searchUserByFirstNameOrLastName(String inputString) {
		List<User> users = userRepository.findByInputString(inputString);
		List<SearchRequest> userDTO = new ArrayList<>();
		users.forEach(user -> {
			SearchRequest s = new SearchRequest();
			s.setId(user.getId());
			s.setEmail(user.getEmail());
			s.setFirstName(user.getFirstName());
			s.setLastName(user.getLastName());
			s.setProfilePic(user.getProfilePic());
			s.setUsername(user.getUsername());
			s.setProfessionalURL(user.getProfessionalURL());
			s.setLocation(user.getLocation());
			s.setNamePronunciation(user.getNamePronunciation());
			userDTO.add(s);
		});
		return userDTO;
	}

}
