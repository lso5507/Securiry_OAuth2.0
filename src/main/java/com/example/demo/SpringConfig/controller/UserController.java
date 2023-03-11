package com.example.demo.SpringConfig.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final HttpSession httpSession;

	@Value("${spring.security.oauth2.client.registration.naver.client-id}") private String activeProfile;
	@RestController
	@RequestMapping("/oauth")
	public class OAuthController {

		@GetMapping("/loginInfo")
		public String oauthLoginInfo(Authentication authentication ){
			System.out.println("activeProfile = " + activeProfile);
			//oAuth2User.toString() 예시 : Name: [2346930276], Granted Authorities: [[USER]], User Attributes: [{id=2346930276, provider=kakao, name=김준우, email=bababoll@naver.com}]
			OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
			//attributes.toString() 예시 : {id=2346930276, provider=kakao, name=김준우, email=bababoll@naver.com}
			Map<String, Object> attributes = oAuth2User.getAttributes();
			return attributes.toString();
		}
	}
}
