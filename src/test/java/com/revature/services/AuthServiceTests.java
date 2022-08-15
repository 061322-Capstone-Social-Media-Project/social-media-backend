package com.revature.services;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.SocialMediaApplication;
import com.revature.models.User;

@SpringBootTest(classes = SocialMediaApplication.class)
public class AuthServiceTests {
	
	@MockBean
	private UserService us;
	
	@Autowired
	private AuthService sut;

	@Test
	void findByCredentialsExists() {
		Optional<User> expected = Optional.of(new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null));
		
		when(us.findByCredentials("calvin@someemail.com", "password")).thenReturn(Optional.of(new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null)));
		
		Optional<User> actual = sut.findByCredentials("calvin@someemail.com", "password");
		assertEquals(expected, actual);
	}
	
	@Test
	void findByCredentialsNotExists() {
		Optional<User> expected = Optional.empty();
		
		when(us.findByCredentials("calvin@someemail.com", "password")).thenReturn(Optional.empty());
		
		Optional<User> actual = sut.findByCredentials("calvin@someemail.com", "password");
		assertEquals(expected, actual);
	}
	
	@Test
	void registerSuccess() {
		User toCreate = new User(0, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		
		when(us.save(toCreate)).thenReturn(new User(3, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null));

		User expected = new User(3, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User actual = sut.register(toCreate);
		
		assertEquals(expected, actual);
		
	}
	
}
