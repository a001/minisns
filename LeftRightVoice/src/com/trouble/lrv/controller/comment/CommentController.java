package com.trouble.lrv.controller.comment;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.trouble.lrv.domain.sns.Comment;
import com.trouble.lrv.service.sns.comment.ICommentService;
/**
 * sns 댓글 등록시 호출되는 Controller 이다.
 * 멀티쓰레드에 안전한 전파를 목적으로 한 Queue 를 인스턴스 변수로 선언하여, 댓글이 등록될 때마다
 * Queue 에 add되어 있는 모든 DeferredResult 에 값을 set(setResult)하여 전파한다.
 * 댓글은 Following, Follower에 무관하게 모든 유저에게 전파되고 있다.
 * 
 * @author Choi Bin
 * @since 1.0 beta
 */

@Controller
@RequestMapping(value = "/comment")
public class CommentController {
	private final Logger logger = LoggerFactory.getLogger(CommentController.class);
	/**
	 *  sns에 접속한 모든 유저가 공유할 수 있는 멀티쓰레드에 안전한 전파를 목적으로 한 Queue 
	 */
	private final Queue<DeferredResult<List<Comment>>> responseBodyCommentQueue = 
			new ConcurrentLinkedQueue<DeferredResult<List<Comment>>>();
	
	@Autowired
	private ICommentService commentService;
	
	/**
	 * Result를 set하기 위한 DeferredResult 를 생성하여 인스턴스변수인 responseBodyCommentQueue 에 add한다.
	 * DeferredResult의 작업이 끝나면 인스턴스 변수인 responseBodyCommentQueue 에서 제거한다.
	 * 이 메소드는 ?.jsp 의 commentList() function 에 의해 무한 루프로 실행된다.
	 * @param principal 현재 로그인한 유저정보를 가져온다
	 * @return result
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody DeferredResult<List<Comment>> commentDeferredResult(Principal principal){
		final DeferredResult<List<Comment>> result = new DeferredResult<List<Comment>>(null, Collections.emptyList());
		//sns에 접속한 모든 유저가 공유 하는 Queue에 DeferredResult 를 생성하여 add한다
		this.responseBodyCommentQueue.add(result);
		
		
		result.onCompletion(new Runnable() {
			@Override
			public void run() {
				//작업이 끝나면 result 를 제거한다
				responseBodyCommentQueue.remove(result);
			}
		});
		
		return result;
	}
	
	/**
	 * SNS 댓글 등록시 호출된다. 댓글이 등록되면 데이터베이스에 저장한 후, 
	 * List에 담아 인스턴스변수인 responseBodyCommentQueue에 담겨있는 DeferredResult 에 set 해준다
	 * @param principal 현재 로그인 한 유저정보를 가져온다
	 * @param comment 댓글 등록 정보를 가져온다
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody void postMessage(Principal principal, Comment comment) {
		
		insertPostToDB(comment, principal.getName());
		
		List<Comment> commentList = new CopyOnWriteArrayList<Comment>();
		commentList.add(comment);
		
		setResult(commentList);
	}

	/**
	 * SNS 댓글 등록시 등록된 댓글의 내용을 데이터베이스에 저장한다
	 * @param comment
	 * @param postUserName
	 */
	private void insertPostToDB(Comment comment, String postUserName) {
		comment.setOwnerId(postUserName);
		this.commentService.addWrite(comment);		
	}
	
	/**
	 * @see postMessage(Principal principal, Comment comment)
	 * 댓글이 등록되었을 경우, sns에 접속한 모든 유저들이 공유하는 Queue 에 담겨있는 DeferredResult에
	 * 댓글 내용을 set하여 전파한다.
	 * @param commentList
	 */
	private void setResult(List<Comment> commentList) {
		for (DeferredResult<List<Comment>> result : this.responseBodyCommentQueue) {
			result.setResult(commentList);
			logger.info("{} propagation success.", commentList);
		}			
	}
}
