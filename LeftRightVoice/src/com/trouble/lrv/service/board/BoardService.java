package com.trouble.lrv.service.board;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trouble.lrv.core.annotation.SecureText;
import com.trouble.lrv.dao.board.BoardMapper;
import com.trouble.lrv.domain.autotext.AutoCompletionText;
import com.trouble.lrv.domain.board.Board;

@Service
@Transactional
public class BoardService implements IBoardService {
	
	private final Logger logger = LoggerFactory.getLogger(BoardService.class);
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	@SecureText
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<Board> getList(){
		List<Board> board = boardMapper.getList(); 
		return board;
	}
	
	@Override
	@PreAuthorize("hasRole('ROLE_USER')")
	public void write(Board board){
		boardMapper.write(board);
		logger.info("{} write", board);
	}

	@Override
	@SecureText
	@PreAuthorize("hasRole('ROLE_USER')")
	public Board read(int wno) {
		return boardMapper.read(wno);
	}

	@Override
	public List<AutoCompletionText> autotext(String searchText) {
		return boardMapper.autoText(searchText);
	}

}
