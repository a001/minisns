package com.trouble.lrv.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trouble.lrv.domain.sns.Comment;
import com.trouble.lrv.domain.sns.Posting;
import com.trouble.lrv.service.sns.post.IPostService;

import context.AppContext;

//@ContextConfiguration(classes=AppContext.class)
//@RunWith(SpringJUnit4ClassRunner.class)
public class PostServiceTest {
/*	
	@Autowired
	IPostService postService;
	
	@Autowired
	ApplicationContext applicationContext;
	
	private Posting posting;
	
	@Before
	public void setup(){
		posting = new Posting();
		posting.setOwnerId("kwo2002");
		posting.setPost("테스트 포스팅");
	}
	
	@Test
	public void getPostTest_포스트리스트조회(){
		List<Posting> postList = this.postService.getWrite("kwo2002");
		
		assertThat(postList.size(), is(2));
		
	}
	
	@Test
	public void getCommentListTest_포스트에해당하는댓글리스트조회(){
		List<Posting> postList = this.postService.getWrite("kwo2002");
		
		for (Comment comment : postList.get(0).getCommentList()) {
			System.out.println(comment.getComText());
		}
		assertThat(postList.get(0).getCommentList().size(), is(2));
	}
	
	@Test
	public void insertPost_포스트등록(){
		this.postService.addWrite(posting);
		
		System.out.println(this.posting.toString());
	}
	
	@Test
	public void postLikeTest_포스트좋아요(){
		List<Posting> postList = this.postService.getWrite("kwo2002");
		
		assertThat(postList.get(0).getPostLikeList().size(), is(2));
		
		assertThat(postList.get(0).getPostLikeList().get(0).getOwnerId(), is("kwo2020"));
	}
	
	@Test
	public void registerBeanNamePrint(){
		  
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		
		for (String string : beanNames) {
			System.out.println(string);
		}
	
	}
*/
}
