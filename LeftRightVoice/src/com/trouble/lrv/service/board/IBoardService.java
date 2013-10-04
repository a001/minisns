package com.trouble.lrv.service.board;

import java.util.List;

import com.trouble.lrv.domain.autotext.AutoCompletionText;
import com.trouble.lrv.domain.board.Board;

public interface IBoardService {

	List<Board> getList();

	void write(Board board);

	Board read(int wno);
	
	List<AutoCompletionText> autotext(String searchText);


}