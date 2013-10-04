package com.trouble.lrv.controller.sns.post;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.trouble.lrv.domain.sns.Posting;
import com.trouble.lrv.service.securityuser.IUserService;
import com.trouble.lrv.service.sns.post.IPostService;
 
/**
 * * sns 글 등록시 호출되는 Controller 이다.
 * 멀티쓰레드에 안전한 전파를 목적으로 한 Map 을 인스턴스 변수로 선언하여, 댓글이 등록될 때마다
 * Map 에 put되어 있는 모든 DeferredResult 에 값을 set(setResult)하여 전파한다.
 * 글은 글 작성자의 Follower에게만 전파된다 
 * @author Choi Bin
 * @since 1.0 beta
 */
@Controller
@RequestMapping("/post")
public class PostController {
	private final Logger logger = LoggerFactory.getLogger(PostController.class);
			
	/**
	 *  sns에 접속한 모든 유저가 공유할 수 있는 멀티쓰레드에 안전한 전파를 목적으로 한 Map
	 *  Map 의 Key   : SNS에 등록 시도한 글 정보 List
	 *  Map 의 Value : SNS에 접속한 유저정보   
	 */
	private final Map<DeferredResult<List<Posting>>, String> responseBodyPostMap = 
			new ConcurrentHashMap<DeferredResult<List<Posting>>, String>();
	
	private IPostService postService;
	private IUserService userService;
	
	@Autowired
	public void init(IPostService postService, IUserService userService){
		this.postService = postService;
		this.userService = userService;
	}
	
	/**
	 * Result를 set하기 위한 DeferredResult 를 생성하여 인스턴스변수인 responseBodyPostMap 에 put 한다.
	 * DeferredResult의 작업이 끝나면 인스턴스 변수인 responseBodyPostMap 에서 제거한다.
	 * 이 메소드는 snshome.jsp 의 postList() function 에 의해 무한 루프로 실행된다.
	 * @param principal 현재 로그인한 유저정보를 가져온다
	 * @return result
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody DeferredResult<List<Posting>> postDeferredResult(Principal principal){
		final DeferredResult<List<Posting>> result = new DeferredResult<List<Posting>>(null, Collections.emptyList());
		this.responseBodyPostMap.put(result, principal.getName());
		
		result.onCompletion(new Runnable() {
			@Override
			public void run() {
				responseBodyPostMap.remove(result);
			}
		});
		
		return result;
	}

	/**
	 * SNS 글 등록시 호출된다. 글이 등록되면 데이터베이스에 저장한 후, 
	 * List에 담아 인스턴스변수인 responseBodyPostMap 담겨있는 DeferredResult 에 set 해준다
	 * @param principal 현재 로그인 한 유저정보를 가져온다
	 * @param comment 댓글 등록 정보를 가져온다
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody void postMessage(Principal principal, Posting posting) throws InterruptedException{
		
		insertPostToDB(posting, principal.getName());
		
		List<Posting> post = new CopyOnWriteArrayList<Posting>();
		post.add(posting);
		
		setResult(post, principal.getName());
	}
	
	/**
	 * SNS 글 등록시 등록된 댓글의 내용을 데이터베이스에 저장한다
	 * @param posting
	 * @param postUserName
	 */
	private void insertPostToDB(Posting posting, String postUserName) {
		posting.setOwnerId(postUserName);
		this.postService.addWrite(posting);		
	}
	
	/**
	 * SNS 글 전파의 경우, 댓글과는 다르게 나를 Following 하고 있는 사용자에게만 전파되어야 한다.
	 * sns에 접속해 있는 모든 사용자는 인스턴스변수인 responseBodyPostMap에 key로 DeferredResult, value로 해당 유저정보를 put 한다.
	 * 글을 전파하기 전에 전파하려는 유저가 글 등록자의 Follower인지 체크 한 후,
	 * Follower이면 전파하고 (setResult(post)), Follower가 아니면 전파하지 않는다 (setResult(null))     
	 * @param post
	 * @param postUserName
	 */
	private void setResult(List<Posting> post, String postUserName) {
		for (Entry<DeferredResult<List<Posting>>, String> entry : this.responseBodyPostMap.entrySet()) {
			
			if(postUserName.equals(userService.findFriend(entry.getValue(), postUserName))){
				entry.getKey().setResult(post);
				logger.info("{} propagation success.", entry.getValue());
			}else{
				entry.getKey().setResult(null);
				logger.info("not propagation. because {} user not follow posted user.", entry.getValue());
			}
		}
		
		logger.info("{} posted success.", postUserName);
		
	}
	
}
