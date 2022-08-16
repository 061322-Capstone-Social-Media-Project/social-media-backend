package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import com.revature.models.User;
import com.revature.repositories.FollowerRepository;

@SpringBootTest(classes = SocialMediaApplication.class)
class FollowerServiceTests {

	@MockBean
	private FollowerRepository fr;

	@MockBean
	private UserService us;

	@Autowired
	private FollowerService sut;

	@Test
	void getFollowersSuccess() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u2 = new User(2, "adam@someemail.com", "password", "adam", "harbeck", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);

		List<Follower> followers = new ArrayList<>();
		followers.add(new Follower(new FollowerKey(1, 2), u2, u1));
		followers.add(new Follower(new FollowerKey(1, 3), u3, u1));
		
		List<User> expected = new ArrayList<>();
		followers.forEach(f -> expected.add(f.getFollower()));
		
		Mockito.when(fr.findFollowersByFollowing(u1, Pageable.unpaged())).thenReturn(new PageImpl<>(followers));
		

		List<User> actual = sut.getFollowersByFollowing(u1, Pageable.unpaged());

		assertEquals(expected, actual);
	}

	@Test
	void getFollowingSuccess() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u2 = new User(2, "adam@someemail.com", "password", "adam", "harbeck", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);

		List<Follower> following = new ArrayList<>();
		following.add(new Follower(new FollowerKey(3, 2), u3, u2));
		following.add(new Follower(new FollowerKey(3, 1), u3, u1));
		
		List<User> expected = new ArrayList<>();
		following.forEach(f -> expected.add(f.getFollowing()));
		
		Mockito.when(fr.findFollowingByFollower(u3, Pageable.unpaged())).thenReturn(new PageImpl<>(following));
		

		List<User> actual = sut.getFollowingByFollower(u3, Pageable.unpaged());

		assertEquals(expected, actual);
	}

	@Test
	void addFollowingSuccess() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);

		FollowerKey fk = new FollowerKey(u3.getId(), u1.getId());
		Follower f = new Follower(fk, u3, u1);
		
		when(fr.findById(fk)).thenReturn(Optional.empty());
		
		when(us.findById(1)).thenReturn(Optional.of(u1));
		when(us.findById(3)).thenReturn(Optional.of(u3));
		
		sut.addFollowing(fk);

		verify(fr).save(f);
	}

	@Test
	void addFollowingUserNotExist() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);

		FollowerKey fk = new FollowerKey(5, 1);

		when(us.findById(1)).thenReturn(Optional.of(u1));
		when(us.findById(5)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> sut.addFollowing(fk));
	}

	@Test
	void addFollowingAlreadyFollowing() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);

		FollowerKey fk = new FollowerKey(3, 1);
		Follower f = new Follower(fk, u3, u1);

		when(fr.findById(fk)).thenReturn(Optional.of(f));

		assertThrows(AlreadyFollowingException.class, () -> sut.addFollowing(fk));
	}

	@Test
	void removeFollowingSuccess() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);

		FollowerKey fk = new FollowerKey(3, 1);
		Follower f = new Follower(fk, u3, u1);
		
		when(fr.findById(fk)).thenReturn(Optional.of(f));
		
		sut.removeFollowing(fk);

		verify(fr).delete(f);
	}
	
	@Test
	void removeFollowingNotFollowing() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);

		FollowerKey fk = new FollowerKey(u3.getId(), u1.getId());
		
		when(fr.findById(fk)).thenReturn(Optional.empty());

		assertThrows(NotFollowingException.class, () -> sut.removeFollowing(fk));
	}
	
	@Test
	void countFollowers() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		
		long expected = 5l;
		
		when(fr.countByFollowing(u1)).thenReturn(5l);
		
		long actual = sut.countFollowersByUserFollowed(u1);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void countFollowing() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		
		long expected = 5l;
		
		when(fr.countByFollower(u1)).thenReturn(5l);
		
		long actual = sut.countFollowingByUserFollowing(u1);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void isFollowingTrue() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);
		FollowerKey fk = new FollowerKey(1, 3);
		
		when(fr.findById(fk)).thenReturn(Optional.of(new Follower(fk, u1, u3)));
		
		assertTrue(sut.isFollowing(fk));
	}
	
	@Test
	void isFollowingFalse() {
		FollowerKey fk = new FollowerKey(1, 3);
		
		when(fr.findById(fk)).thenReturn(Optional.empty());
		
		assertFalse(sut.isFollowing(fk));
	}

}
