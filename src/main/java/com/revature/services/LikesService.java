package com.revature.services;

import org.springframework.stereotype.Service;

import com.revature.exceptions.LikeNotFoundException;
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
	public void removeLike(int id) throws LikeNotFoundException {
			
		lr.deleteById(id);
	}
	public Likes findLikesByUserIdAndPostId(int user_id, int post_id) {
		return lr.findLikesByUserIdAndPostId(user_id, post_id);
		
	}

	
	public Likes findById(int id) {
		return lr.findLikesById( id);
		
	}
}
