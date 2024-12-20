package com.example.demo.SpringConfig.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.demo.SpringConfig.OAuthAttributes;
import com.example.demo.SpringConfig.Repository.UserRepository;
import com.example.demo.SpringConfig.domain.User;
import com.example.demo.SpringConfig.domain.UserProfile;

import lombok.RequiredArgsConstructor;
/*
    OAuth2 로그인 성공시 DB에 저장하는 작업
 */
@Service
@RequiredArgsConstructor
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest); // OAuth 서비스(kakao, google, naver)에서 가져온 유저 정보를 담고있음

		String registrationId = userRequest.getClientRegistration()
			.getRegistrationId(); // OAuth 서비스 이름(ex. kakao, naver, google)
		String userNameAttributeName = userRequest.getClientRegistration()
			.getProviderDetails()
			.getUserInfoEndpoint()
			.getUserNameAttributeName(); // OAuth 로그인 시 키(pk)가 되는 값
		Map<String, Object> attributes = oAuth2User.getAttributes(); // OAuth 서비스의 유저 정보들

		UserProfile userProfile = OAuthAttributes.extract(registrationId, attributes); // registrationId에 따라 유저 정보를 통해 공통된 UserProfile 객체로 만들어 줌
		userProfile.setProvider(registrationId);
		User user = saveOrUpdate(userProfile);

		Map<String, Object> customAttribute = customAttribute(attributes, userNameAttributeName, userProfile, registrationId);

		return new DefaultOAuth2User(
			Collections.singleton(new SimpleGrantedAuthority("USER")),
			customAttribute,
			userNameAttributeName);

	}

	private Map customAttribute(Map attributes, String userNameAttributeName, UserProfile memberProfile, String registrationId) {
		Map<String, Object> customAttribute = new LinkedHashMap<>();
		customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
		customAttribute.put("provider", registrationId);
		customAttribute.put("name", memberProfile.getName());
		customAttribute.put("email", memberProfile.getEmail());
		return customAttribute;

	}

	private User saveOrUpdate(UserProfile memberProfile) {

		User member = userRepository.findByEmailAndProvider(memberProfile.getEmail(), memberProfile.getProvider())
			.map(m -> m.update(memberProfile.getName(), memberProfile.getEmail())) // OAuth 서비스 사이트에서 유저 정보 변경이 있을 수 있기 때문에 우리 DB에도 update
			.orElse(memberProfile.toMember());

		return userRepository.save(member);
	}


}