package com.example.demo.SpringConfig.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile {
	private String name;
	private String email;
	private String provider;
	private String nickname;

	public User toMember() {
		return User.builder()
			.name(name)
			.email(email)
			.provider(provider)
			.build();
	}

}