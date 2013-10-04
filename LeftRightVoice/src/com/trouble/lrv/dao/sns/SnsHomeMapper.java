package com.trouble.lrv.dao.sns;

public interface SnsHomeMapper {
	int followerCount(String friendUserName);
	int followingCount(String loginUserName);

}
