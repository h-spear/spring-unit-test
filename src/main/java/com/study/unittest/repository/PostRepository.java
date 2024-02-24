package com.study.unittest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.study.unittest.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	@Query("select p from Post p join fetch p.writer order by p.id desc")
	List<Post> findAllWithWriter();

	@Query("select p from Post p join fetch p.writer where p.id = :postId")
	Optional<Post> findByIdWithWriter(@Param("postId") Long postId);
}
