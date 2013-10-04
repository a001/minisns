package com.trouble.lrv.domain.sns;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.ibatis.type.Alias;

import com.trouble.lrv.domain.BaseTimeDomain;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */

@Alias("PostLike")
@XmlRootElement
public class PostLike extends BaseTimeDomain{
	private static final long serialVersionUID = 2270411673871902986L;

	private String postId;
	
	private int likeId;
	
	private String ownerId;
	
	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public int getLikeId() {
		return likeId;
	}

	public void setLikeId(int likeId) {
		this.likeId = likeId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	

}
