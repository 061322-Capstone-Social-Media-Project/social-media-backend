package com.revature.services;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.SocialMediaApplication;
import com.revature.exceptions.LikeNotFoundException;
import com.revature.models.Likes;
import com.revature.repositories.LikesRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes=SocialMediaApplication.class)
public class LikesServiceTest {
	@MockBean
	private LikesRepository likesRepository;
	
	@Autowired
	private LikesService lService;
	
	@Test
	public void findLikesByUserIdAndPostIdTest() {
		Likes likeExpected = new Likes();
		likeExpected.setId(1);
		likeExpected.setPostId(1);
		likeExpected.setUserId(1);
		
		Mockito.when(likesRepository.findLikesByUserIdAndPostId(1, 1)).thenReturn(likeExpected);
		Likes likesActuaLikes =  lService.findLikesByUserIdAndPostId(1, 1);
		 
		assertEquals(likeExpected, likesActuaLikes);
	}
	
	@Test
	public void userLikesPostTest() {
		Likes likeExpected = new Likes();
		likeExpected.setId(1);
		likeExpected.setPostId(1);
		likeExpected.setUserId(1);
		
		Mockito.when(likesRepository.save(likeExpected)).thenReturn(likeExpected);
		Likes actualLikes = lService.userLikesPost(likeExpected);
		assertEquals(likeExpected, actualLikes);
	}

	@Test
	public void userLikesPostDeleteTest() {
		Mockito.doThrow(IllegalArgumentException.class).when(likesRepository).deleteById(1);
		assertThrows(LikeNotFoundException.class, () -> lService.removeLike(1));
	}

	@Test
	public void userLikesPostDeleteExist() throws LikeNotFoundException {
		Likes likeExpected = new Likes();
		likeExpected.setId(1);
		likeExpected.setPostId(1);
		likeExpected.setUserId(1);		

		Mockito.doNothing().when(likesRepository).deleteById(1);
		lService.removeLike(1);
	}
	
	@Test
	public void findLikesByIdTest() {
		Likes likeExpected = new Likes();
		likeExpected.setId(1);
		likeExpected.setPostId(1);
		likeExpected.setUserId(1);
		
		Mockito.when(likesRepository.findLikesById(1)).thenReturn(likeExpected);
		Likes likesActuaLikes =  lService.findById(1);
		 
		assertEquals(likeExpected, likesActuaLikes);
	}
}
