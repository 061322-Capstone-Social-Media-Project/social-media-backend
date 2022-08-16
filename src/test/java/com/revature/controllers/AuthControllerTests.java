package com.revature.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
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
import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.models.User;
import com.revature.services.AuthService;

@SpringBootTest(classes = SocialMediaApplication.class)
class AuthControllerTests {
	
	@MockBean
	private AuthService as;
	
	@Autowired
	private WebApplicationContext context;
	
	private ObjectMapper om = new ObjectMapper();
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Throwable {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	void loginSuccess() throws JsonProcessingException, Exception {
		LoginRequest req = new LoginRequest("calvin@gmail.com", "password");
		User u1 = new User(1, "calvin@gmail.com", "password", "calvin", "post", null, null, null, null, null);
		
		when(as.findByCredentials("calvin@gmail.com", "password")).thenReturn(Optional.of(u1));
		
		mockMvc.perform(
				post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(req)))
		.andExpect(status().isOk())
		.andExpect(request().sessionAttribute("user", u1))
		.andExpect(content().json(om.writeValueAsString(u1)));
		
	}
	
	@Test
	void loginBadRequest() throws JsonProcessingException, Exception {
		LoginRequest req = new LoginRequest("calvin@gmail.com", "password");
		when(as.findByCredentials("calvin@gmail.com", "password")).thenReturn(Optional.empty());
		
		mockMvc.perform(
				post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(req)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void logout() throws Exception {
		mockMvc.perform(
				post("/auth/logout")
				.sessionAttr("user", om.writeValueAsString(new User(1, "calvin@gmail.com", "password", "calvin", "post", null, null, null, null, null))))
		.andExpect(status().isOk())
		.andExpect(request().sessionAttributeDoesNotExist("user"));
	}
	
	@Test
	void registerSuccess() throws JsonProcessingException, Exception {
		RegisterRequest req = new RegisterRequest("calvin@gmail.com", "password", "Calvin", "Post", "mypic.png", "calpost", "mysite.com", "city, st", "cal-vin");
		User user = new User(0, "calvin@gmail.com", "password", "Calvin", "Post", "mypic.png", "calpost", "mysite.com", "city, st", "cal-vin");
		User expected = new User(1, "calvin@gmail.com", "password", "Calvin", "Post", "mypic.png", "calpost", "mysite.com", "city, st", "cal-vin");
		
		when(as.register(user)).thenReturn(expected);
		
		mockMvc.perform(
				post("/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(req)))
		.andExpect(status().isCreated())
		.andExpect(content().json(om.writeValueAsString(expected)));
	}

}
