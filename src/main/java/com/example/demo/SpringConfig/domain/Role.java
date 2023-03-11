package com.example.demo.SpringConfig.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum Role {
	GUEST("ROLE_GUEST", "손님"),
	USER("ROLE_USER", "사용자");
	Role(String key,String title){
		this.key=key;
		this.title=title;
	}
	private final String key;
	private final String title;
}
