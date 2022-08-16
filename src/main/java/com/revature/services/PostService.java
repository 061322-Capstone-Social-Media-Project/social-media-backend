package com.revature.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.models.Post;
import com.revature.repositories.PostRepository;

@Service
public class PostService {

	private PostRepository postRepository;
	
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
		return this.postRepository.getMainPosts();
	}
	
	public Post findById(int id) {
		return this.postRepository.findPostById(id);
	}

}
