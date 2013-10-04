package com.trouble.lrv.domain.enumset;

public enum PoliticsColor {
	LEFT(1, "보수"), RIGHT(2, "진보");
	
	private int value;
	private String name;
	
	private PoliticsColor(int value, String name){
		this.value = value;
		this.name = name;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public String getName(){
		return this.name;
	}
	
	public static PoliticsColor valueOf(int value){
		switch (value) {
		case 1: return LEFT;
		case 2: return RIGHT;
		default:
			throw new IllegalArgumentException("잘못된 정치성향 입니다");
		}
	}
	
}
