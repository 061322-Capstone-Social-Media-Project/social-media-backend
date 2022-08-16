package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
	@Transactional 
	@Modifying
	@Query("select p  from  Post  p where comments_id is null") List<Post> getMainPosts();	
	
	Post findPostById(int id);

}
