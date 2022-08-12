package com.revature.repositories;

import com.revature.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer>{
	List<Post> getPostByCommentsIsNull();
}
