package com.revature.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.keys.FollowerKey;
import com.revature.models.Follower;
import com.revature.models.User;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, FollowerKey>{
	
	public Page<Follower> findFollowersByFollowing(User u, Pageable p);
	
	public Page<Follower> findFollowingByFollower(User u, Pageable p);
	
	public long countByFollowing(User u);
	
	public long countByFollower(User u);
}
