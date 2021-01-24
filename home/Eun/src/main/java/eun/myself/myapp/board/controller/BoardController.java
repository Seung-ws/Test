package eun.myself.myapp.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eun.myself.myapp.board.model.Board;
import eun.myself.myapp.board.service.IBoardCategoryService;
import eun.myself.myapp.board.service.IBoardService;
import eun.myself.myapp.syslog.SysLog;

@Controller
public class BoardController {

	@Autowired
	IBoardService boardService;
	
	@Autowired
	IBoardCategoryService categoryService;
	
	@Autowired
	SysLog syslog;

	
	@RequestMapping("/boardList/cat/{category_id}/{page}")
	public String getListByCategory(@PathVariable int category_id, @PathVariable int page, HttpSession session, Model model)
	{
		//게시물 카테고리 리스트를 불러오는 페이지
		syslog.getLog("1");
		//카테고리 아이디 값과 페이지를 변수로 받는다.
		//세션에 페이지를 모델Requst에 카테고리 아이디를 넣는다.
		session.setAttribute("page", page);
		model.addAttribute("category_id", category_id);
		syslog.getLog("2");
		List<Board> boardList = boardService.selectArticleListByCategory(category_id, page);
		model.addAttribute("boardList", boardList);
		syslog.getLog("3");
		// paging start
		int bbsCount = boardService.selectTotalArticleCountByCategoryId(category_id);
		int totalPage = 0;
		syslog.getLog("4");
		if(bbsCount > 0) {
			totalPage= (int)Math.ceil(bbsCount/10.0);
		}
		syslog.getLog("5");
		model.addAttribute("totalPageCount", totalPage);
		model.addAttribute("page", page);
		syslog.getLog("6");
		return "boardList/boardList";
	}
	
	@RequestMapping(value = "/boardList/cat/{category_id}")
	public String getListByCategory(@PathVariable int category_id,HttpSession session, Model model) {
		//카테고리 아이디 값을 변수로 받는다.
		//세션 과 모델은 임시로 받아오고 카테고리 리스트를 전부가져오기위한 설정
		syslog.getLog("->boardList/cat/"+category_id);
		return getListByCategory(category_id, 1, session, model);
	}
	
	@RequestMapping("/board/{board_id}/{page}")	
	public String getBoardDetails(@PathVariable int board_id, @PathVariable int page, Model model) {
		syslog.getLog("1");
		//게시물의 정보를 가져오는 페이지이다
		Board board = boardService.selectArticle(board_id);
		model.addAttribute("board", board);
		model.addAttribute("page", page);
		model.addAttribute("category_id", board.getCategory_id());
		syslog.getLog("2");
		//logger.info("getBoardDetails " + board.toString());
		return "boardView/boardView";
	}

	@RequestMapping("/board/{board_id}")
	public String getBoardDetails(@PathVariable int board_id, Model model) {
		//게시물 세부 정보 보기 
		syslog.getLog("1");
		return getBoardDetails(board_id, 1, model);
	}
	
}
