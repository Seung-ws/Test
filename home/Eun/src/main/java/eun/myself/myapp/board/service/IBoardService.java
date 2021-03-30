package eun.myself.myapp.board.service;

import java.util.List;

import eun.myself.myapp.board.model.Board;
import eun.myself.myapp.board.model.BoardUploadFile;

public interface IBoardService {
	void insertArticle(Board board);
	void insertArticle(Board board, BoardUploadFile file);
	
	List<Board> selectArticleListByCategory(int category_Id, int page);
	List<Board> selectArticleListByCategory(int category_Id);
	
	Board selectArticle(int board_Id);
	
	BoardUploadFile getFile(int file_Id);
	
	void replyArticle(Board board);
	void replyArticle(Board board, BoardUploadFile file);

	String getPassword(int board_Id);
	
	void updateArticle(Board board);
	void updateArticle(Board board, BoardUploadFile file);
	
	Board selectDeleteArticle(int board_id);
	void deleteArticle(int board_Id,int board_Master_Id, int reply_Board_Number);
	
	int selectTotalArticleCount();
	int selectTotalArticleCountByCategoryId(int category_Id);
	
	List<Board> searchListByContentKeyword(String keyword, int page);
	int selectTotalArticleCountByKeyword(String keyword);
}