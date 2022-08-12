package com.revature.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.SocialMediaApplication;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import com.revature.services.AuthService;
import com.revature.services.UserService;

@SpringBootTest(classes = SocialMediaApplication.class)
class UserControllerTest {

    @MockBean
    private UserService us;
    
    @MockBean
    private AuthService as;
    
    @MockBean
    private UserRepository ur;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper om = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getUser() throws UserNotFoundException, Exception {
    	User u1 = new User(1,"test@email.com","password","test","user","google.com","testUser","google.com","Virginia","testUser");
		
		Mockito.when(us.findById(1)).thenReturn(Optional.of(u1));
		mockMvc.perform(
                get("/user-profile")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(u1)));
    }

    @Test
    void updateUser() throws UserNotFoundException, Exception {
        User expected = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
        Mockito.when(us.findById(1)).thenReturn(Optional.of(expected));
        expected.setEmail("newemail@email.com");
        
        mockMvc.perform(
                put("/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(expected)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(expected)));
        
        
    }
}