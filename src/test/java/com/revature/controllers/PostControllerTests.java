package com.revature.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.services.NotificationService;
import com.revature.services.PostService;

@SpringBootTest(classes = SocialMediaApplication.class)
class PostControllerTests {

	@MockBean
	private PostService ps;
	
	@MockBean
	private NotificationService ns;

	@Autowired
	private WebApplicationContext context;

	private ObjectMapper om = new ObjectMapper();
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Throwable {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	void getAllPosts() throws JsonProcessingException, Exception {
		List<Post> posts = new ArrayList<>();
		Post p1 = new Post(1, "A post", null, null, null, new User(1, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null));
		Post p2 = new Post(1, "Another post", null, null, null, new User(1, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null));
		posts.add(p1);
		posts.add(p2);
		
		when(ps.getMainPosts()).thenReturn(posts);
		
		mockMvc.perform(
				get("/post"))
		.andExpect(status().isOk())
		.andExpect(content().json(om.writeValueAsString(posts)));
	}
	
	@Test
	void upsertPost() throws JsonProcessingException, Exception {
		Post newPost = new Post(0, "A post", null, null, null, new User(1, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null));
		Post expected = new Post(1, "A post", null, null, null, new User(1, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null));
		
		doNothing().when(ns).commentNotification(newPost);
		when(ps.upsert(newPost)).thenReturn(expected);
		
		mockMvc.perform(
				put("/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(newPost)))
		.andExpect(status().isOk())
		.andExpect(content().json(om.writeValueAsString(expected)));
	}
	
	@Test
	void findById() throws JsonProcessingException, Exception {
		Post expected = new Post(1, "A post", null, null, null, new User(1, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null));
		
		when(ps.findById(1)).thenReturn(expected);
		
		mockMvc.perform(
				get("/post/1"))
		.andExpect(status().isOk())
		.andExpect(content().json(om.writeValueAsString(expected)));
	}
}
