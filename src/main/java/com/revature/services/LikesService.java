package com.revature.services;

import java.util.Optional;

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
		Optional<Likes> l = lr.findById(id);
		if (l.isEmpty()) {
			throw new LikeNotFoundException();
		}
		lr.deleteById(id);
	}
	
	public Likes findLikesByUserIdAndPostId(int userId, int postId) {
		return lr.findLikesByUserIdAndPostId(userId, postId);
	}

	
	public Likes findById(int id) {
		return lr.findLikesById( id);
	}

	public long countLikesByPostId(int postId) {
		return lr.countLikesByPostId(postId);
	}
}
