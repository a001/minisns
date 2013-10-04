package com.trouble.lrv.service.sns.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trouble.lrv.dao.sns.CommentMapper;
import com.trouble.lrv.domain.sns.Comment;

@Service
@Transactional
public class CommentService implements ICommentService {

	@Autowired
	CommentMapper commentMapper;
	
	@Override
	public void addWrite(Comment comment) {
		commentMapper.addPost(comment);		
	}

	@Override
	public void removeWrite(Comment comment) {
		commentMapper.removePost(comment);	
	}

	@Override
	@Transactional(readOnly=true)
	public List<Comment> getWrite(String loginUserName) {
		return commentMapper.getPost(loginUserName);
	}

}
