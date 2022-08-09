package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.revature.exceptions.AlreadyFollowingException;
import com.revature.exceptions.NotFollowingException;
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
		List<User> following =new ArrayList<>();
		results.forEach(r -> following.add(r.getFollowing()));
		return following;
	}

	public void addFollowing(FollowerKey fk) {
		
		Follower f = fr.findById(fk).orElse(null);
		if (f != null) {
			throw new AlreadyFollowingException();
		}
//		User follower = us.findById(fk.getFollowerId()).isPresent() ? us.findById(fk.getFollowerId()).get() : null;
//		User following = us.findById(fk.getFollowingId()).get();
		
		User follower = us.getUserById(fk.getFollowerId());
		User following = us.getUserById(fk.getFollowingId());
		f = new Follower(fk, follower, following);
		fr.save(f);
		
	}

	public void removeFollowing(FollowerKey fk) {
		
		Follower f = fr.findById(fk).orElseThrow(NotFollowingException::new);
		fr.delete(f);
		
	}
	
	public boolean isFollowing(FollowerKey fk) {
		Optional<Follower> f = fr.findById(fk);
		return f.isPresent() ? true : false;
	}
}
