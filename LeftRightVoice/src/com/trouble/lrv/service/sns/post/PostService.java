package com.trouble.lrv.service.sns.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trouble.lrv.dao.sns.PostMapper;
import com.trouble.lrv.domain.sns.Posting;

/**
 * 글 등록, 삭제
 * @author Choi Bin
 * @since 1.0 beta
 */

@Service
@Transactional
public class PostService implements IPostService {
	@Autowired
	PostMapper postMapper;

	@Override
	public void addWrite(Posting post) {
		postMapper.addPost(post);
	}

	@Override
	public void removeWrite(Posting post) {
		postMapper.removePost(post);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Posting> getWrite(String loginUserName) {
		return postMapper.getPost(loginUserName);
	}

}
