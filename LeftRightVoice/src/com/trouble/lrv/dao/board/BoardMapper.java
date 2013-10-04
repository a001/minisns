package com.trouble.lrv.dao.board;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.trouble.lrv.domain.autotext.AutoCompletionText;
import com.trouble.lrv.domain.board.Board;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */

@Repository
public interface BoardMapper {
	
	//목록 조회
	List<Board> getList();
	
	//글 카운트
	int getCount();

	//글 쓰기
	void write(Board board);

	//글 조회
	Board read(int wno);

	List<AutoCompletionText> autoText(String searchText);


}
