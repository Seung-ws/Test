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
		//리플순서를 업데이트하고 게시판 아이디와 숫자
		
	}

	@Override
	public void replyArticle(Board board, BoardUploadFile file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPassword(int board_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateArticle(Board board) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateArticle(Board board, BoardUploadFile file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Board selectDeleteArticle(int board_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteArticle(int board_id, int reply_Number) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int selectTotalArticleCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int selectTotalArticleCountByCategoryId(int category_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Board> searchListByContentKeyword(String keyword, int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectTotalArticleCountByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
