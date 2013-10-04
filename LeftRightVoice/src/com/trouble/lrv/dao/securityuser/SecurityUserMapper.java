package com.trouble.lrv.dao.securityuser;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.trouble.lrv.domain.user.SecurityUser;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */

@Repository
public interface SecurityUserMapper {
	/**
     * Create a new user with the supplied details.
     */
    void createUser(UserDetails user);
    
    /**
     * 권한 추가
     */
    void insertUserAuthorities(String username, String authority);

    /**
     * Update the specified user.
     */
    void updateUser(UserDetails user);

    /**
     * Remove the user with the given login name from the system.
     */
    void deleteUser(String username);

    /**
     * Modify the current user's password. This should change the user's password in
     * the persistent user repository (datbase, LDAP etc).
     *
     * @param oldPassword current password (for re-authentication if required)
     * @param newPassword the password to change to
     */
    void changePassword(String oldPassword, String newPassword);

    /**
     * Check if a user with the supplied login name exists in the system.
     */
    List<String> userExists(String username);
    
    /**
     * 사용자 이름으로 유저 조회
     */
	SecurityUser loadUserByUsername(String username);
	
	/**
	 * 로그인아이디, 차단 친구 를 제외한 사용자 전체 리스트 조회
	 */
	List<String> userList(String username);

	/**
	 * username 을 친구등록 한다.
	 */
	void addFriend(String loginUser, String username);

	/**
	 * username 친구를 찾는다.
	 */
	String findFriend(String loginUsername, String username);
	
	/**
	 * loginUser의 친구목록을 가져온다
	 * @param loginUsername
	 * @return
	 */
	String[] getFriend(String loginUsername);

	/**
	 * loginUser의 친구목록을 전체 삭제한다
	 * @param username
	 */
	void delFriend(String username);

	
	/**
	 * 회원 가입시 기본 친구를 생성한다(현재는 자기자신)
	 * @param username
	 */
	void createBasicFriend(String username);

}
