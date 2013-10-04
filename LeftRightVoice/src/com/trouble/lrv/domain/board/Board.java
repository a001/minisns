package com.trouble.lrv.domain.board;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.NotEmpty;

import com.trouble.lrv.domain.BaseTimeDomain;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */

@Alias("Board")
@XmlRootElement
public class Board extends BaseTimeDomain{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2872321366917384298L;
	@NotEmpty
	private String subject;
	@NotEmpty
	private String content;
	
	private String ownerId;
	private int wno;
	
	public int getWno() {
		return wno;
	}
	public void setWno(int wno) {
		this.wno = wno;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}


}
