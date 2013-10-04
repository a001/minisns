package com.trouble.lrv.domain.user;

import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.trouble.lrv.domain.BaseTimeDomain;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */

@Alias("SecurityUser")
@XmlRootElement
public class SecurityUser extends BaseTimeDomain implements UserDetails{
	private static final long serialVersionUID = -2485342645344623683L;
	
	@Size(min=2, max=10)
	private String username;
	
	@Size(min=8, max=20)
	private String email;

	@Size(min=4, max=12)
	private String password;
	
	private String userPhoto;
	
	private String[] friendlist;
	
	private boolean enabled, accountNonExpired, accountNonLoked, credentialsNonexpired;
		
	private Collection<? extends GrantedAuthority> authority;
	
	public String getUserPhoto() {
		return userPhoto;
	}
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	@NotNull
	private int politicsColor;
	
	
	public String[] getFriendlist() {
		return friendlist;
	}
	public void setFriendlist(String[] friendlist) {
		this.friendlist = friendlist;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public int getPoliticsColor() {
		return politicsColor;
	}
	public void setPoliticsColor(int politicsColor) {
		this.politicsColor = politicsColor;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities){
		this.authority = authorities;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authority;
	}
	
	public void setAccountNonLoked(boolean accountNonLoked) {
		this.accountNonLoked = accountNonLoked;
	}
	public void setCredentialsNonexpired(boolean credentialsNonexpired) {
		this.credentialsNonexpired = credentialsNonexpired;
	}
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}
	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	@Override
	public boolean isAccountNonLocked() {
		return accountNonLoked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonexpired;
	}
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	
}
