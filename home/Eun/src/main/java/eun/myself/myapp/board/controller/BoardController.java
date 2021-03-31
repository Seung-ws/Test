package eun.myself.myapp.board.controller;

import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eun.myself.myapp.board.model.Board;
import eun.myself.myapp.board.model.BoardCategory;
import eun.myself.myapp.board.model.BoardUploadFile;
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

	
	@RequestMapping("/boardList/cat/{category_Id}/{page}")
	public String getListByCategory(@PathVariable int category_Id, @PathVariable int page,
			HttpSession session, Model model)
	{
		/*
		 * 게시물의 카테고리id값을 기반으로  게시물을 불러오는 페이지값으로 게시물을 나눠서 보여준다.
		 * 
		 */
		//게시물 카테고리 리스트를 불러오는 페이지
	
		//카테고리 아이디 값과 페이지를 변수로 받는다.
		//세션에 페이지를 모델Requst에 카테고리 아이디를 넣는다.
		session.setAttribute("page", page);
		model.addAttribute("category_Id", category_Id);
	
		List<Board> boardList = boardService.selectArticleListByCategory(category_Id, page);
		//게시물 리스트를 카테고리 기준 가져온다
		model.addAttribute("boardList", boardList);

		// paging start
		int bbsCount = boardService.selectTotalArticleCountByCategoryId(category_Id);
		//총 게시물의 수를 조회한다
		int totalPage = 0;

		if(bbsCount > 0) {
			totalPage= (int)Math.ceil(bbsCount/10.0);//같거나 최소 정수로 반환한다.
		}
	
		model.addAttribute("totalPageCount", totalPage);
		model.addAttribute("page", page);
	
		return "boardList/boardList";
	}
	
	@RequestMapping(value = "/boardList/cat/{category_Id}")
	public String getListByCategory(@PathVariable int category_Id,HttpSession session, Model model) {
		/*
		 * 카테고리와 관련된 게시물을 보여주는데 페이지가 정해지지않아 첫페이지를 호출해 보여준다.
		 */
		syslog.getLog("->boardList/cat/"+category_Id);
		return getListByCategory(category_Id, 1, session, model);
	}
	
	@RequestMapping("/board/{board_Id}/{page}")	
	public String getBoardDetails(@PathVariable int board_Id, @PathVariable int page, Model model) {
		
		//게시물의 정보를 가져오며 뷰어에서 출력할 수 있도록 request정보 입력
		Board board = boardService.selectArticle(board_Id);
		model.addAttribute("board", board);
		model.addAttribute("page", page);
		model.addAttribute("category_Id", board.getCategory_Id());
		syslog.getLog("--게시글 보기");
		syslog.getLog("board_Id : "+board.getBoard_Id());
		syslog.getLog("category_Id : "+board.getCategory_Id());
		syslog.getLog("board_Master_Id : "+board.getBoard_Master_Id());
		syslog.getLog("reply_Board_Number : "+board.getReply_Board_Number());
		syslog.getLog("reply_Board_Step : "+board.getReply_Board_Step());
		syslog.getLog("reply_Board_StartBoard : "+board.getReply_Board_StartBoard());
		//logger.info("getBoardDetails " + board.toString());
		return "boardView/boardView";
	}

	@RequestMapping("/board/{board_Id}")
	public String getBoardDetails(@PathVariable int board_Id, Model model) {
		//게시물 정보를 불러오며 디폴트 페이지 1값 입력
		syslog.getLog(">게시물 디테일 정보");
		return getBoardDetails(board_Id, 1, model);
	}
	
	@RequestMapping(value="/boardWrite/{category_Id}", method=RequestMethod.GET)
	public String writeArticle(@PathVariable int category_Id, Model model) {
		/*카테고리들을 불러온다음에 게시물 쓰기의 선택사항 리스트로 집어 넣는다 게시물 쓰기 페이지 호출
		 * 카테고리 아이디는 초기 선택 아이템을 유도하기 위함 이고 게시물 쓰기 페이지 호출
		 */
		
		List<BoardCategory> categoryList = categoryService.selectAllCategory();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("category_Id", category_Id);
		return "boardWrite/boardWrite";
	}
	

	@RequestMapping(value="/boardWrite", method=RequestMethod.POST)
	public String writeArticle(Board board, BindingResult result, RedirectAttributes redirectAttrs) {
		/*
		 * 게시물쓰기 모드로 게시글을 작성한다.
		 * 작성한 뒤에는 게시물 목록으로 돌아온다.
		 */
		syslog.getLog("--게시글 쓰기");
		syslog.getLog("board_Id : "+board.getBoard_Id());
		syslog.getLog("category_Id : "+board.getCategory_Id());
		syslog.getLog("board_Master_Id : "+board.getBoard_Master_Id());
		syslog.getLog("reply_Board_Number : "+board.getReply_Board_Number());
		syslog.getLog("reply_Board_Step : "+board.getReply_Board_Step());
		syslog.getLog("reply_Board_StartBoard : "+board.getReply_Board_StartBoard());
		try{
			syslog.getLog("title:"+board.getBoard_Title());
			board.setBoard_Title(Jsoup.clean(board.getBoard_Title(), Whitelist.basic()));
		
			board.setBoard_Content(Jsoup.clean(board.getBoard_Content(), Whitelist.basic()));
	
			MultipartFile mfile = board.getFile();
			syslog.getLog("30%");
			if(mfile!=null && !mfile.isEmpty()) {
				syslog.getLog("/boardWrite : " + mfile.getOriginalFilename());
				BoardUploadFile file = new BoardUploadFile();
				file.setFile_Name(mfile.getOriginalFilename());
				file.setFile_Size(mfile.getSize());
				file.setFile_Content_Type(mfile.getContentType());
				file.setFile_Data(mfile.getBytes());
				syslog.getLog("/boardWrite : " + file.toString());
	
				boardService.insertArticle(board, file);
			}else {
		
				boardService.insertArticle(board);
			}
		}catch(Exception e){
			e.printStackTrace();
			redirectAttrs.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/boardList/cat/"+board.getCategory_Id();
	}
	
	@RequestMapping(value="/boardDelete/{board_Id}", method=RequestMethod.GET)
	public String deleteArticle(@PathVariable int board_Id, Model model) {
		/*
		 * 게시물 아이디 값을 기준으로 삭제 게시물의 정보를 가져오고 삭제선택 페이지를 호출 
		 */
		Board board = boardService.selectDeleteArticle(board_Id);
		model.addAttribute("category_Id", board.getCategory_Id());
		model.addAttribute("board_Id", board_Id);
		model.addAttribute("reply_Board_Number", board.getReply_Board_Number());
		model.addAttribute("master_Id",board.getBoard_Master_Id());
		return "boardDelete/boardDelete";
	}
	
	@RequestMapping(value="/boardDelete", method=RequestMethod.POST)
	public String deleteArticle(Board board, BindingResult result, HttpSession session, Model model) {
		/*
		 * 삭제페이지에서는 db에 게시물 아이디를 기반해 비밀번호를 대조 후 
		 * 게시물 아이디 값을 기준으로 삭제를 실행한다. 답글에 대해서는 추가적으로 입력받아 같이 삭제한다.
		 */
		syslog.getLog("마스터삭제:"+board.getBoard_Master_Id());
		try {
			
			String dbpw = boardService.getPassword(board.getBoard_Id());

			if(dbpw.equals(board.getBoard_Password())) {
				boardService.deleteArticle(board.getBoard_Id(),board.getBoard_Master_Id(), board.getReply_Board_Number());
				return "redirect:/boardList/cat/"+board.getCategory_Id()+"/"+(Integer)session.getAttribute("page");
			}else {
				model.addAttribute("message", "WRONG_PASSWORD_NOT_DELETED");
				return "error/runtime";
			}
		}catch(Exception e){
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
			return "error/runtime";
		}
		
	}
	
	
	@RequestMapping(value="/boardUpdate/{board_Id}", method=RequestMethod.GET)
	public String updateArticle(@PathVariable int board_Id, Model model) {
		/*
		 * 게시물 수정 게시물아이디 기반 수정 페이지 호출 한다 카테고리 종류를 먼저 불러온 후 선택되어있는 카테고리와 
		 * 게시물 정보를 넘긴다.
		 */
		List<BoardCategory> categoryList = categoryService.selectAllCategory();
		model.addAttribute("categoryList", categoryList);
		Board board = boardService.selectArticle(board_Id);
		model.addAttribute("category_Id", board.getCategory_Id());
		model.addAttribute("board", board);
		return "boardUpdate/boardUpdate";
	}

	@RequestMapping(value="/boardUpdate", method=RequestMethod.POST)
	public String updateArticle(Board board, BindingResult result, HttpSession session, RedirectAttributes redirectAttrs) {
		//logger.info("/boardUpdate " + board.toString());
		try{
			board.setBoard_Title(Jsoup.clean(board.getBoard_Title(), Whitelist.basic()));
			board.setBoard_Content(Jsoup.clean(board.getBoard_Content(), Whitelist.basic()));
			MultipartFile mfile = board.getFile();
			if(mfile!=null && !mfile.isEmpty()) {
				//logger.info("/board/update : " + mfile.getOriginalFilename());
				BoardUploadFile file = new BoardUploadFile();
				file.setFile_Id(board.getFile_Id());
				file.setFile_Name(mfile.getOriginalFilename());
				file.setFile_Size(mfile.getSize());
				file.setFile_Content_Type(mfile.getContentType());
				file.setFile_Data(mfile.getBytes());
				//logger.info("/board/update : " + file.toString());
				boardService.updateArticle(board, file);
			}else {
				boardService.updateArticle(board);
			}
		}catch(Exception e){
			e.printStackTrace();
			redirectAttrs.addFlashAttribute("message", e.getMessage());
		}

		return "redirect:/board/"+board.getBoard_Id();
	}
	@RequestMapping(value="/boardReply/{board_Id}",method=RequestMethod.GET)
	public String replyArticle(@PathVariable int board_Id, Model model) {
		/*
		 * 게시물 id 값을 기준으로 답글을 작성한다.
		 * 
		 */
		Board board = boardService.selectArticle(board_Id);
		board.setBoard_Writer("");
		board.setBoard_Writer_Id("");
		board.setBoard_Title("[Re]"+board.getBoard_Title());
		board.setBoard_Content("\n\n\n----------\n" + board.getBoard_Content());
		model.addAttribute("board", board);
		model.addAttribute("next", "reply");
		return "boardReply/boardReply";
	}
	@RequestMapping(value="/boardReply",method=RequestMethod.POST)
	public String replyArticle(Board board, BindingResult result, RedirectAttributes redirectAttrs, HttpSession session) {
		//logger.info("/board/reply : " + board.toString());

//	    if(result.hasErrors()) {
//	    	logger.debug(result.getErrorCount());
//	        return "board/write";
//	    }

		try{
			syslog.getLog("--리플 쓰기");
			syslog.getLog("board_Id : "+board.getBoard_Id());
			syslog.getLog("category_Id : "+board.getCategory_Id());
			syslog.getLog("board_Master_Id : "+board.getBoard_Master_Id());
			syslog.getLog("reply_Board_Number : "+board.getReply_Board_Number());
			syslog.getLog("reply_Board_Step : "+board.getReply_Board_Step());
			syslog.getLog("reply_Board_StartBoard : "+board.getReply_Board_StartBoard());
			
			board.setBoard_Title(Jsoup.clean(board.getBoard_Title(), Whitelist.basic()));
			board.setBoard_Content(Jsoup.clean(board.getBoard_Content(), Whitelist.basic()));
			board.setReply_Board_StartBoard(board.getBoard_Id());
	
			MultipartFile mfile = board.getFile();
			if(mfile!=null && !mfile.isEmpty()) {
				//logger.info("/board/reply : " + mfile.getOriginalFilename());
				BoardUploadFile file = new BoardUploadFile();
				file.setFile_Name(mfile.getOriginalFilename());
				file.setFile_Size(mfile.getSize());
				file.setFile_Content_Type(mfile.getContentType());
				file.setFile_Data(mfile.getBytes());
				//logger.info("/board/reply : " + file.toString());

				boardService.replyArticle(board, file);
			}else {
				
				boardService.replyArticle(board);
				
			}
		}catch(Exception e){
			e.printStackTrace();
			redirectAttrs.addFlashAttribute("message", e.getMessage());
		}
		
		if(session.getAttribute("page") != null) {
			return "redirect:/boardList/cat/"+board.getCategory_Id()+"/"+(Integer)session.getAttribute("page");
		}else {
			return "redirect:/boardList/cat/"+board.getCategory_Id(); 
		}
	}
	@RequestMapping("/file/{file_Id}")
	public ResponseEntity<byte[]> getFile(@PathVariable int file_Id) {
		BoardUploadFile file = boardService.getFile(file_Id);
		//logger.info("getFile " + file.toString());
		final HttpHeaders headers = new HttpHeaders();
		
		String[] mtypes = file.getFile_Content_Type().split("/");
		headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
		headers.setContentLength(file.getFile_Size());
		headers.setContentDispositionFormData("attachment", file.getFile_Name(), Charset.forName("UTF-8"));
		return new ResponseEntity<byte[]>(file.getFile_Data(), headers, HttpStatus.OK);
	}
	@RequestMapping("/boardSearch/{page}")
	public String search(@RequestParam(required=false, defaultValue="") String keyword, @PathVariable int page, HttpSession session, Model model) {
		try {
			List<Board> boardList = boardService.searchListByContentKeyword(keyword, page);
			model.addAttribute("boardList", boardList);
	
			// paging start
			int bbsCount = boardService.selectTotalArticleCountByKeyword(keyword);
			int totalPage = 0;
			System.out.println(bbsCount);
			if(bbsCount > 0) {
				totalPage= (int)Math.ceil(bbsCount/10.0);
			}
			model.addAttribute("totalPageCount", totalPage);
			model.addAttribute("page", page);
			model.addAttribute("keyword", keyword);
			//logger.info(totalPage + ":" + page + ":" + keyword);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "boardSearch/boardSearch";
	}
}
