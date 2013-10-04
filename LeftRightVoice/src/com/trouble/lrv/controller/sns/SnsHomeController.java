package com.trouble.lrv.controller.sns;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trouble.lrv.core.annotation.Principal;
import com.trouble.lrv.domain.sns.Posting;
import com.trouble.lrv.domain.user.SecurityUser;
import com.trouble.lrv.service.sns.home.ISnsHomeService;
import com.trouble.lrv.service.sns.post.IPostService;

/**
 * SNS의 메인 페이지 Controller이다.
 * @author Choi Bin
 * @since 1.0 beta
 */
@Controller
@RequestMapping(value = "/sns")
public class SnsHomeController {
	
	private final static Logger logger = LoggerFactory.getLogger(SnsHomeController.class);
	private ISnsHomeService snsHomeService;
	private IPostService postService;
	
	@Autowired
	public void init(ISnsHomeService snsHomeService, IPostService postService){
		this.snsHomeService = snsHomeService;
		this.postService = postService;
	}
	
	
	/**
	 * sns에 메인페이지에 접속하였을 때 호출된다.
	 * @param principal 로그인 유저 정보
	 * @param model 화면에 표현해야 할 정보를 add 한다
	 * model의 postList - 글 목록 (최초에 읽어들이는 글 목록은 query 로 현재 로그인한 유저의 Follower 인지 , 아닌지를 판별하여 가져온다)
	 * model의 followrCount - 현재 로그인한 유저의 Follower 카운트
	 * model의 followingCount - 현재 로그인한 유저가 Following 한 카운트
	 * model의 loginUserId - 현재 로그인한 유저의 아이디
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String snsMain(@Principal SecurityUser principal, Model model){
		List<Posting> postList = this.postService.getWrite(principal.getUsername());
		//포스팅 리스트
		model.addAttribute("postList", postList);
		//팔로워 카운트
		model.addAttribute("followerCount", this.snsHomeService.followerCount(principal.getUsername())-1);
		//팔로잉 카운트
		model.addAttribute("followingCount", this.snsHomeService.followingCount(principal.getUsername())-1);
		//로그인 유저 아이디
		model.addAttribute("loginUser", principal);
		logger.info("user {} ", principal.getUserPhoto());
		return "sns/snshome";
	}
}
