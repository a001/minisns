package com.trouble.lrv.domain.enumset;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */
public enum Roles {
	ROLE_RIGHT(1,"ROLE_RIGHT"),
	ROLE_LEFT(2,"ROLE_LEFT"),
	ROLE_ADMIN(3,"ROLE_ADMIN"),
	ROLE_STEP(4,"ROLE_STEP"),
	DEFAULT_ROLE(5,"ROLE_USER");
	
	String role;
	int value;
	
	private Roles(int value, String role){
		this.value = value;
		this.role = role;
	}
	
	public String getName(){
		return role;
	}
	
	public int getValue(){
		return value;
		
	}
	
	public static Roles valueOf(int value){
		switch (value) {
		case 1: return ROLE_RIGHT;
		case 2: return ROLE_LEFT;
		case 3: return ROLE_ADMIN;
		case 4: return ROLE_STEP;
		case 5: return DEFAULT_ROLE;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	

}
