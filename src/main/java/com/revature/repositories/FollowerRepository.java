package com.revature.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.keys.FollowerKey;
import com.revature.models.Follower;
import com.revature.models.User;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, FollowerKey>{

	public Set<User> findFollowersByUser(User u);
	
	public Set<User> findFollowingByUser(User u);
}
