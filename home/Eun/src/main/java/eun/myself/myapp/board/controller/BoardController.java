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
		 * �Խù��� ī�װ�id���� �������  �Խù��� �ҷ����� ������������ �Խù��� ������ �����ش�.
		 * 
		 */
		//�Խù� ī�װ� ����Ʈ�� �ҷ����� ������
	
		//ī�װ� ���̵� ���� �������� ������ �޴´�.
		//���ǿ� �������� ��Requst�� ī�װ� ���̵� �ִ´�.
		session.setAttribute("page", page);
		model.addAttribute("category_Id", category_Id);
	
		List<Board> boardList = boardService.selectArticleListByCategory(category_Id, page);
		//�Խù� ����Ʈ�� ī�װ� ���� �����´�
		model.addAttribute("boardList", boardList);

		// paging start
		int bbsCount = boardService.selectTotalArticleCountByCategoryId(category_Id);
		//�� �Խù��� ���� ��ȸ�Ѵ�
		int totalPage = 0;

		if(bbsCount > 0) {
			totalPage= (int)Math.ceil(bbsCount/10.0);//���ų� �ּ� ������ ��ȯ�Ѵ�.
		}
	
		model.addAttribute("totalPageCount", totalPage);
		model.addAttribute("page", page);
	
		return "boardList/boardList";
	}
	
	@RequestMapping(value = "/boardList/cat/{category_Id}")
	public String getListByCategory(@PathVariable int category_Id,HttpSession session, Model model) {
		/*
		 * ī�װ��� ���õ� �Խù��� �����ִµ� �������� ���������ʾ� ù�������� ȣ���� �����ش�.
		 */
		syslog.getLog("->boardList/cat/"+category_Id);
		return getListByCategory(category_Id, 1, session, model);
	}
	
	@RequestMapping("/board/{board_Id}/{page}")	
	public String getBoardDetails(@PathVariable int board_Id, @PathVariable int page, Model model) {
		
		//�Խù��� ������ �������� ���� ����� �� �ֵ��� request���� �Է�
		Board board = boardService.selectArticle(board_Id);
		model.addAttribute("board", board);
		model.addAttribute("page", page);
		model.addAttribute("category_Id", board.getCategory_Id());
		syslog.getLog("--�Խñ� ����");
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
		//�Խù� ������ �ҷ����� ����Ʈ ������ 1�� �Է�
		syslog.getLog(">�Խù� ������ ����");
		return getBoardDetails(board_Id, 1, model);
	}
	
	@RequestMapping(value="/boardWrite/{category_Id}", method=RequestMethod.GET)
	public String writeArticle(@PathVariable int category_Id, Model model) {
		/*ī�װ����� �ҷ��´����� �Խù� ������ ���û��� ����Ʈ�� ���� �ִ´� �Խù� ���� ������ ȣ��
		 * ī�װ� ���̵�� �ʱ� ���� �������� �����ϱ� ���� �̰� �Խù� ���� ������ ȣ��
		 */
		
		List<BoardCategory> categoryList = categoryService.selectAllCategory();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("category_Id", category_Id);
		return "boardWrite/boardWrite";
	}
	

	@RequestMapping(value="/boardWrite", method=RequestMethod.POST)
	public String writeArticle(Board board, BindingResult result, RedirectAttributes redirectAttrs) {
		/*
		 * �Խù����� ���� �Խñ��� �ۼ��Ѵ�.
		 * �ۼ��� �ڿ��� �Խù� ������� ���ƿ´�.
		 */
		syslog.getLog("--�Խñ� ����");
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
		 * �Խù� ���̵� ���� �������� ���� �Խù��� ������ �������� �������� �������� ȣ�� 
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
		 * ���������������� db�� �Խù� ���̵� ����� ��й�ȣ�� ���� �� 
		 * �Խù� ���̵� ���� �������� ������ �����Ѵ�. ��ۿ� ���ؼ��� �߰������� �Է¹޾� ���� �����Ѵ�.
		 */
		syslog.getLog("�����ͻ���:"+board.getBoard_Master_Id());
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
		 * �Խù� ���� �Խù����̵� ��� ���� ������ ȣ�� �Ѵ� ī�װ� ������ ���� �ҷ��� �� ���õǾ��ִ� ī�װ��� 
		 * �Խù� ������ �ѱ��.
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
		 * �Խù� id ���� �������� ����� �ۼ��Ѵ�.
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
			syslog.getLog("--���� ����");
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
