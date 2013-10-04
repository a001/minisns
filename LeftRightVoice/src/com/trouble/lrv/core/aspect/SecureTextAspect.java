package com.trouble.lrv.core.aspect;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.trouble.lrv.domain.board.Board;
import com.trouble.lrv.domain.user.SecurityUser;
import com.trouble.lrv.service.securityuser.IUserService;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */

@Component
@Aspect
public class SecureTextAspect {
	private IUserService userService;
	private PasswordEncoder passwordEncoder;
	private SaltSource saltSource;
	
	@Autowired
	public void init(IUserService userService, PasswordEncoder passwordEncoder, SaltSource saltSource){
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.saltSource = saltSource;
	}
	
	@Pointcut("@annotation(com.trouble.lrv.core.annotation.SecureText)")
	public void point(){}
	
	@SuppressWarnings("unchecked")
	@Around("point()")
	public Object SecureText(ProceedingJoinPoint joinPoint) throws Throwable{
		
		try {
			Object obj = joinPoint.proceed();
			if(obj instanceof Board){
				secureSubject ((Board)obj);
			}else if(obj instanceof List){
				for (Board board : ((List<Board>)obj)) {
					secureSubject(board);
				}
			}
			
			return obj;
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	
	private void secureSubject(Board board){
		SecurityUser user = (SecurityUser)userService.loadUserByUsername(board.getOwnerId());
		SecurityUser loginUser = (SecurityUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(!user.getUsername().equals(userService.findFriend(loginUser.getUsername(), user.getUsername()))){
			if(user.getPoliticsColor() != loginUser.getPoliticsColor()){
				board.setSubject(passwordEncoder.encodePassword(board.getSubject(), saltSource.getSalt(user)));
				board.setContent(passwordEncoder.encodePassword(board.getContent(), saltSource.getSalt(user)));
			}			
		}
		
    }

}
