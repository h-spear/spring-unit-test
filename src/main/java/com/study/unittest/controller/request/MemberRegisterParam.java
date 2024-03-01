package com.study.unittest.controller.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberRegisterParam {

	@NotBlank
	@Size(min=4, max=16)
	@Pattern(regexp = "[a-zA-Z0-9]{4,16}")
	private String username;

	@NotBlank
	@Size(min=8, max=24)
	private String password;

	@NotBlank
	private String name;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
}
