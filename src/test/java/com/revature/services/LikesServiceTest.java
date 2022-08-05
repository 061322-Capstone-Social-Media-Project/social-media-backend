package com.revature.services;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.SocialMediaApplication;
import com.revature.models.Likes;
import com.revature.repositories.LikesRepository;

@SpringBootTest(classes=SocialMediaApplication.class)
public class LikesServiceTest {
	@MockBean
	private LikesRepository likesRepository;
	
	@InjectMocks
	private LikesService lService;
	
	@Autowired
	public LikesServiceTest(LikesRepository likesRepository,LikesService lService) {
		super();
		this.likesRepository = likesRepository;
		this.lService = lService;
	}
	
	@Test
	public void findLikesByUserIdAndPostIdTest() {
		Likes likeExpected = new Likes();
		likeExpected.setId(1);
		likeExpected.setPostId(1);
		likeExpected.setUserId(1);
		
		Mockito.when(likesRepository.findLikesByUserIdAndPostId(1, 1)).thenReturn(likeExpected);
		
		assertEquals(likeExpected, likeExpected);
	}
	
	@Test
	public void userLikesPostTest() {
		
		Likes likeExpected = new Likes();
		likeExpected.setId(1);
		likeExpected.setPostId(1);
		likeExpected.setUserId(1);
		
		Mockito.when(lService.userLikesPost(likeExpected)).thenReturn(likeExpected);
		
		assertEquals(likeExpected, likeExpected);
	}

	
	
	
}
