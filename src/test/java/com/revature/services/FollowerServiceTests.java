package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.SocialMediaApplication;
import com.revature.exceptions.AlreadyFollowingException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.keys.FollowerKey;
import com.revature.models.Follower;
import com.revature.models.User;
import com.revature.repositories.FollowerRepository;

@SpringBootTest(classes = SocialMediaApplication.class)
public class FollowerServiceTests {

	@MockBean
	private FollowerRepository fr;

	@MockBean
	private UserService us;

	@Autowired
	private FollowerService sut;

	@Test
	public void getFollowersSuccess() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post");
		User u2 = new User(2, "adam@someemail.com", "password", "adam", "harbeck");
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff");

		Set<User> followers = new HashSet<>();
		followers.add(u2);

		Mockito.when(fr.findFollowersByUser(u1)).thenReturn(followers);

		Set<User> followersActual = sut.getFollowersByUser(u1);

		assertEquals(followers, followersActual);
	}

	@Test
	public void getFollowingSuccess() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post");
		User u2 = new User(2, "adam@someemail.com", "password", "adam", "harbeck");
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff");

		Set<User> following = new HashSet<>();
		following.add(u1);

		Mockito.when(fr.findFollowingByUser(u2)).thenReturn(following);

		Set<User> followingActual = sut.getFollowingByUser(u2);

		assertEquals(following, followingActual);
	}

	@Test
	public void addFollowingSuccess() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post");
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff");

		FollowerKey fk = new FollowerKey(u3.getId(), u1.getId());
		Follower f = new Follower(fk, u3, u1);

		sut.addFollowing(fk);

		Mockito.verify(fr).save(f);
	}

	@Test
	public void addFollowingUserNotExist() {

		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post");
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff");

		FollowerKey fk = new FollowerKey(u3.getId(), u1.getId());
		Follower f = new Follower(fk, u3, u1);

		Mockito.when(us.getUserById(fk.getFollowingId())).thenThrow(new UserNotFoundException());

		assertThrows(UserNotFoundException.class, () -> sut.addFollowing(fk));
	}

	@Test
	public void addFollowingAlreadyFollowing() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post");
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff");

		FollowerKey fk = new FollowerKey(u3.getId(), u1.getId());
		Follower f = new Follower(fk, u3, u1);

		Mockito.when(fr.getReferenceById(fk)).thenReturn(f);

		assertThrows(AlreadyFollowingException.class, () -> sut.addFollowing(fk));
	}

	// Mock get ref id
	@Test
	public void removeFollowingSuccess() {
		User u1 = new User(1, "calvin@someemail.com", "password", "calvin", "post");
		User u3 = new User(3, "trey@someemail.com", "password", "robert", "ratcliff");

		FollowerKey fk = new FollowerKey(u3.getId(), u1.getId());
		Follower f = new Follower(fk, u3, u1);

		sut.removeFollowing(fk);

		Mockito.verify(fr).delete(f);
	}

}
