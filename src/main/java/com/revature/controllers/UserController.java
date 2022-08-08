package com.revature.controllers;

import com.revature.dtos.UserDTO;
import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUser(@RequestParam String inputString){
        return ResponseEntity.ok(userService.searchUserByFirstNameOrLastName(inputString));
    }

}
