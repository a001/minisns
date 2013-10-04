package com.trouble.lrv.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.trouble.lrv.core.annotation.Principal;
import com.trouble.lrv.domain.autotext.AutoCompletionText;
import com.trouble.lrv.domain.board.Board;
import com.trouble.lrv.domain.user.SecurityUser;
import com.trouble.lrv.service.board.IBoardService;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */

@Controller
@SessionAttributes("board")
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private IBoardService boardService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultPage(@Principal SecurityUser principal, Model model) {
		
		model.addAttribute("board", boardService.getList());
		
		model.addAttribute("user", principal);
		return "/main/welcome";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(@Principal SecurityUser principal, Model model){
		Board board = new Board();
		
		board.setOwnerId(principal.getUsername());
		logger.info("board.getOwnerId() : {}", board.getOwnerId());
		
		model.addAttribute("board", board);
		
		return "/board/write";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@ModelAttribute("board") @Valid Board board, BindingResult result, SessionStatus status){
		if(result.hasErrors()){
			return "/board/write";
		}
		
		boardService.write(board);
		status.setComplete();
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/read/{wno}", method = RequestMethod.GET)
	public String read(@PathVariable int wno, Model model){
		model.addAttribute("board", boardService.read(wno));
		
		return "/board/read";
	}
	
	@RequestMapping(value = "/autotext", produces={"application/xml", "application/json"})
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<AutoCompletionText> autoText(String searchText){
		
		if(StringUtils.hasText(searchText)){
			return boardService.autotext(searchText);		
		}
		
		return null;
	}
	
}
