package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.SocialMediaApplication;
import com.revature.dtos.SearchRequest;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@SpringBootTest(classes = SocialMediaApplication.class)
class UserServiceTests {

	@MockBean
	private UserRepository ur;

	@Autowired
	private UserService sut;

	@Test
	void findByCredentialsTest() {
		User u = new User(1, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null);
		Optional<User> expected = Optional.of(new User(1, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null));
		
		when(ur.findByEmailAndPassword("testuser@gmail.com", "password")).thenReturn(Optional.of(u));
		
		Optional<User> actual = sut.findByCredentials("testuser@gmail.com", "password");
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdTest() {
		User u = new User(1, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null);
		Optional<User> expected = Optional.of(new User(1, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null));
		
		when(ur.findById(1)).thenReturn(Optional.of(u));
		
		Optional<User> actual = sut.findById(1);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveTest() {
		User u = new User(0, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null);
		User expected = new User(1, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null);
		
		when(ur.save(u)).thenReturn(expected);
		
		User actual = sut.save(u);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void searchUserTest() {
		List<User> users = new ArrayList<>();
		User u1 = new User(1, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null);
		User u2 = new User(2, "testuser2@gmail.com", "password", "Another", "User", null, null, null, null, null);
		users.add(u1);
		users.add(u2);
		
		List<SearchRequest> expected = new ArrayList<>();
		expected.add(new SearchRequest(1, "testuser@gmail.com", "Test", "User", null, null, null, null, null));
		expected.add(new SearchRequest(2, "testuser2@gmail.com", "Another", "User", null, null, null, null, null));
		
		when(ur.findByInputString("test")).thenReturn(users);
		
		List<SearchRequest> actual = sut.searchUserByFirstNameOrLastName("test");
		
		assertEquals(expected, actual);
	}

}
