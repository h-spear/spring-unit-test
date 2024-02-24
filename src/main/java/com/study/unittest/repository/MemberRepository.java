package com.study.unittest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.unittest.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
