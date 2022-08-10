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
		User u1 = new User(1, "test1@someemail.com", "password", "test1", "user1", null, null, null, null, null);
		User u2 = new User(2, "test2@someemail.com", "password", "test2", "user2", null, null, null, null, null);

		List<Hobby> expected = new ArrayList<>();
		expected.add(new Hobby(1, "Bike Riding", "Golfing", "Programming", 1));
		expected.add(new Hobby(2, "Hiking", "Sleeping", "Movies", 2));
		
		Mockito.when(hr.findAll()).thenReturn(expected);
		
		List<Hobby> actual = new ArrayList<>();
		actual = hs.getAll();
		
		assertEquals(expected, actual);
	}

//	@Test
//	void getFollowingSuccess() {
//		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
//		User u2 = new User(2, "adam@someemail.com", "password", "adam", "harbeck", null, null, null, null, null);
//		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);
//
//		List<Follower> following = new ArrayList<>();
//		following.add(new Follower(new FollowerKey(3, 2), u3, u2));
//		following.add(new Follower(new FollowerKey(3, 1), u3, u1));
//		
//		List<User> expected = new ArrayList<>();
//		following.forEach(f -> expected.add(f.getFollowing()));
//		
//		Mockito.when(fr.findFollowingByFollower(u3, Pageable.unpaged())).thenReturn(new PageImpl<>(following));
//		
//
//		List<User> actual = sut.getFollowingByFollower(u3, Pageable.unpaged());
//
//		assertEquals(expected, actual);
//	}
//
//	@Test
//	void addFollowingSuccess() {
//		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
//		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);
//
//		FollowerKey fk = new FollowerKey(u3.getId(), u1.getId());
//		Follower f = new Follower(fk, u3, u1);
//		
//		Mockito.when(fr.findById(fk)).thenReturn(Optional.empty());
//		
//		Mockito.when(us.findById(1)).thenReturn(Optional.of(u1));
//		Mockito.when(us.findById(3)).thenReturn(Optional.of(u3));
//		
//		sut.addFollowing(fk);
//
//		Mockito.verify(fr).save(f);
//	}
//
//	@Test
//	void addFollowingUserNotExist() {
//		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
//		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);
//
//		FollowerKey fk = new FollowerKey(u3.getId(), u1.getId());
//
//		Mockito.when(us.findById(fk.getFollowingId())).thenThrow(new UserNotFoundException());
//
//		assertThrows(UserNotFoundException.class, () -> sut.addFollowing(fk));
//	}
//
//	@Test
//	void addFollowingAlreadyFollowing() {
//		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
//		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);
//
//		FollowerKey fk = new FollowerKey(u3.getId(), u1.getId());
//		Follower f = new Follower(fk, u3, u1);
//
//		Mockito.when(fr.findById(fk)).thenReturn(Optional.of(f));
//
//		assertThrows(AlreadyFollowingException.class, () -> sut.addFollowing(fk));
//	}
//
//	@Test
//	void removeFollowingSuccess() {
//		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
//		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);
//
//		FollowerKey fk = new FollowerKey(u3.getId(), u1.getId());
//		Follower f = new Follower(fk, u3, u1);
//		
//		Mockito.when(fr.findById(fk)).thenReturn(Optional.of(f));
//		
//		sut.removeFollowing(fk);
//
//		Mockito.verify(fr).delete(f);
//	}
//	
//	@Test
//	void removeFollowingNotFollowing() {
//		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
//		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);
//
//		FollowerKey fk = new FollowerKey(u3.getId(), u1.getId());
//		
//		Mockito.when(fr.findById(fk)).thenThrow(new NotFollowingException());
//
//		assertThrows(NotFollowingException.class, () -> sut.removeFollowing(fk));
//	}

}
