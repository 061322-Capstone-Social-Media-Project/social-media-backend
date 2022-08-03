package com.revature.services;

import org.springframework.stereotype.Service;

import com.revature.models.Likes;
import com.revature.repositories.LikesRepository;

@Service
public class LikesService {

	private LikesRepository lr;

	public LikesService(LikesRepository lr) {
		super();
		this.lr = lr;
	}
	public Likes userLikesPost(Likes likes) {
		return lr.save(likes);
		
	
		
	}
	public void removeLike(int id) {
			
		lr.deleteById(id);
	}
}
