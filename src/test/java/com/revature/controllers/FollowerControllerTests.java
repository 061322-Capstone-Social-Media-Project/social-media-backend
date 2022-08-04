package com.revature.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.SocialMediaApplication;
import com.revature.advice.AuthAspect;
import com.revature.exceptions.AlreadyFollowingException;
import com.revature.keys.FollowerKey;
import com.revature.models.User;
import com.revature.services.FollowerService;

@SpringBootTest(classes = SocialMediaApplication.class)
public class FollowerControllerTests {
	
	@MockBean
	private FollowerService fs;
	
	@Autowired
	private WebApplicationContext context;
	
	private ObjectMapper om = new ObjectMapper();
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() throws Throwable {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void getFollowing() throws JsonProcessingException, Exception {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post");
		User u2 = new User(2, "adam@someemail.com", "password", "adam", "harbeck");
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff");

		Set<User> following = new HashSet<>();
		following.add(u1);
		
		when(fs.getFollowingByFollower(u3)).thenReturn(following);
		
		mockMvc.perform(
				get("/following")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(u3)))
			.andExpect(status().isOk())
			.andExpect(content().json(om.writeValueAsString(following)));
	}
	
	@Test
	public void getFollowers() throws JsonProcessingException, Exception {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post");
		User u2 = new User(2, "adam@someemail.com", "password", "adam", "harbeck");
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff");

		Set<User> followers = new HashSet<>();
		followers.add(u3);
		
		when(fs.getFollowersByFollowing(u1)).thenReturn(followers);
		
		mockMvc.perform(
				get("/followers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(u1)))
			.andExpect(status().isOk())
			.andExpect(content().json(om.writeValueAsString(followers)));
	}
	
	@Test
	public void addFollowingSuccess() throws JsonProcessingException, Exception {
		FollowerKey fk = new FollowerKey(1,3);
		
		mockMvc.perform(
				post("/following")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(fk)))
			.andExpect(status().isOk());
		
		Mockito.verify(fs).addFollowing(fk);
	}
	
	@Test
	public void addFollowingFailure() throws JsonProcessingException, Exception {
		FollowerKey fk = new FollowerKey(1,3);
		
		doThrow(new AlreadyFollowingException()).when(fs).addFollowing(fk);
		
		mockMvc.perform(
				post("/following")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(fk)))
			.andExpect(status().isBadRequest());	
	}
	
	@Test
	public void deleteFollowingSuccess() throws JsonProcessingException, Exception {
		FollowerKey fk = new FollowerKey(1,3);
		
		mockMvc.perform(
				delete("/following")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(fk)))
			.andExpect(status().isOk());
		
		Mockito.verify(fs).removeFollowing(fk);
	
	}
}
