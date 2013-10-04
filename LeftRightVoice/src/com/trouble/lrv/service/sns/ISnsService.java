package com.trouble.lrv.service.sns;

import java.util.List;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */

public interface ISnsService<T> {
	
	//글 쓰기
	void addWrite(T write);
	
	//글 삭제
	void removeWrite(T write);

	//글 목록 가져오기
	List<T> getWrite(String loginUserName);	
	

}
