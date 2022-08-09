package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.SocialMediaApplication;
import com.revature.exceptions.UserNotFoundException;
import com.revature.repositories.UserRepository;

@SpringBootTest(classes = SocialMediaApplication.class)
class FollowerServiceTests {

	@MockBean
	private UserRepository ur;

	@MockBean
	private UserService us;

	@Autowired
	private UserService sut;

	@Test
	void findByCredentialsNotExist() {
		assertThrows(UserNotFoundException.class, () -> sut.findByCredentials("doesnt@exist.com", "nopass"));
	}
}
