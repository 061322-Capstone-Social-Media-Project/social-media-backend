package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	void findLikesByUserIdAndPostIdTest() {
		Likes likeExpected = new Likes();
		likeExpected.setId(1);
		likeExpected.setPostId(1);
		likeExpected.setUserId(1);

		Mockito.when(likesRepository.findLikesByUserIdAndPostId(1, 1)).thenReturn(likeExpected);
		Likes likesActuaLikes = lService.findLikesByUserIdAndPostId(1, 1);

		assertEquals(likeExpected, likesActuaLikes);
	}

	@Test
	void userLikesPostTest() {
		Likes likeExpected = new Likes();
		likeExpected.setId(1);
		likeExpected.setPostId(1);
		likeExpected.setUserId(1);

		Mockito.when(likesRepository.save(likeExpected)).thenReturn(likeExpected);
		Likes actualLikes = lService.userLikesPost(likeExpected);
		assertEquals(likeExpected, actualLikes);
	}

	@Test
	void userLikesPostDeleteTest() {
		when(likesRepository.findById(1)).thenReturn(Optional.empty());
		assertThrows(LikeNotFoundException.class, () -> lService.removeLike(1));
	}

	@Test
	void userLikesPostDeleteExist() throws LikeNotFoundException {
		Likes likeExpected = new Likes();
		likeExpected.setId(1);
		likeExpected.setPostId(1);
		likeExpected.setUserId(1);

		when(likesRepository.findById(1)).thenReturn(Optional.of(likeExpected));
		
		lService.removeLike(1);
		Mockito.verify(likesRepository).deleteById(1);

	}

	@Test
	void findLikesByIdTest() {
		Likes likeExpected = new Likes();
		likeExpected.setId(1);
		likeExpected.setPostId(1);
		likeExpected.setUserId(1);

		Mockito.when(likesRepository.findLikesById(1)).thenReturn(likeExpected);
		Likes likesActuaLikes = lService.findById(1);

		assertEquals(likeExpected, likesActuaLikes);
	}

	@Test
	public void countLikesByPostIdTest() {
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
