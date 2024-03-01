package com.study.unittest.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.unittest.controller.request.MemberRegisterParam;
import com.study.unittest.controller.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	@PostMapping("/join")
	public ApiResponse<?> join(@Validated @RequestBody MemberRegisterParam param,
							   BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return ApiResponse.ofFail(bindingResult);
		return ApiResponse.ofSuccess();
	}
}
