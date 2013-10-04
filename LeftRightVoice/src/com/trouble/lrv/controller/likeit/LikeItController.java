package com.trouble.lrv.controller.likeit;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trouble.lrv.domain.sns.PostLike;
import com.trouble.lrv.service.sns.likeit.ILikeItService;

/**
 * SNS에 등록된 글을 '좋아요' 했을 때, 호출되는 컨트롤러이다.
 * '좋아요'는 DeferredResult를 이용하지는 않았다. 글등록, 댓글등록 두가지만 DeferredResult를 이용한 실시간 전파를 이용한다.
 * '좋아요'를 DeferredResult를 이용한 실시간 전파를 이용하지 않은 이유는 DeferredResult가 무한 루프로 실행되면서 값을 받아오기 때문에 
 * 성능상 이슈가 발생할 것을 걱정하였기 때문이다. (성능 상 이슈에 대한 정확한 테스트 및 검증은 아직 이뤄지지 않았다)
 * 또한, '좋아요' 는 실시간으로 전파 해줘야할 필요성을 느끼지 못하였다. 
 * @author Choi Bin
 * @since 1.0 beta
 */
@Controller
public class LikeItController {
	
	@Autowired
	private ILikeItService likeItService;
	
	/**
	 * '좋아요' 를 하였을 때, 호출된다. 
	 * view 에서 json이나 xml로 호출하여야만 호출된다 (@see @RequestMapping(String[] produces()))
	 * '좋아요' 정보 테이블에 '좋아요' 정보를 INSERT한다
	 * @param postLike '좋아요' 정보
	 * @param principal 로그인 유저 정보
	 * @return '좋아요'를 한 글에 대한 '좋아요' 카운트 (수정될 전망)
	 */
	@RequestMapping(value = "/likeit", produces={"application/xml", "application/json"})
	public @ResponseBody int likeIt(PostLike postLike, Principal principal){
		postLike.setOwnerId(principal.getName());
		this.likeItService.addWrite(postLike);
		
		return this.likeItService.getWrite(postLike.getPostId()).size();
	}
	
	/**
	 * '좋아요 취소' 를 하였을 때, 호출된다. 
	 * view 에서 json이나 xml로 호출하여야만 호출된다 (@see @RequestMapping(String[] produces()))
	 * '좋아요' 정보 테이블에 '좋아요' 정보를 DELETE 한다
	 * @param postLike '좋아요 취소' 정보
	 * @param principal 로그인 유저 정보
	 * @return '좋아요'를 한 글에 대한 '좋아요' 카운트 (수정될 전망)
	 */
	@RequestMapping(value = "/likeitcancel", produces={"application/xml", "application/json"})
	public @ResponseBody int likeItCancel(PostLike postLike, Principal principal){
		postLike.setOwnerId(principal.getName());
		this.likeItService.removeWrite(postLike);
		return this.likeItService.getWrite(postLike.getPostId()).size();
	}

}
