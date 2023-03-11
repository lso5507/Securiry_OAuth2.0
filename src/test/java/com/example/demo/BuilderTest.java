package com.example.demo;

import org.junit.jupiter.api.Test;

import lombok.Builder;
import lombok.Getter;

public class BuilderTest {
	@Builder
	@Getter
	public static class CustomUser{
		private String name;
		private String pw;
	}

	@Test
	public void buidlerTest(){
		CustomUser build = CustomUser.builder()
			.name("leee")
			.pw("TEST")
			.build();
		System.out.println("build = " + build);
	}

}
