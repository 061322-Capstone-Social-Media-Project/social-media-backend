package com.revature.services;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByCredentials(String email, String password) {
    	Optional<User> u = userRepository.findByEmailAndPassword(email, password);
    	
    	if (u.isEmpty()) {
    		throw new UserNotFoundException();
    	}
    	
        return userRepository.findByEmailAndPassword(email, password);
    }
    
    public Optional<User> findById(int id){
    	return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
