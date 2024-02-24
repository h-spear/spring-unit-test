package com.study.unittest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.unittest.controller.response.ApiResponse;
import com.study.unittest.dto.PostDetailResponse;
import com.study.unittest.dto.PostResponse;
import com.study.unittest.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

	private final PostService postService;

	@GetMapping
	public ApiResponse<List<PostResponse>> list() {
		return ApiResponse.ofSuccess(postService.findAll());
	}

	@GetMapping("/{postId}")
	public ApiResponse<?> detail(@PathVariable Long postId) {
		try {
			return ApiResponse.ofSuccess(postService.findOne(postId));
		} catch (RuntimeException e) {
			return ApiResponse.ofError(e.getMessage());
		}
	}
}
