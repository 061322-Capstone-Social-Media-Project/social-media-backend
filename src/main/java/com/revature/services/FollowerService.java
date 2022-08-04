package com.revature.services;

import java.util.Set;

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

	public Set<User> getFollowersByUser(User u) {
		return fr.findFollowersByUser(u);
	}

	public Set<User> getFollowingByUser(User u) {
		return fr.findFollowingByUser(u);
	}

	public void addFollowing(FollowerKey fk) {
		Follower f = fr.getReferenceById(fk);
		if (f != null) {
			throw new AlreadyFollowingException();
		}
		User follower = us.getUserById(fk.getFollowerId());
		User following = us.getUserById(fk.getFollowingId());
		f = new Follower(fk, follower, following);
		fr.save(f);
	}

	public void removeFollowing(FollowerKey fk) {
		Follower f = fr.getReferenceById(fk);
		if (f == null) {
			throw new NotFollowingException();
		}
		fr.delete(f);
	}
}
