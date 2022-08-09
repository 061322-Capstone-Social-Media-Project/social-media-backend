package com.revature.controllers;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.SocialMediaApplication;
import com.revature.dtos.UserDTO;
import com.revature.exceptions.AlreadyFollowingException;
import com.revature.exceptions.NotFollowingException;
import com.revature.keys.FollowerKey;
import com.revature.models.User;
import com.revature.services.FollowerService;

@SpringBootTest(classes = SocialMediaApplication.class)
class FollowerControllerTests {
	
	@MockBean
	private FollowerService fs;
	
	@Autowired
	private WebApplicationContext context;
	
	private ObjectMapper om = new ObjectMapper();
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Throwable {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	void getFollowingSuccess() throws JsonProcessingException, Exception {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);

		List<User> following = new ArrayList<>();
		following.add(u1);
		
		List<UserDTO> fdto = new ArrayList<>();
		following.forEach(u -> {
			UserDTO f = new UserDTO();
			f.setId(u.getId());
			f.setFirstName(u.getFirstName());
			f.setLastName(u.getLastName());
			fdto.add(f);
		});
		
		when(fs.getFollowingByFollower(u3, Pageable.unpaged())).thenReturn(following);
		
		mockMvc.perform(
				get("/following")
				.sessionAttr("user", u3))
			.andExpect(status().isOk())
			.andExpect(content().json(om.writeValueAsString(fdto)));
	}
	
	@Test
	void getFollowingNotLoggedIn() throws JsonProcessingException, Exception {
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);
		
		mockMvc.perform(
				get("/following")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(u3)))
			.andExpect(status().isUnauthorized());
	}
	
	@Test
	void getFollowers() throws JsonProcessingException, Exception {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u2 = new User(2, "adam@someemail.com", "password", "adam", "harbeck", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);

		List<User> followers = new ArrayList<>();
		followers.add(u3);
		followers.add(u2);
		
		List<UserDTO> fdto = new ArrayList<>();
		followers.forEach(u -> {
			UserDTO f = new UserDTO();
			f.setId(u.getId());
			f.setFirstName(u.getFirstName());
			f.setLastName(u.getLastName());
			fdto.add(f);
		});
		
		when(fs.getFollowersByFollowing(u1, Pageable.unpaged())).thenReturn(followers);
		
		mockMvc.perform(
				get("/followers")
				.sessionAttr("user", u1))
			.andExpect(status().isOk())
			.andExpect(content().json(om.writeValueAsString(fdto)));
	}
	
	@Test
	void getFollowersNotLoggedIn() throws JsonProcessingException, Exception {
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);
		
		mockMvc.perform(
				get("/followers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(u3)))
			.andExpect(status().isUnauthorized());
	}
	
	@Test
	void addFollowingSuccess() throws JsonProcessingException, Exception {
		FollowerKey fk = new FollowerKey(1,3);
		
		mockMvc.perform(
				post("/following")
				.sessionAttr("user", new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null))
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(fk)))
			.andExpect(status().isOk());
		
		Mockito.verify(fs).addFollowing(fk);
	}
	
	@Test
	void addFollowingAlreadyFollowing() throws JsonProcessingException, Exception {
		FollowerKey fk = new FollowerKey(1,3);
		
		doThrow(new AlreadyFollowingException()).when(fs).addFollowing(fk);
		
		mockMvc.perform(
				post("/following")
				.sessionAttr("user", new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null))
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(fk)))
			.andExpect(status().isBadRequest());	
	}
	
	@Test
	void addFollowingNotLoggedIn() throws JsonProcessingException, Exception {
		FollowerKey fk = new FollowerKey(1,3);
		
		mockMvc.perform(
				post("/following")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(fk)))
			.andExpect(status().isUnauthorized());
	}
	
	@Test
	void removeFollowingSuccess() throws JsonProcessingException, Exception {
		FollowerKey fk = new FollowerKey(1,3);
		
		mockMvc.perform(
				delete("/following")
				.sessionAttr("user", new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null))
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(fk)))
			.andExpect(status().isOk());
		
		Mockito.verify(fs).removeFollowing(fk);
	
	}
	
	@Test
	void removeFollowingNotFollowing() throws JsonProcessingException, Exception {
		FollowerKey fk = new FollowerKey(1,3);
		
		doThrow(new NotFollowingException()).when(fs).removeFollowing(fk);
		
		mockMvc.perform(
				delete("/following")
				.sessionAttr("user", new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null))
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(fk)))
			.andExpect(status().isBadRequest());	
	}
	
	@Test
	void removeFollowingNotLoggedIn() throws JsonProcessingException, Exception {
		FollowerKey fk = new FollowerKey(1,3);
		
		mockMvc.perform(
				delete("/following")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(fk)))
			.andExpect(status().isUnauthorized());
	}
}
