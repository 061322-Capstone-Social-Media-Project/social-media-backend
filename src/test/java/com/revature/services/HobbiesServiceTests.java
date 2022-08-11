package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.revature.SocialMediaApplication;
import com.revature.exceptions.AlreadyFollowingException;
import com.revature.exceptions.NotFollowingException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.keys.FollowerKey;
import com.revature.models.Follower;
import com.revature.models.Hobby;
import com.revature.models.User;
import com.revature.repositories.HobbyRepository;

@SpringBootTest(classes = SocialMediaApplication.class)
class HobbiesServiceTests {

	@MockBean
	private HobbyRepository hr;

	@MockBean
	private UserService us;

	@Autowired
	private HobbyService hs;

	@Test
	void getHobbiesSuccess() {
		List<Hobby> expected = new ArrayList<>();
		expected.add(new Hobby(1, "Bike Riding", "Golfing", "Programming", 1));
		expected.add(new Hobby(2, "Hiking", "Sleeping", "Movies", 2));
		
		Mockito.when(hr.findAll()).thenReturn(expected);
		
		List<Hobby> actual = new ArrayList<>();
		actual = hs.getAll();
		
		assertEquals(expected, actual);
	}

	@Test
	void getByIdSuccess() {
		Optional<Hobby> expected = Optional.of(new Hobby(1, "Hiking", "Sleeping", "Movies", 1));
		
		Mockito.when(hr.findById(1)).thenReturn(expected);
		
		Optional<Hobby> actual = Optional.of(new Hobby());
		actual = hs.getById(1);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void getByUserIdSuccess() {
		Optional<Hobby> expected = Optional.of(new Hobby(1, "Hiking", "Sleeping", "Movies", 1));
		
		Mockito.when(hr.findByUserId(1)).thenReturn(expected);
		
		Optional<Hobby> actual = Optional.of(new Hobby());
		actual = hs.getByUserId(1);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void upsertSuccess() {
		Hobby h = new Hobby(1, "Hiking", "Sleeping", "Movies", 1);
		
		hs.upsert(h);

		Mockito.verify(hr).save(h);
	}
}
