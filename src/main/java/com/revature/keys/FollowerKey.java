package com.revature.keys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FollowerKey implements Serializable {

	@Column(name="follower_id")
	int followerId;
	
	@Column(name="following_id")
	int followingId;
}
