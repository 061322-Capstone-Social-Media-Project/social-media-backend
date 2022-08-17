package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes")
public class Likes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "user_id")
	private int userId;
	@Column(name = "post_id")
	private int postId;

}
