package com.trouble.lrv.domain.autotext;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.ibatis.type.Alias;

import com.trouble.lrv.domain.BaseTimeDomain;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */

@Alias("AutoCompletionText")
@XmlRootElement
public class AutoCompletionText extends BaseTimeDomain{
	/**
	 * 
	 */
	private static final long serialVersionUID = -722818772833241213L;
	private String autoText;
	
	public String getAutoText() {
		return autoText;
	}
	public void setAutoText(String autoText) {
		this.autoText = autoText;
	}
	
	

}
