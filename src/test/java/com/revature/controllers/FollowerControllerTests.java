package com.revature.controllers;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.SocialMediaApplication;
import com.revature.dtos.SearchRequest;
import com.revature.exceptions.AlreadyFollowingException;
import com.revature.exceptions.NotFollowingException;
import com.revature.keys.FollowerKey;
import com.revature.models.User;
import com.revature.services.FollowerService;
import com.revature.services.UserService;

@SpringBootTest(classes = SocialMediaApplication.class)
class FollowerControllerTests {
	
	@MockBean
	private FollowerService fs;
	
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
	void isFollowingTrue() throws JsonProcessingException, Exception {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		FollowerKey fk = new FollowerKey(1, 2);
		
		when(fs.isFollowing(fk)).thenReturn(true);
		
		Map<String, Boolean> map = Collections.singletonMap("following", true);
		
		mockMvc.perform(
				post("/followed")
				.sessionAttr("user", u1)
		.contentType(MediaType.APPLICATION_JSON)
		.content(om.writeValueAsString(fk)))
			.andExpect(status().isOk())
			.andExpect(content().json(om.writeValueAsString(map)));
	}
	
	@Test
	void isFollowingFalse() throws JsonProcessingException, Exception {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		FollowerKey fk = new FollowerKey(1, 2);
		
		when(fs.isFollowing(fk)).thenReturn(false);
		
		Map<String, Boolean> map = Collections.singletonMap("following", false);
		
		mockMvc.perform(
				post("/followed")
				.sessionAttr("user", u1)
		.contentType(MediaType.APPLICATION_JSON)
		.content(om.writeValueAsString(fk)))
			.andExpect(status().isOk())
			.andExpect(content().json(om.writeValueAsString(map)));
	}
	
	@Test
	void countFollowingSuccess() throws JsonProcessingException, Exception {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		
		when(us.findById(1)).thenReturn(Optional.of(u1));
		when(fs.countFollowingByUserFollowing(u1)).thenReturn(5l);
		
		Map<String, Long> map = Collections.singletonMap("count", 5l);
		
		mockMvc.perform(
				get("/following/user/1/count")
				.sessionAttr("user", u1))
			.andExpect(status().isOk())
			.andExpect(content().json(om.writeValueAsString(map)));
	}
	
	@Test
	void countFollowingUserNotFound() throws JsonProcessingException, Exception {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		
		when(us.findById(1)).thenReturn(Optional.empty());
		
		mockMvc.perform(
				get("/following/user/1/count")
				.sessionAttr("user", u1))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	void countFollowersSuccess() throws JsonProcessingException, Exception {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		
		when(us.findById(1)).thenReturn(Optional.of(u1));
		when(fs.countFollowersByUserFollowed(u1)).thenReturn(5l);
		
		Map<String, Long> map = Collections.singletonMap("count", 5l);
		
		mockMvc.perform(
				get("/followers/user/1/count")
				.sessionAttr("user", u1))
			.andExpect(status().isOk())
			.andExpect(content().json(om.writeValueAsString(map)));
	}
	
	@Test
	void countFollowersUserNotFound() throws JsonProcessingException, Exception {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		
		when(us.findById(1)).thenReturn(Optional.empty());
		
		mockMvc.perform(
				get("/followers/user/1/count")
				.sessionAttr("user", u1))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	void getFollowingUnpaged() throws JsonProcessingException, Exception {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);

		List<User> following = new ArrayList<>();
		following.add(u1);
		
		List<SearchRequest> fdto = new ArrayList<>();
		following.forEach(u -> {
			SearchRequest f = new SearchRequest();
			f.setId(u.getId());
			f.setFirstName(u.getFirstName());
			f.setLastName(u.getLastName());
			f.setEmail(u.getEmail());
			f.setLocation(u.getLocation());
			f.setNamePronunciation(u.getNamePronunciation());
			f.setProfessionalURL(u.getProfessionalURL());
			f.setProfilePic(u.getProfilePic());
			f.setUsername(u.getUsername());
			fdto.add(f);
		});
		
		when(us.findById(3)).thenReturn(Optional.of(u3));
		when(fs.getFollowingByFollower(u3, Pageable.unpaged())).thenReturn(following);
		
		mockMvc.perform(
				get("/following/user/3")
				.sessionAttr("user", u3))
			.andExpect(status().isOk())
			.andExpect(content().json(om.writeValueAsString(fdto)));
	}
	
	@Test
	void getFollowingLimit() throws JsonProcessingException, Exception {
		User u1 = new User(1, "calvin@someemail.com", "password", "Calvin", "Post", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "Robert", "Ratcliff", null, null, null, null, null);
		User u2 = new User(3, "adam@someemail.com", "password", "Adam", "Harbeck", null, null, null, null, null);
		User u4 = new User(3, "shouchuang@someemail.com", "password", "Shouchuang", "Zhu", null, null, null, null, null);

		List<User> following = new ArrayList<>();
		following.add(u1);
		following.add(u2);
		following.add(u4);
		
		List<SearchRequest> fdto = new ArrayList<>();
		following.forEach(u -> {
			SearchRequest f = new SearchRequest();
			f.setId(u.getId());
			f.setFirstName(u.getFirstName());
			f.setLastName(u.getLastName());
			f.setEmail(u.getEmail());
			f.setLocation(u.getLocation());
			f.setNamePronunciation(u.getNamePronunciation());
			f.setProfessionalURL(u.getProfessionalURL());
			f.setProfilePic(u.getProfilePic());
			f.setUsername(u.getUsername());
			fdto.add(f);
		});
		
		when(us.findById(3)).thenReturn(Optional.of(u3));
		when(fs.getFollowingByFollower(u3, PageRequest.of(0, 3))).thenReturn(following);
		
		mockMvc.perform(
				get("/following/user/3?limit=3")
				.sessionAttr("user", u3))
			.andExpect(status().isOk())
			.andExpect(content().json(om.writeValueAsString(fdto)));
	}
	
	@Test
	void getFollowingLimitAndOffset() throws JsonProcessingException, Exception {
		User u1 = new User(1, "calvin@someemail.com", "password", "Calvin", "Post", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "Robert", "Ratcliff", null, null, null, null, null);
		User u2 = new User(3, "adam@someemail.com", "password", "Adam", "Harbeck", null, null, null, null, null);
		User u4 = new User(3, "shouchuang@someemail.com", "password", "Shouchuang", "Zhu", null, null, null, null, null);

		List<User> following = new ArrayList<>();
		following.add(u1);
		following.add(u2);
		following.add(u4);
		
		List<SearchRequest> fdto = new ArrayList<>();
		following.forEach(u -> {
			SearchRequest f = new SearchRequest();
			f.setId(u.getId());
			f.setFirstName(u.getFirstName());
			f.setLastName(u.getLastName());
			f.setEmail(u.getEmail());
			f.setLocation(u.getLocation());
			f.setNamePronunciation(u.getNamePronunciation());
			f.setProfessionalURL(u.getProfessionalURL());
			f.setProfilePic(u.getProfilePic());
			f.setUsername(u.getUsername());
			fdto.add(f);
		});
		
		when(us.findById(3)).thenReturn(Optional.of(u3));
		when(fs.getFollowingByFollower(u3, PageRequest.of(3, 3))).thenReturn(following);
		
		mockMvc.perform(
				get("/following/user/3?offset=3&limit=3")
				.sessionAttr("user", u3))
			.andExpect(status().isOk())
			.andExpect(content().json(om.writeValueAsString(fdto)));
	}
	
	/*
	@Test
	void getFollowingNotLoggedIn() throws JsonProcessingException, Exception {
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);
		
		mockMvc.perform(
				get("/following/user/3")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(u3)))
			.andExpect(status().isUnauthorized());
	}
	*/
	
	@Test
	void getFollowersUnpaged() throws JsonProcessingException, Exception {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u2 = new User(2, "adam@someemail.com", "password", "adam", "harbeck", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);

		List<User> followers = new ArrayList<>();
		followers.add(u3);
		followers.add(u2);
		
		List<SearchRequest> fdto = new ArrayList<>();
		followers.forEach(u -> {
			SearchRequest f = new SearchRequest();
			f.setId(u.getId());
			f.setFirstName(u.getFirstName());
			f.setLastName(u.getLastName());
			f.setEmail(u.getEmail());
			f.setLocation(u.getLocation());
			f.setNamePronunciation(u.getNamePronunciation());
			f.setProfessionalURL(u.getProfessionalURL());
			f.setProfilePic(u.getProfilePic());
			f.setUsername(u.getUsername());
			fdto.add(f);
		});
		
		when(us.findById(1)).thenReturn(Optional.of(u1));
		when(fs.getFollowersByFollowing(u1, Pageable.unpaged())).thenReturn(followers);
		
		mockMvc.perform(
				get("/followers/user/1")
				.sessionAttr("user", u1))
			.andExpect(status().isOk())
			.andExpect(content().json(om.writeValueAsString(fdto)));
	}
	
	@Test
	void getFollowersLimit() throws JsonProcessingException, Exception {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u2 = new User(2, "adam@someemail.com", "password", "adam", "harbeck", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);

		List<User> followers = new ArrayList<>();
		followers.add(u3);
		followers.add(u2);
		
		List<SearchRequest> fdto = new ArrayList<>();
		followers.forEach(u -> {
			SearchRequest f = new SearchRequest();
			f.setId(u.getId());
			f.setFirstName(u.getFirstName());
			f.setLastName(u.getLastName());
			f.setEmail(u.getEmail());
			f.setLocation(u.getLocation());
			f.setNamePronunciation(u.getNamePronunciation());
			f.setProfessionalURL(u.getProfessionalURL());
			f.setProfilePic(u.getProfilePic());
			f.setUsername(u.getUsername());
			fdto.add(f);
		});
		
		when(us.findById(1)).thenReturn(Optional.of(u1));
		when(fs.getFollowersByFollowing(u1, PageRequest.of(0, 3))).thenReturn(followers);
		
		mockMvc.perform(
				get("/followers/user/1?limit=3")
				.sessionAttr("user", u1))
			.andExpect(status().isOk())
			.andExpect(content().json(om.writeValueAsString(fdto)));
	}
	
	@Test
	void getFollowersLimitAndOffset() throws JsonProcessingException, Exception {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u2 = new User(2, "adam@someemail.com", "password", "adam", "harbeck", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);

		List<User> followers = new ArrayList<>();
		followers.add(u3);
		followers.add(u2);
		
		List<SearchRequest> fdto = new ArrayList<>();
		followers.forEach(u -> {
			SearchRequest f = new SearchRequest();
			f.setId(u.getId());
			f.setFirstName(u.getFirstName());
			f.setLastName(u.getLastName());
			f.setEmail(u.getEmail());
			f.setLocation(u.getLocation());
			f.setNamePronunciation(u.getNamePronunciation());
			f.setProfessionalURL(u.getProfessionalURL());
			f.setProfilePic(u.getProfilePic());
			f.setUsername(u.getUsername());
			fdto.add(f);
		});
		
		when(us.findById(1)).thenReturn(Optional.of(u1));
		when(fs.getFollowersByFollowing(u1, PageRequest.of(3, 3))).thenReturn(followers);
		
		mockMvc.perform(
				get("/followers/user/1?offset=3&limit=3")
				.sessionAttr("user", u1))
			.andExpect(status().isOk())
			.andExpect(content().json(om.writeValueAsString(fdto)));
	}
	
	@Test
	void getFollowersBadRequest() throws JsonProcessingException, Exception {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		
		when(us.findById(5)).thenReturn(Optional.empty());
		
		mockMvc.perform(
				get("/followers/user/5")
				.sessionAttr("user", u1))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	void getFollowingBadRequest() throws JsonProcessingException, Exception {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		
		when(us.findById(5)).thenReturn(Optional.empty());
		
		mockMvc.perform(
				get("/following/user/5")
				.sessionAttr("user", u1))
			.andExpect(status().isBadRequest());
	}
	
	/*
	@Test
	void getFollowersNotLoggedIn() throws JsonProcessingException, Exception {
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);
		
		mockMvc.perform(
				get("/followers/user/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(u3)))
			.andExpect(status().isUnauthorized());
	}
	*/
	
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
	
	/*
	@Test
	void addFollowingNotLoggedIn() throws JsonProcessingException, Exception {
		FollowerKey fk = new FollowerKey(1,3);
		
		mockMvc.perform(
				post("/following")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(fk)))
			.andExpect(status().isUnauthorized());
	}
	*/
	
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
	
	/*
	@Test
	void removeFollowingNotLoggedIn() throws JsonProcessingException, Exception {
		FollowerKey fk = new FollowerKey(1,3);
		
		mockMvc.perform(
				delete("/following")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(fk)))
			.andExpect(status().isUnauthorized());
	}
	*/
}
