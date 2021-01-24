package eun.myself.myapp.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import eun.myself.myapp.board.model.Board;
import eun.myself.myapp.board.model.BoardUploadFile;

public interface IBoardRepository {
	int selectMaxArticleNo();
	int selectMaxReplyNo(@Param("master_id") int master_id);//최신리플을 아래쪽으로 쌓기 위해 체크
	int selectCustomMaxReplyNo(@Param("master_id") int master_id,@Param("reply_step")int reply_step,@Param("reply_parents_number")int reply_parents_number);
	int selectMaxSameStep(@Param("master_id") int master_id,@Param("reply_step")int reply_step,@Param("reply_parents_number")int reply_parents_number);
	
	int selectMaxFileId();
	
	void insertArticle(Board board);
	void insertFileData(BoardUploadFile file);
	
	List<Board> selectArticleListByCategory(@Param("category_id") int category_id, @Param("start") int start, @Param("end") int end);
	
	Board selectArticle(int board_id);
	BoardUploadFile getFile(int file_id);
		
	void updateReadCount(int board_id);

	void updateReplyNumber(@Param("master_id") int master_id, @Param("reply_number") int reply_number);
	void test(@Param("master_id") int master_id, @Param("reply_number") int reply_number);
	void replyArticle(Board board_id);
	
	String getPassword(int board_id);
	
	void updateArticle(Board board);
	void updateFileData(BoardUploadFile file);
	
	void deleteFileData(int board_id);
	void deleteReplyFileData(int board_id);
	Board selectDeleteArticle(int board_id);
	void deleteArticleByBoardId(int board_id);
	void deleteArticleByMasterId(int board_id);
	
	int selectTotalArticleCount();
	int selectTotalArticleCountByCategoryId(int category_id);

	int selectTotalArticleCountByKeyword(String keyword);
	List<Board> searchListByContentKeyword(@Param("keyword") String keyword, @Param("start") int start, @Param("end") int end);
}