package com.study.unittest.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.study.unittest.domain.Member;
import com.study.unittest.domain.Post;

@DataJpaTest
class PostRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PostRepository postRepository;

	private static final String USERNAME = "Tester";
	private static final String PASSWORD = "password!!";

	@Test
	void findAllWithWriterTest() throws Exception {
	    // given
		Member member = memberRepository.save(Member.builder()
			.username(USERNAME)
			.password(PASSWORD)
			.build());

		List<Post> posts = new ArrayList<>();
		int postCount = 5;
		for (int i = 0; i < postCount; ++i) {
			Post post = postRepository.save(Post.builder()
				.title("title..." + i)
				.content("content..." + i)
				.writer(member)
				.build());
			posts.add(post);
		}

	    // when
		List<Post> results = postRepository.findAllWithWriter();

		// then
		assertThat(results.size()).isEqualTo(5);
		assertThat(results.stream().map(Post::getId).collect(Collectors.toList()))
			.containsExactlyElementsOf(posts.stream()
				.map(Post::getId)
				.sorted((o1, o2) -> Long.compare(o2, o1))
				.collect(Collectors.toList())
			);
	}

	@Test
	void findByIdWithWriterTest() throws Exception {
	    // given
		Member member = memberRepository.save(Member.builder()
			.username(USERNAME)
			.password(PASSWORD)
			.build());
		Post post = postRepository.save(Post.builder()
			.title("title...")
			.content("content...")
			.writer(member)
			.build());

		Long postId = post.getId();

	    // when
		Post findPost = postRepository.findByIdWithWriter(postId).orElse(null);

		// then
		assertThat(findPost).isNotNull();
		assertThat(findPost.getId()).isEqualTo(postId);
		assertThat(findPost.getTitle()).isEqualTo("title...");
		assertThat(findPost.getContent()).isEqualTo("content...");
		assertThat(findPost.getWriter().getUsername()).isEqualTo(USERNAME);
		assertThat(findPost.getWriter().getPassword()).isEqualTo(PASSWORD);
	}
}
