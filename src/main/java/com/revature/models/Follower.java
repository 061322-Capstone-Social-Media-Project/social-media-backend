package com.revature.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.revature.keys.FollowerKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "follower")
public class Follower {
	
	@EmbeddedId
	FollowerKey id;
	
	@ManyToOne
	@MapsId("followerId")
	@JoinColumn(name="follower_id")
	User follower;
	
	@ManyToOne
	@MapsId("followingId")
	@JoinColumn(name="following_id")
	User following;

}
