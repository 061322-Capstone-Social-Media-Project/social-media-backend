package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.exceptions.LikeNotFoundException;
import com.revature.models.Likes;
import com.revature.repositories.LikesRepository;

@SpringBootTest
class LikesServiceTest {

	@MockBean
	private LikesRepository likesRepository;

	@Autowired
	private LikesService lService;

	@Test
	void findLikesSuccess() {
		Likes likeExpected = new Likes();
		likeExpected.setId(1);
		likeExpected.setPostId(1);
		likeExpected.setUserId(1);

		Mockito.when(likesRepository.findLikesByUserIdAndPostId(1, 1)).thenReturn(likeExpected);
		Likes likesActuaLikes = lService.findLikesByUserIdAndPostId(1, 1);

		assertEquals(likeExpected, likesActuaLikes);
	}

	@Test
	void addLikesSuccess() {
		Likes likeExpected = new Likes();
		likeExpected.setId(1);
		likeExpected.setPostId(1);
		likeExpected.setUserId(1);

		Mockito.when(likesRepository.save(likeExpected)).thenReturn(likeExpected);
		Likes actualLikes = lService.userLikesPost(likeExpected);
		assertEquals(likeExpected, actualLikes);
	}

	@Test
	void removeLikesSuccess() {
		Mockito.doThrow(IllegalArgumentException.class).when(likesRepository).deleteById(1);
		assertThrows(LikeNotFoundException.class, () -> lService.removeLike(1));
	}

	@Test
	void removeLikesExist() throws LikeNotFoundException {
		Likes likeExpected = new Likes();
		likeExpected.setId(1);
		likeExpected.setPostId(1);
		likeExpected.setUserId(1);

		lService.removeLike(1);
		Mockito.verify(likesRepository).deleteById(1);

	}

	@Test
	void findLikesByIdSuccess() {
		Likes likeExpected = new Likes();
		likeExpected.setId(1);
		likeExpected.setPostId(1);
		likeExpected.setUserId(1);

		Mockito.when(likesRepository.findLikesById(1)).thenReturn(likeExpected);
		Likes likesActuaLikes = lService.findById(1);

		assertEquals(likeExpected, likesActuaLikes);
	}

	@Test
	void countLikesByPostIdSuccess() {
		Likes likeExpected = new Likes();
		likeExpected.setId(1);
		likeExpected.setPostId(1);
		likeExpected.setUserId(1);

		List<Likes> likeExpecteds = new ArrayList<>();

		long outputExpected = 1;
		Mockito.when(likesRepository.countLikesByPostId(1)).thenReturn(outputExpected);
		long outputActual = lService.countLikesByPostId(1);
		assertEquals(outputActual, outputExpected);

	}
}
