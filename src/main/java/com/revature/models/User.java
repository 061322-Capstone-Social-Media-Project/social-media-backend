package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="profile_pic")
    private String profilePic;
    @Column(name="username")
    private String username;
    @Column(name="professional_url")
    private String professionalURL;
    @Column(name="location")
    private String location;
    @Column(name="name_pronunciation")
    private String namePronunciation;
}
