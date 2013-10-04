package com.trouble.lrv.domain;

import java.io.Serializable;

import java.util.Date;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */
public class BaseTimeDomain implements Serializable, Comparable<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 492002643021983972L;
	
	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date				insertTime = new Date();

	@DateTimeFormat(iso = ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date				updateTime = new Date();

	@Override
	public int compareTo(Object o) {
		return CompareToBuilder.reflectionCompare(this, o);
	}
	
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	

}
