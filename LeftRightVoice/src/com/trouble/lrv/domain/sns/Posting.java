package com.trouble.lrv.domain.sns;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.ibatis.type.Alias;

import com.trouble.lrv.domain.BaseTimeDomain;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */

@Alias("Posting")
@XmlRootElement
public class Posting extends BaseTimeDomain{
	private static final long serialVersionUID = -1084931126066502621L;
	
	private int postId;

	private String post;
	
	private String userPhoto;
	
	private String ownerId;
	
	private Comment comment;
	
	private List<Comment> commentList;
	
	private List<PostLike> postLikeList;
	
	private boolean isLike;
	
	public boolean isLike() {
		return isLike;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	public List<Comment> getCommentList() {
		return commentList;
	}
	
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public List<PostLike> getPostLikeList() {
		return postLikeList;
	}

	public void setPostLikeList(List<PostLike> postLikeList) {
		this.postLikeList = postLikeList;
	}
	
}
