package com.study.unittest.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.study.unittest.domain.Member;
import com.study.unittest.domain.Post;
import com.study.unittest.dto.PostDetailResponse;
import com.study.unittest.dto.PostResponse;
import com.study.unittest.repository.PostRepository;

@ExtendWith(SpringExtension.class)
class PostServiceTest {

	@Mock
	private PostRepository postRepository;

	@InjectMocks
	private PostService postService;

	private static final String USERNAME = "Tester1";
	private static final String POST_TITLE = "게시글 제목";
	private static final String POST_CONTENT = "게시글 내용";

	private static final Long FAKE_MEMBER_ID = 1L;
	private static final Long[] FAKE_POST_IDS = {1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L};

	private Member fakeMember;
	private List<Post> fakePosts = new ArrayList<>();

	@BeforeEach
	void beforeEach() {
		initData();
	}

	@AfterEach
	void afterEach() {
		fakePosts.clear();
	}

	@Test
	@DisplayName("게시글 번호로 게시글 상세 조회에 성공한다.")
	void findOneTest_Success() throws Exception {
		// given
		final Long postId = 4L;

		// mock
		when(postRepository.findByIdWithWriter(postId)).thenReturn(Optional.of(fakePosts.get(3)));

		// when
		PostDetailResponse response = postService.findOne(postId);

		// then
		verify(postRepository, times(1)).findByIdWithWriter(4L);
		assertThat(response.getPostId()).isEqualTo(4L);
		assertThat(response.getTitle()).isEqualTo(POST_TITLE);
		assertThat(response.getContent()).isEqualTo(POST_CONTENT);
		assertThat(response.getWriterId()).isEqualTo(FAKE_MEMBER_ID);
		assertThat(response.getWriterUsername()).isEqualTo(USERNAME);
	}

	@Test
	@DisplayName("존재하지 않는 게시글 번호로 게시글을 조회하면 RuntimeException이 발생한다.")
	void findOneTest_RuntimeException() throws Exception {
		// given
		final Long postId = 92L;

		// mock
		when(postRepository.findByIdWithWriter(postId)).thenReturn(Optional.empty());

		// assert
		assertThatThrownBy(() -> postService.findOne(postId))
			.isInstanceOf(RuntimeException.class);
		verify(postRepository, times(1)).findByIdWithWriter(92L);
	}

	@Test
	@DisplayName("게시글 목록 조회에 성공한다.")
	void findAllTest() throws Exception {
	    // given
		int totalPostCount = FAKE_POST_IDS.length;

		// mock
		when(postRepository.findAllWithWriter()).thenReturn(
			fakePosts.stream()
				.sorted((o1, o2) -> Long.compare(o2.getId(), o1.getId()))
				.collect(Collectors.toList())
		);

	    // when
		List<PostResponse> results = postService.findAll();

		// then
		verify(postRepository, times(1)).findAllWithWriter();
		assertThat(results.size()).isEqualTo(totalPostCount);
		assertThat(results.stream().map(PostResponse::getPostId).collect(Collectors.toList()))
			.containsExactly(10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L, 1L);
	}

	private void initData() {
		fakeMember = Member.builder()
			.username(USERNAME).build();
		ReflectionTestUtils.setField(fakeMember, "id", FAKE_MEMBER_ID);

		for (Long fakePostId: FAKE_POST_IDS) {
			Post post = Post.builder()
				.title(POST_TITLE)
				.content(POST_CONTENT)
				.writer(fakeMember)
				.build();
			ReflectionTestUtils.setField(post, "id", fakePostId);
			fakePosts.add(post);
		}
	}
}