package com.revature.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HobbyRequest {

	private int id;
	private String hobby1;
	private String hobby2;
	private String hobby3;
	private int userId;
}
