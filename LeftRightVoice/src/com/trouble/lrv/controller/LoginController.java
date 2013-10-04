package com.trouble.lrv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */

@Controller
public class LoginController {
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(){
		return "/user/login";
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void submitLogin(String userName, String password){
	}

}
