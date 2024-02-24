package com.study.unittest.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String username;
	private String password;

	@OneToMany(mappedBy = "writer")
	private List<Post> posts = new ArrayList<>();

	protected Member() {
	}

	@Builder
	public Member(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
