package com.trouble.lrv.dao.sns;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface SnsMapper<T> {
	//글 등록
	void addPost(T post);

	//글 삭제
	void removePost(T post);

	//글 목록 조회
	List<T> getPost(String id);

}
