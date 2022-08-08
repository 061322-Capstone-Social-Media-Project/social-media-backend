package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
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

		List<User> followers = new ArrayList<>();
		followers.add(u2);
		followers.add(u3);

		Mockito.when(fr.findFollowersByFollowing(u1, Pageable.unpaged())).thenReturn(new PageImpl<>(followers));

		List<User> followersActual = sut.getFollowersByFollowing(u1, Pageable.unpaged());

		assertEquals(followers, followersActual);
	}

	@Test
	void getFollowingSuccess() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u2 = new User(2, "adam@someemail.com", "password", "adam", "harbeck", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);

		List<User> following = new ArrayList<>();
		following.add(u1);

		Mockito.when(fr.findFollowingByFollower(u2, Pageable.unpaged())).thenReturn(new PageImpl<>(following));
		

		List<User> followingActual = sut.getFollowingByFollower(u2, Pageable.unpaged());

		assertEquals(following, followingActual);
	}

	@Test
	void addFollowingSuccess() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);

		FollowerKey fk = new FollowerKey(u3.getId(), u1.getId());
		Follower f = new Follower(fk, u3, u1);
		
		Mockito.when(fr.findById(fk)).thenReturn(Optional.empty());
		
		Mockito.when(us.findById(1)).thenReturn(Optional.of(u1));
		Mockito.when(us.findById(3)).thenReturn(Optional.of(u3));
		
		sut.addFollowing(fk);

		Mockito.verify(fr).save(f);
	}

	@Test
	void addFollowingUserNotExist() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);

		FollowerKey fk = new FollowerKey(u3.getId(), u1.getId());

		Mockito.when(us.findById(fk.getFollowingId())).thenThrow(new UserNotFoundException());

		assertThrows(UserNotFoundException.class, () -> sut.addFollowing(fk));
	}

	@Test
	void addFollowingAlreadyFollowing() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);

		FollowerKey fk = new FollowerKey(u3.getId(), u1.getId());
		Follower f = new Follower(fk, u3, u1);

		Mockito.when(fr.findById(fk)).thenReturn(Optional.of(f));

		assertThrows(AlreadyFollowingException.class, () -> sut.addFollowing(fk));
	}

	@Test
	void removeFollowingSuccess() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);

		FollowerKey fk = new FollowerKey(u3.getId(), u1.getId());
		Follower f = new Follower(fk, u3, u1);
		
		Mockito.when(fr.findById(fk)).thenReturn(Optional.of(f));
		
		sut.removeFollowing(fk);

		Mockito.verify(fr).delete(f);
	}
	
	@Test
	void removeFollowingNotFollowing() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post", null, null, null, null, null);
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff", null, null, null, null, null);

		FollowerKey fk = new FollowerKey(u3.getId(), u1.getId());
		
		Mockito.when(fr.findById(fk)).thenThrow(new NotFollowingException());

		assertThrows(NotFollowingException.class, () -> sut.removeFollowing(fk));
	}

}
