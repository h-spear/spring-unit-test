package com.study.unittest.dto;

import com.study.unittest.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PostResponse {

	private Long postId;
	private String title;
	private Long writerId;
	private String writerUsername;

	public static PostResponse of(Post post) {
		return PostResponse.builder()
			.postId(post.getId())
			.title(post.getTitle())
			.writerId(post.getWriter().getId())
			.writerUsername(post.getWriter().getUsername())
			.build();
	}
}
