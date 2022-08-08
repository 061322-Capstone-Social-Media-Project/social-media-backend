package com.revature.services;

import com.revature.dtos.UserDTO;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
    
    public User getUserById(int id) {
    	return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    public List<User> searchUserByFirstNameOrLastName(String inputString) {
        return userRepository.findByInputString(inputString);
    }


}
