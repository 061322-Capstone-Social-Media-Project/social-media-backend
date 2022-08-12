package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.SocialMediaApplication;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@SpringBootTest(classes = SocialMediaApplication.class)
public class UserServiceTests {
	@MockBean
	private UserRepository ur;
	
	@Autowired
	private UserService us;
	
	@Test
	void getUserSuccess() {
		Optional<User> expected = Optional.of(new User(1,"test@email.com","password","test","user","google.com","testUser","google.com","Virginia","testUser"));
		
		Mockito.when(ur.findByEmailAndPassword("test@email.com","password")).thenReturn(expected);
		
		Optional<User> actual = Optional.of(new User());
		actual = us.findByCredentials("test@email.com", "password");
		
		assertEquals(expected, actual);
	}

	@Test
	void getByIdSuccess() {
		Optional<User> expected = Optional.of(new User(1,"test@email.com","password","test","user","google.com","testUser","google.com","Virginia","testUser"));
		
		Mockito.when(ur.findById(1)).thenReturn(expected);
		
		Optional<User> actual = us.findById(1);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void checkUpdateSuccess() {
		Optional<User> uToBeUpdated = Optional.of(new User(1,"test@email.com","password","test","user","google.com","testUser","google.com","Virginia","testUser"));
		
		Mockito.when(ur.findById(1)).thenReturn(uToBeUpdated);
		
		Optional<User> u = uToBeUpdated;
		
		u.get().setEmail("newEmail@email.com");
		u.get().setPassword("newPassword");
		u.get().setFirstName("newFirstname");
		u.get().setLastName("newLastName");
		u.get().setLocation("newLocation");
		u.get().setNamePronunciation("newNamePro");
		u.get().setEmail("newUsername");
		u.get().setProfessionalURL("newURL");
		u.get().setProfilePic("newURL");
		ur.save(u.get());
		
		Optional<User> expected = ur.findById(1);
		
		
		Optional<User> actual = us.findById(1);
		
		assertEquals(expected, actual);
	}
	
}
