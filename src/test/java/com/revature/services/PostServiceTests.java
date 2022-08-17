package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.SocialMediaApplication;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.PostRepository;

@SpringBootTest(classes = SocialMediaApplication.class)
class PostServiceTests {

	@MockBean
	private PostRepository pr;

	@Autowired
	private PostService sut;

	@Test
	void getAllTest() {
		List<Post> expected = new ArrayList<>();
		expected.add(new Post(1, "A post", null, null, null,
				new User(1, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null)));
		expected.add(new Post(1, "Another post", null, null, null,
				new User(1, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null)));

		when(pr.findAll()).thenReturn(expected);

		List<Post> actual = sut.getAll();

		assertEquals(expected, actual);
	}

	@Test
	void upsertTest() {
		Post p = new Post(0, "A post", null, null, null,
				new User(1, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null));
		Post expected = new Post(1, "A post", null, null, null,
				new User(1, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null));

		when(pr.save(p)).thenReturn(expected);

		Post actual = sut.upsert(p);

		assertEquals(expected, actual);
	}

	@Test
	void getMainPostsTest() {
		List<Post> expected = new ArrayList<>();
		expected.add(new Post(1, "A post", null, null, null,
				new User(1, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null)));
		expected.add(new Post(1, "Another post", null, null, null,
				new User(1, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null)));

		when(pr.getMainPosts()).thenReturn(expected);

		List<Post> actual = sut.getMainPosts();

		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdTest() {
		Post expected = new Post(1, "A post", null, null, null,
				new User(1, "testuser@gmail.com", "password", "Test", "User", null, null, null, null, null));

		when(pr.findPostById(1)).thenReturn(expected);

		Post actual = sut.findById(1);

		assertEquals(expected, actual);
	}
	
}
