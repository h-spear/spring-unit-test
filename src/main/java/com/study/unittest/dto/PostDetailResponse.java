package com.study.unittest.dto;

import com.study.unittest.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PostDetailResponse {

	private Long postId;
	private String title;
	private String content;
	private Long writerId;
	private String writerUsername;

	public static PostDetailResponse of(Post post) {
		return PostDetailResponse.builder()
			.postId(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.writerId(post.getWriter().getId())
			.writerUsername(post.getWriter().getUsername())
			.build();
	}
}
