package com.trouble.lrv.service.sns.home;

public interface ISnsHomeService {
	int followerCount(String friendUserName);
	int followingCount(String loginUserName);
}
