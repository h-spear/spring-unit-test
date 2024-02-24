package com.study.unittest.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.study.unittest.dto.PostDetailResponse;
import com.study.unittest.service.PostService;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private PostService postService;

	@Test
	@DisplayName("/post: 모든 게시글 목록 조회에 성공하면 status code 200, 응답의 status 값 success를 반환한다.")
	void listTest() throws Exception {
		// given

		// when
		ResultActions resultActions = mvc.perform(get("/post")
			.contentType(MediaType.APPLICATION_JSON)
			.characterEncoding(StandardCharsets.UTF_8));

		// then
		resultActions
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.status").value("success"))
			.andExpect(jsonPath("$.message").doesNotExist());

		verify(postService).findAll();
	}

	@Test
	@DisplayName("/post/{postId}: 게시글을 상세 조회에 성공하면 status code 200, 응답의 status값 success를 반환한다.")
	void detailTest() throws Exception {
		// given
		final Long postId = 1L;

		// mock
		given(postService.findOne(postId))
			.willReturn(getFakePostDetailResponse(postId));

		// when
		ResultActions resultActions = mvc.perform(get("/post/" + postId)
			.contentType(MediaType.APPLICATION_JSON)
			.characterEncoding(StandardCharsets.UTF_8));

		// then
		resultActions
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.status").value("success"))
			.andExpect(jsonPath("$.data").exists())
			.andExpect(jsonPath("$.message").doesNotExist());

		verify(postService).findOne(postId);
	}

	@Test
	@DisplayName("/post/{postId}: 존재하지 않는 게시글 번호로 게시글을 조회하면 status code 200, 응답의 status 값 error를 반환한다.")
	void detailTest_Fail() throws Exception {
		// given
		final Long postId = 99L;
		final String msg = "존재하지 않는 게시글 번호입니다.";

		// mock
		given(postService.findOne(postId))
			.willThrow(new RuntimeException(msg));

		// when
		ResultActions resultActions = mvc.perform(get("/post/" + postId)
			.contentType(MediaType.APPLICATION_JSON)
			.characterEncoding(StandardCharsets.UTF_8));

		// then
		resultActions
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.status").value("error"))
			.andExpect(jsonPath("$.data").doesNotExist())
			.andExpect(jsonPath("$.message").value(msg));

		verify(postService).findOne(postId);
	}

	private PostDetailResponse getFakePostDetailResponse(Long postId) {
		return PostDetailResponse.builder()
			.postId(postId)
			.title("title..." + postId)
			.content("content..." + postId)
			.writerId(1L)
			.writerUsername("tester")
			.build();
	}
}