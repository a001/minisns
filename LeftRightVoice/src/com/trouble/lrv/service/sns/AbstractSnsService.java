package com.trouble.lrv.service.sns;

import java.util.List;

import com.trouble.lrv.dao.sns.SnsMapper;

//현재 사용하지 않음
public abstract class AbstractSnsService<T, M extends SnsMapper<T>> implements ISnsService<T> {
	
	M m;

	@Override
	public void addWrite(T write) {
		m.addPost(write);
	}

	@Override
	public void removeWrite(T write) {
		m.removePost(write);

	}

	@Override
	public List<T> getWrite(String loginUserName) {
		return m.getPost(loginUserName);
	}

}
