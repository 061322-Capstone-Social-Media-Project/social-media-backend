package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

	@Autowired
	private UserService us;

	@Test
	void findByCredentialsNotExist() {

		Mockito.doThrow(IllegalArgumentException.class).when(ur).findByEmailAndPassword("doesnt@exist.com", "nopass");

		assertThrows(UserNotFoundException.class, () -> us.findByCredentials("doesnt@exist.com", "nopass"));
	}
	
	@Test
	void findByIdNotExist() {

		Mockito.doThrow(IllegalArgumentException.class).when(ur).findById(9999999);

		assertThrows(IllegalArgumentException.class, () -> us.findById(9999999));
	}
}
