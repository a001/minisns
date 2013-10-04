package com.trouble.lrv.service.securityuser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.trouble.lrv.dao.securityuser.SecurityUserMapper;
import com.trouble.lrv.domain.user.SecurityUser;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */

@Transactional
public class UserService extends JdbcDaoImpl implements IUserService {
	private SecurityUserMapper userMapper;
	private final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public void setSecurityUserMapper(SecurityUserMapper userMapper){
		this.userMapper = userMapper;
	}
	
	@Override
	protected UserDetails createUserDetails(String username,
			UserDetails userFromUserQuery,
			List<GrantedAuthority> combinedAuthorities) {
		String returnUsername = userFromUserQuery.getUsername();
		if(!isUsernameBasedPrimaryKey()){
			returnUsername = username;
		}
		SecurityUser user = new SecurityUser();
		
		user.setUsername(returnUsername);
		user.setPassword(userFromUserQuery.getPassword());
		user.setEnabled(userFromUserQuery.isEnabled());
		user.setAccountNonExpired(true);
		user.setAccountNonLoked(true);
		user.setCredentialsNonexpired(true);
		user.setAuthorities(combinedAuthorities);
		user.setEmail(((SecurityUser) userFromUserQuery).getEmail());
		user.setUserPhoto(((SecurityUser) userFromUserQuery).getUserPhoto());
		user.setPoliticsColor(((SecurityUser) userFromUserQuery).getPoliticsColor());
		
		
		return user;
	}
	
	@Override
	@Transactional(readOnly=true)
	protected List<UserDetails> loadUsersByUsername(String username) {
		List<UserDetails> userDetailsList = new ArrayList<UserDetails>();
		try{
			userDetailsList.add(userMapper.loadUserByUsername(username));
		}catch(Exception e){
			logger.info("exception occured! : {}", e.getMessage());
		}
		
		return userDetailsList;
	}
	
	@Override
	public void createUser(UserDetails user) {
		validateUserDetails(user);
		userMapper.createUser(user);
		
		if(getEnableAuthorities()){
			insertUserAuthorities(user);
		}
	}
	
	private void insertUserAuthorities(UserDetails user) {
        for (GrantedAuthority auth : user.getAuthorities()) {
        	userMapper.insertUserAuthorities(user.getUsername(), auth.getAuthority());
        }
    }

	@Override
	public void updateUser(UserDetails user) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void deleteUser(String username) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	@Transactional(readOnly=true)
	public boolean userExists(String username) {
		 List<String> users = userMapper.userExists(username); 

	        if (users.size() > 1) {
	            throw new IncorrectResultSizeDataAccessException("이 이름을 가진 사용자가 2명 이상입니다 : '" + username + "'", 1);
	        }

	        return users.size() == 1;
	}
	
	private void validateUserDetails(UserDetails user) {
        Assert.hasText(user.getUsername(), "이름은 null을 허용하지 않습니다.");
        validateAuthorities(user.getAuthorities());
    }

    private void validateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "권한은 null을 허용하지 않습니다.");

        for (GrantedAuthority authority : authorities) {
        	logger.info("authority : {}", authority.getAuthority());
            Assert.notNull(authority, "권한 항목은 null을 허용하지 않습니다.");
            Assert.hasText(authority.getAuthority(), "권한 항목의 text는 null을 허용하지 않습니다.");
            
        }
    }

	@Override
	@Transactional(readOnly=true)
	public List<String> userList(String username) {
		return userMapper.userList(username);
	}

	@Override	
	public void addFriend(String loginUsername, String... username) {
		for (String user : username) {
			userMapper.addFriend(loginUsername, user);			
		}
		
	}

	@Override
	@Transactional(readOnly=true)
	public String findFriend(String loginUsername, String username) {
		return userMapper.findFriend(loginUsername, username);
		
	}

	@Override
	@Transactional(readOnly=true)
	public String[] getFriend(String loginUsername) {
		return userMapper.getFriend(loginUsername);
	}

	@Override
	public void delFriend(String username) {
		userMapper.delFriend(username);
	}
	
	@Override
	public void createBasicFriend(String username) {
		userMapper.createBasicFriend(username);
		
	}
}
