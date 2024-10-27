package com.example.demo.SpringConfig.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/login")
@RestController
public class AuthController {
	@GetMapping("/oauth2/code/naver")
	public void authorizationCode_naver(){

	}
}
