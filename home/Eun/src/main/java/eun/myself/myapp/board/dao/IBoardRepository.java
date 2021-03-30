package eun.myself.myapp.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import eun.myself.myapp.board.model.Board;
import eun.myself.myapp.board.model.BoardUploadFile;

public interface IBoardRepository {
	int selectMaxArticleNo();
	int selectMaxReplyNo(@Param("board_Master_Id") int board_Master_Id);//최신리플을 아래쪽으로 쌓기 위해 체크
	int selectCustomMaxReplyNo(@Param("board_Master_Id") int board_Master_Id,@Param("reply_Board_Step")int reply_Board_Step,@Param("reply_Board_StartBoard")int reply_Board_StartBoard);
	int selectMaxSameStep(@Param("board_Master_Id") int board_Master_Id,@Param("reply_Board_Step")int reply_Board_Step,@Param("reply_parents_number")int reply_parents_number);
	
	int selectMaxFileId();
	
	void insertArticle(Board board);
	void insertFileData(BoardUploadFile file);
	
	List<Board> selectArticleListByCategory(@Param("category_Id") int category_Id, @Param("start") int start, @Param("end") int end);
	
	Board selectArticle(int board_Id);
	BoardUploadFile getFile(int file_Id);
		
	void updateReadCount(int board_Id);

	void updateReplyNumber(@Param("board_Master_Id") int board_Master_Id, @Param("reply_Board_Number") int reply_Board_Number);
	void test(@Param("board_Master_Id") int board_Master_Id, @Param("reply_Board_Number") int reply_Board_Number);
	void replyArticle(Board board_Id);
	
	String getPassword(int board_Id);
	
	void updateArticle(Board board);
	void updateFileData(BoardUploadFile file);
	
	void deleteFileData(int board_Id);
	void deleteReplyFileData(int board_Id);
	Board selectDeleteArticle(int board_Id);
	void deleteArticleByBoardId(int board_Id);
	void deleteArticleByMasterId(int board_Id);
	
	int selectTotalArticleCount();
	int selectTotalArticleCountByCategoryId(int category_Id);

	int selectTotalArticleCountByKeyword(String keyword);
	List<Board> searchListByContentKeyword(@Param("keyword") String keyword, @Param("start") int start, @Param("end") int end);
}