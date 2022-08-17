package com.revature.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.PostRepository;

@Service
public class PostService {

	private PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public List<Post> getAll() {
		return postRepository.findAll();
	}

	public Post upsert(Post post) {
		return postRepository.save(post);
	}

	public List<Post> getMainPosts() {
		return postRepository.getMainPosts();
	}

	public Post findById(int id) {
		return postRepository.findPostById(id);
	}

	public User findcommentUser(List<Post> comments) {
		User u = new User();
		for (Post comment : comments) {
			if (comment.getId() == 0) {
				u = comment.getAuthor();
				break;
			} else {
				if (!comment.getComments().isEmpty()) {
					u = this.findcommentUser(comment.getComments());
				}
			}
		}
		return u;
	}

	public Post findPostPrimary(int id) {
		Post post = postRepository.findPostById(id);
		if (post.getCommentsId() == null) {
			return post;
		} else {
			return findPostPrimary(post.getCommentsId());
		}
	}
}
