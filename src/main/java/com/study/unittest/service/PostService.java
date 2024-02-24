package com.study.unittest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.unittest.domain.Post;
import com.study.unittest.dto.PostDetailResponse;
import com.study.unittest.dto.PostResponse;
import com.study.unittest.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;

	public List<PostResponse> findAll() {
		return postRepository.findAllWithWriter()
			.stream().map(PostResponse::of)
			.collect(Collectors.toList());
	}

	public PostDetailResponse findOne(Long postId) {
		Post post = postRepository.findByIdWithWriter(postId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 게시글 번호입니다."));
		return PostDetailResponse.of(post);
	}
}
