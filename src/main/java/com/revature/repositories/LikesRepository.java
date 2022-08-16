package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Likes;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer> {
	

	Likes findLikesByUserIdAndPostId(int userId, int postId);

	Likes findLikesById(int id);
	
	long countLikesByPostId(int postId);
}
