package com.revature.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.revature.dtos.HobbyRequest;
import com.revature.models.Hobby;
import com.revature.services.HobbyService;

@SpringBootTest(classes = SocialMediaApplication.class)
class HobbyControllerTests {

	@MockBean
	private HobbyService hs;

	@Autowired
	private WebApplicationContext context;

	private ObjectMapper om = new ObjectMapper();
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Throwable {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void getHobbiesSuccess() throws JsonProcessingException, Exception {
		Hobby h = new Hobby(1, "coding", "testing", "chilling", 1);

		when(hs.getByUserId(1)).thenReturn(Optional.of(h));

		mockMvc.perform(
				get("/hobby?id=1"))
		.andExpect(status().isOk())
		.andExpect(content().json(om.writeValueAsString(h)));
	}

	@Test
	void getHobbiesBadRequest() throws Exception {
		when(hs.getById(1)).thenReturn(Optional.empty());

		mockMvc.perform(
				get("/hobby?id=1"))
		.andExpect(status().isOk());
	}
	
	@Test
	void upsertHobbySuccess() throws JsonProcessingException, Exception {
		HobbyRequest hr = new HobbyRequest(1, "coding", "testing", "chilling", 1);
		Hobby oldHobby = new Hobby(1, "coding", null, null, 1);
		
		Hobby expected = new Hobby(1, "coding", "testing", "chilling", 1);
		
		when(hs.getById(1)).thenReturn(Optional.of(oldHobby));
		
		mockMvc.perform(
				put("/hobby")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(hr)))
		.andExpect(status().isOk())
		.andExpect(content().json(om.writeValueAsString(expected)));
		
		verify(hs).upsert(expected);
	}
	
	@Test
	void upsertHobbyBadRequest() throws JsonProcessingException, Exception {
		HobbyRequest hr = new HobbyRequest(1, "coding", "testing", "chilling", 1);
		
		when(hs.getById(1)).thenReturn(Optional.empty());
		
		mockMvc.perform(
				put("/hobby")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(hr)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void createHobby() throws JsonProcessingException, Exception {
		HobbyRequest hr = new HobbyRequest(0, "coding", "testing", "chilling", 1);
		Hobby newHobby = new Hobby(0, "coding", "testing", "chilling", 1);
		Hobby expected = new Hobby(1, "coding", "testing", "chilling", 1);
		
		when(hs.upsert(newHobby)).thenReturn(expected);
		
		mockMvc.perform(
				post("/hobby")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(hr)))
		.andExpect(status().isOk())
		.andExpect(content().json(om.writeValueAsString(expected)));
	}
	
}
