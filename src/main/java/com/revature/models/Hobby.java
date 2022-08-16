package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hobbies")
public class Hobby {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "hobby_1" )
	private String hobby1;
	@Column(name = "hobby_2" )
	private String hobby2;
	@Column(name = "hobby_3" )
	private String hobby3;
	@Column(name = "user_id")
	private int userId;
}
