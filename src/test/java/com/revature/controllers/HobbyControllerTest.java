package com.revature.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.SocialMediaApplication;
import com.revature.dtos.HobbyRequest;
import com.revature.models.Hobby;
import com.revature.repositories.HobbyRepository;
import com.revature.services.HobbyService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

@SpringBootTest(classes = SocialMediaApplication.class)
public class HobbyControllerTest {
	
	@MockBean
	private HobbyService hs;
	
	@MockBean
	private HobbyRepository hr;
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	private ObjectMapper om = new ObjectMapper();
	
	@BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
	
	@Test
	void getHobbies() throws Exception{
		Optional<Hobby> h1 = Optional.of(new Hobby(1,"Swimming", "Sleeping","Coding",1));
		
		Mockito.when(hr.findByUserId(1)).thenReturn(h1);
		
		mockMvc.perform(
                get("/hobby")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .content(om.writeValueAsString(h1)))
                .andExpect(status().isOk());
	}
	
	@Test
	void updateHobby() throws Exception{
		 	Hobby expected = new Hobby(1,"Swimming", "Sleeping","Coding",1);
			Mockito.when(hs.getById(1)).thenReturn(Optional.of(expected));
	        expected.setHobby1("Test Covering");
	        
	        mockMvc.perform(
	                put("/hobby")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(om.writeValueAsString(expected)))
	                .andExpect(status().isOk())
	                .andExpect(content().json(om.writeValueAsString(expected)));
	        
	}
	
	@Test
	void createHobby() throws Exception{
		Hobby expected = new Hobby(1,"Swimming", "Sleeping","Coding",1);
		HobbyRequest hobbyRequest = new HobbyRequest(1,"Swimming", "Sleeping","Coding",1);
		Mockito.when(hs.upsert(expected)).thenReturn(expected);
        
        
        mockMvc.perform(
                post("/hobby")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(hobbyRequest)))
                .andExpect(status().isOk());
	        
	}
}
