package com.revature.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.SocialMediaApplication;
import com.revature.dtos.SearchRequest;
import com.revature.dtos.UpdateRequest;
import com.revature.models.User;
import com.revature.services.UserService;

@SpringBootTest(classes = SocialMediaApplication.class)
class UserControllerTests {
	
	@MockBean
	private UserService us;
	
	@Autowired
	private WebApplicationContext context;
	
	private ObjectMapper om = new ObjectMapper();
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Throwable {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	void getUserSuccess() throws JsonProcessingException, Exception {
		User u = new User();
		u.setId(1);
		u.setEmail("testuser@gmail.com");
		u.setPassword("password");
		u.setFirstName("Test");
		u.setLastName("User");
		
		when(us.findById(1)).thenReturn(Optional.of(u));
		
		mockMvc.perform(
				get("/user-profile?id=1"))
		.andExpect(status().isOk())
		.andExpect(content().json(om.writeValueAsString(u)));
	}
	
	@Test
	void getUserBadRequest() throws JsonProcessingException, Exception {
		User u = new User();
		u.setId(1);
		u.setEmail("testuser@gmail.com");
		u.setPassword("password");
		u.setFirstName("Test");
		u.setLastName("User");
		
		when(us.findById(1)).thenReturn(Optional.empty());
		
		mockMvc.perform(
				get("/user-profile?id=1"))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void updateUserSuccess() throws Exception {
		UpdateRequest ur = new UpdateRequest(1, "testuser@gmail.com", "password", "New", "Name", "img.jpg", "justatest", "mysite.com", "Reston, VA", "test");
		User u = new User();
		u.setId(1);
		u.setEmail("testuser@gmail.com");
		u.setPassword("password");
		u.setFirstName("Test");
		u.setLastName("User");
		
		when(us.findById(1)).thenReturn(Optional.of(u));
		
		User expected = new User(1, "testuser@gmail.com", "password", "New", "Name", "img.jpg", "justatest", "mysite.com", "Reston, VA", "test");
		
		mockMvc.perform(
				put("/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(ur)))
		.andExpect(status().isOk())
		.andExpect(content().json(om.writeValueAsString(expected)));
		
		verify(us).save(expected);
	}
	
	@Test
	void updateUserBadRequest() throws Exception {
		UpdateRequest ur = new UpdateRequest(1, "testuser@gmail.com", "password", "New", "Name", "img.jpg", "justatest", "mysite.com", "Reston, VA", "test");
		
		when(us.findById(1)).thenReturn(Optional.empty());
		
		mockMvc.perform(
				put("/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(ur)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void searchUserSuccess() throws JsonProcessingException, Exception {
		List<SearchRequest> results = new ArrayList<>();
		SearchRequest s1 = new SearchRequest(1, "testuser@gmail.com", "Test", "User", null, null, null, null, null);
		SearchRequest s2 = new SearchRequest(1, "testuser@gmail.com", "Test", "User", null, null, null, null, null);
		
		results.add(s1);
		results.add(s2);
		
		when(us.searchUserByFirstNameOrLastName("test")).thenReturn(results);
		
		mockMvc.perform(
				get("/search?user=test"))
		.andExpect(status().isOk())
		.andExpect(content().json(om.writeValueAsString(results)));
	}

}
