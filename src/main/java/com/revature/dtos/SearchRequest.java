package com.revature.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
	private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String profilePic;
    private String username;
    private String professionalURL;
    private String location;
    private String namePronunciation;
}