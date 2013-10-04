package com.trouble.lrv.domain.sns;

import com.trouble.lrv.domain.BaseTimeDomain;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */
public class Comment extends BaseTimeDomain{
	private static final long serialVersionUID = -4022879285892569709L;
	
	private int comId;
	
	private int postId;
	
	private String comText;
	
	private String ownerId;
	
	public int getComId() {
		return comId;
	}
	public void setComId(int comId) {
		this.comId = comId;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getComText() {
		return comText;
	}
	public void setComText(String comText) {
		this.comText = comText;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	@Override
	public String toString() {
		return "Comment [comId=" + comId + ", postId=" + postId + ", comText="
				+ comText + ", ownerId=" + ownerId + "]";
	}
	
	

}
