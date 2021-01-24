package eun.myself.myapp.board.service;

import java.util.List;

import eun.myself.myapp.board.model.Board;
import eun.myself.myapp.board.model.BoardUploadFile;

public interface IBoardService {
	void insertArticle(Board board);
	void insertArticle(Board board, BoardUploadFile file);
	
	List<Board> selectArticleListByCategory(int category_id, int page);
	List<Board> selectArticleListByCategory(int category_id);
	
	Board selectArticle(int board_id);
	
	BoardUploadFile getFile(int file_id);
	
	void replyArticle(Board board);
	void replyArticle(Board board, BoardUploadFile file);

	String getPassword(int board_id);
	
	void updateArticle(Board board);
	void updateArticle(Board board, BoardUploadFile file);
	
	Board selectDeleteArticle(int board_id);
	void deleteArticle(int board_id,int master_id, int reply_Number);
	
	int selectTotalArticleCount();
	int selectTotalArticleCountByCategoryId(int category_id);
	
	List<Board> searchListByContentKeyword(String keyword, int page);
	int selectTotalArticleCountByKeyword(String keyword);
}