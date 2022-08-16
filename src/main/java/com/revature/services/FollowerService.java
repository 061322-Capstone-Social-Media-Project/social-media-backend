package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.revature.exceptions.AlreadyFollowingException;
import com.revature.exceptions.NotFollowingException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.keys.FollowerKey;
import com.revature.models.Follower;
import com.revature.models.User;
import com.revature.repositories.FollowerRepository;

@Service
public class FollowerService {

	private FollowerRepository fr;
	private UserService us;

	public FollowerService(FollowerRepository fr, UserService us) {
		super();
		this.fr = fr;
		this.us = us;
	}

	public List<User> getFollowersByFollowing(User u, Pageable p) {
		List<Follower> results = fr.findFollowersByFollowing(u, p).toList();
		List<User> followers = new ArrayList<>();
		results.forEach(r -> followers.add(r.getFollower()));
		return followers;
	}

	public List<User> getFollowingByFollower(User u, Pageable p) {
		List<Follower> results = fr.findFollowingByFollower(u, p).toList();
		List<User> following = new ArrayList<>();
		results.forEach(r -> following.add(r.getFollowing()));
		return following;
	}

	public void addFollowing(FollowerKey fk) {

		Optional<Follower> f = fr.findById(fk);
		if (f.isPresent()) {
			throw new AlreadyFollowingException();
		}

		Optional<User> follower = us.findById(fk.getFollowerId());
		Optional<User> following = us.findById(fk.getFollowingId());

		if (follower.isEmpty() || following.isEmpty()) {
			throw new UserNotFoundException();
		}
		
		fr.save(new Follower(fk, follower.get(), following.get()));

	}

	public void removeFollowing(FollowerKey fk) {
		Optional<Follower> f = fr.findById(fk);
		if (f.isEmpty()) {
			throw new NotFollowingException();
		}
		fr.delete(f.get());
	}

	public boolean isFollowing(FollowerKey fk) {
		Optional<Follower> f = fr.findById(fk);
		return f.isPresent();
	}

	public long countFollowersByUserFollowed(User u) {
		return fr.countByFollowing(u);
	}

	public long countFollowingByUserFollowing(User u) {
		return fr.countByFollower(u);
	}
}
