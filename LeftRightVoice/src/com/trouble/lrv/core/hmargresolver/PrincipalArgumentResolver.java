package com.trouble.lrv.core.hmargresolver;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.trouble.lrv.core.annotation.Principal;
import com.trouble.lrv.domain.user.SecurityUser;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */

@Component
public class PrincipalArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Principal.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		
		return (SecurityUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
