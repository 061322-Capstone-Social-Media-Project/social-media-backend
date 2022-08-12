package com.revature.services;

import com.revature.models.Post;
import com.revature.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

	private final PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public List<Post> getAll() {
		return this.postRepository.findAll();
	}

	public Post upsert(Post post) {
		return this.postRepository.save(post);
	}
	
	public List<Post> getMainPosts() {
		return this.postRepository.getPostByCommentsIsNull();
	}
}
