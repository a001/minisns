package com.trouble.lrv.service.securityuser;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */
public interface IUserService extends UserDetailsService{

	void createUser(UserDetails user);

	void updateUser(UserDetails user);

	void deleteUser(String username);

	void changePassword(String oldPassword, String newPassword);

	boolean userExists(String username);
	
	List<String> userList(String username);

	void addFriend(String loginUsename, String... userlist);
	
	String findFriend(String loginUsername, String username);
	
	String[] getFriend(String loginUsername);

	void delFriend(String username);

	void createBasicFriend(String username);

}