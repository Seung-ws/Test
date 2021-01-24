package eun.myself.myapp.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eun.myself.myapp.board.dao.IBoardRepository;
import eun.myself.myapp.board.model.Board;
import eun.myself.myapp.board.model.BoardUploadFile;
import eun.myself.myapp.board.service.IBoardService;

@Service
public class BoardService implements IBoardService{

	@Autowired
	@Qualifier("IBoardRepository")
	IBoardRepository boardRepository;
	
	//동시에 처리 될때 오류 발생에 대한 처리
	@Transactional
	public void insertArticle(Board board) {
		// 보드의 갯수를 산정해서 아이디를 겹치지 않게 지정하고 삽입한다.
		board.setBoard_id(boardRepository.selectMaxArticleNo()+1);
		boardRepository.insertArticle(board);
	}

	//동시에 처리 될때 오류 발생에 대한 처리
	@Transactional
	public void insertArticle(Board board, BoardUploadFile file) {
		// 보드의 갯수를 산정해서 아이디를 겹치지 않게 지정하고 삽입한다.
		board.setBoard_id(boardRepository.selectMaxArticleNo()+1);
		boardRepository.insertArticle(board);
	}

	@Override
	public List<Board> selectArticleListByCategory(int category_id, int page) {
		//
		int start=(page-1)*10;
		//시작 페이지를 계산하고 게시판에서 해당 페이지 카테고리 게시물을 10개를 가지고 온다. 가지고 온다.
		return boardRepository.selectArticleListByCategory(category_id, start, start+10);
	}

	@Override
	public List<Board> selectArticleListByCategory(int category_id) {
		// TODO Auto-generated method stub
		//별도의 페이지가 지정되지 않았을 때 100개 까지 게시물 페이지를 가지고 온다.
		return boardRepository.selectArticleListByCategory(category_id, 0, 100);
	}
	@Transactional
	public Board selectArticle(int board_id) {
		//해당 게시물을 확인시 조회수를 갱신하고 하고 게시물을 가지고 온다.
		boardRepository.updateReadCount(board_id);
		return boardRepository.selectArticle(board_id);
	}

	@Override
	public BoardUploadFile getFile(int file_id) {
		//게시물에 파일이 있다면 가지고 오도록 한다.
		return boardRepository.getFile(file_id);
	}

	@Transactional
	public void replyArticle(Board board) {
		//답글 순서를 업데이트하고 파일이 없는 답글 올리기 
		
		if(board.getReply_step()>0)
		{
			int sum =boardRepository.selectMaxSameStep(board.getMaster_id(),
					board.getReply_step()+1, board.getReply_number());
			int reply_no=boardRepository.selectCustomMaxReplyNo(board.getMaster_id(),
					board.getReply_step()+1, board.getReply_number());
			System.out.println("확인용 sum : "+sum);
			System.out.println("확인용 reply_no : "+reply_no);
			System.out.println("확인용 getReply_number : "+board.getReply_number());
			
			boardRepository.updateReplyNumber(board.getMaster_id(), board.getReply_number()+sum);	
			board.setBoard_id(boardRepository.selectMaxArticleNo()+1);
			board.setReply_parents_number(board.getReply_number());
			board.setReply_number(board.getReply_number()+1+sum);
			board.setReply_step(board.getReply_step()+1);	
		
		}
		else {
			//답글이 없는 게시물의 때 갱신방식
			board.setBoard_id(boardRepository.selectMaxArticleNo()+1);
			board.setReply_parents_number(board.getReply_number());
			board.setReply_number(boardRepository.selectMaxReplyNo(board.getMaster_id())+1);
			board.setReply_step(board.getReply_step()+1);	
		}
		
		
		boardRepository.replyArticle(board);
	}

	@Transactional
	public void replyArticle(Board board, BoardUploadFile file) {
		//답글 순서를 업데이트하고 파일이 없는 답글 올리기 
		boardRepository.updateReplyNumber(board.getMaster_id(), board.getReply_number());	
		board.setBoard_id(boardRepository.selectMaxArticleNo()+1);
		board.setReply_number(board.getReply_number()+1);
		board.setReply_step(board.getReply_step()+1);
		boardRepository.replyArticle(board);
		if(file != null && file.getFile_name() != null && !file.getFile_name().equals("")) {
        	file.setBoard_id(board.getBoard_id());
        	boardRepository.insertFileData(file);
        }
		
	}

	@Override
	public String getPassword(int board_id) {
		// 게시물 비밀번호를 가져온다
		return boardRepository.getPassword(board_id);
	}

	@Override
	public void updateArticle(Board board) {
		// 게시물을 업데이트 한다. 파일없음
		boardRepository.updateArticle(board);
	}

	@Transactional
	public void updateArticle(Board board, BoardUploadFile file) {
		// 게시물을 업데이트한다. 파일 있음
		boardRepository.updateArticle(board);
        if(file != null && file.getFile_name() != null && !file.getFile_name().equals("")) {
        	file.setBoard_id(board.getBoard_id());
//        	System.out.println(file.toString());
        	if(file.getFile_id()>0) {
        		boardRepository.updateFileData(file);
        	}else {
        		boardRepository.insertFileData(file);
        	}
        }
	}

	@Override
	public Board selectDeleteArticle(int board_id) {
		// 게시물을 지운다. 
		System.out.println("선택게시물지우기");
		
		return boardRepository.selectDeleteArticle(board_id);
	}

	@Transactional
	public void deleteArticle(int board_id,int master_id, int reply_Number) {
		// 게시물을 지우면 답글도 지우고 없으면 게시물만 지운다.
		System.out.println("게시물지우기");
		if(reply_Number>0) {
			System.out.println("master_id : "+master_id);
			System.out.println("reply_Number : "+reply_Number);
			
			boardRepository.test(master_id,reply_Number);
			boardRepository.deleteReplyFileData(board_id);
			boardRepository.deleteArticleByBoardId(board_id);
			
		}else if(reply_Number==0){
			boardRepository.deleteFileData(board_id);
			boardRepository.deleteArticleByMasterId(board_id);
		}else {
			throw new RuntimeException("WRONG_REPLYNUMBER");
		}
	}

	@Override
	public int selectTotalArticleCount() {
		// 전체 게시물의 숫자를 반환한다.
		return boardRepository.selectTotalArticleCount();
	}

	@Override
	public int selectTotalArticleCountByCategoryId(int category_id) {
		// 특정 카테고리의 게시물 숫자를 반환한다.
		return boardRepository.selectTotalArticleCountByCategoryId(category_id);
	}

	@Override
	public List<Board> searchListByContentKeyword(String keyword, int page) {
		// 키워드 검색 후 해당 페이지범위만큼 반환한다.
		int start = (page-1) * 10;
		return boardRepository.searchListByContentKeyword("%"+keyword+"%", start, start+10);
	}

	@Override
	public int selectTotalArticleCountByKeyword(String keyword) {
		// 키워드 검색시 게시물 수를 반환한다.
		return boardRepository.selectTotalArticleCountByKeyword("%"+keyword+"%");
	}
	
	
}
