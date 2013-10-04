package com.trouble.lrv.core.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FileCheckValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new UnsupportedOperationException();

	}

}
