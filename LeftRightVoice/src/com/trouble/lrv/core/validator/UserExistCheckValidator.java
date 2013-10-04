package com.trouble.lrv.core.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.trouble.lrv.domain.user.SecurityUser;
import com.trouble.lrv.service.securityuser.IUserService;


/**
 * @author Choi Bin
 * @since 1.0 beta
 */

@Component
public class UserExistCheckValidator implements Validator {
	@Autowired
	IUserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return (UserDetails.class.isAssignableFrom(clazz));
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		SecurityUser loginUser = (SecurityUser)arg0;
		
		if(userService.userExists(loginUser.getUsername())){
			errors.rejectValue("username",  "user.exist");
		}
	}

}
