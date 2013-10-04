package com.trouble.lrv.service.sns.likeit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trouble.lrv.dao.sns.LikeItMapper;
import com.trouble.lrv.domain.sns.PostLike;

@Service
@Transactional
public class LikeItService implements ILikeItService {
	@Autowired
	LikeItMapper likeItMapper;

	@Override
	public void addWrite(PostLike postLike) {
		this.likeItMapper.addPost(postLike);
	}

	@Override
	public void removeWrite(PostLike postLike) {
		this.likeItMapper.removePost(postLike);
	}

	@Override
	@Transactional(readOnly=true)
	public List<PostLike> getWrite(String postId) {
		return this.likeItMapper.getPost(postId);
	}

}
