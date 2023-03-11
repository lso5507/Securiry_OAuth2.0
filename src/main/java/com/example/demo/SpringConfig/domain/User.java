package com.example.demo.SpringConfig.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자 만들어줌
@Getter @Entity
@Table(name="TB_USER")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "provider", nullable = false)
	private String provider;

	@Column(name = "nickname", nullable = true, unique = true)
	private String nickname;

	@Builder //생성을 Builder 패턴으로 하기 위해서
	public User(Long id, String name, String email, String provider, String nickname) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.provider = provider;
		this.nickname = nickname;
	}


	public User update(String name, String email) {
		this.name = name;
		this.email = email;
		return this;
	}
}
