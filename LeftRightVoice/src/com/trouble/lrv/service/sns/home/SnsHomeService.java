package com.trouble.lrv.service.sns.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trouble.lrv.dao.sns.SnsHomeMapper;

@Service
public class SnsHomeService implements ISnsHomeService {
	
	@Autowired
	SnsHomeMapper snsHomeMapper;

	@Override
	public int followerCount(String friendUserName) {
		return this.snsHomeMapper.followerCount(friendUserName);
	}

	@Override
	public int followingCount(String loginUserName) {
		return this.snsHomeMapper.followingCount(loginUserName);
	}

}
