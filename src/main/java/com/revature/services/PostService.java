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

	public User findcommentUser(List<Post> comments) {
		User u = new User();
		for (Post comment : comments) {
			if (comment.getId() == 0) {
				u = comment.getAuthor();
				break;
			} else {
				if (!comment.getComments().isEmpty()) {
					u = findcommentUser(comment.getComments());
				}
			}
		}
		return u;

	}

	public Post findPostPrimary(int id) {
		boolean post_check = true;

		while (post_check) {
			Post post = postRepository.findPostById(id);
			if (post.getCommentsId() == null) {
				return post;
			} else {
				if (post.getCommentsId() != null) {
					id = post.getCommentsId();
				} else {
					break;
				}
			}
		}

		return null;
	}
}
