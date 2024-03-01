package com.study.unittest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	@DisplayName("memberRegisterParam: 검증에 성공한다.")
	void memberRegisterParamTest1() throws Exception {
		// given
		Map<String, String> request = new HashMap<>();
		request.put("username", "tester1");
		request.put("password", "asdf1234!!");
		request.put("name", "Avril Lavigne");
		request.put("birthDate", "1984-09-27");
		String content = new ObjectMapper().writeValueAsString(request);

		// when
		ResultActions resultActions = mvc.perform(post("/member/join")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content)
			.characterEncoding(StandardCharsets.UTF_8));

		// then
		resultActions
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.status").value("success"))
			.andExpect(jsonPath("$.message").doesNotExist());
	}

	@Test
	@DisplayName("memberRegisterParam: 검증에 실패한다.")
	void memberRegisterParamTest2() throws Exception {
		// given
		Map<String, String> request = new HashMap<>();
		request.put("username", "abc");
		request.put("password", "zzz");
		request.put("name", "");
		request.put("birthDate", "1984-09-27");
		String content = new ObjectMapper().writeValueAsString(request);

		// when
		ResultActions resultActions = mvc.perform(post("/member/join")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content)
			.characterEncoding(StandardCharsets.UTF_8));

		// then
		resultActions
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.status").value("fail"))
			.andExpect(jsonPath("$.message").doesNotExist());
	}

}