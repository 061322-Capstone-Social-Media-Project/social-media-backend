package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Likes;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer> {
	

	Likes findLikesByUserIdAndPostId(int user_id, int post_id);

	Likes findLikesById(int id);
	

}
