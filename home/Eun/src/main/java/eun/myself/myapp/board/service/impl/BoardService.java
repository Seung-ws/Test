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
		board.setBoard_Id(boardRepository.selectMaxArticleNo()+1);
		boardRepository.insertArticle(board);
	}

	//동시에 처리 될때 오류 발생에 대한 처리
	@Transactional
	public void insertArticle(Board board, BoardUploadFile file) {
		// 보드의 갯수를 산정해서 아이디를 겹치지 않게 지정하고 삽입한다.
		board.setBoard_Id(boardRepository.selectMaxArticleNo()+1);
		  
		boardRepository.insertArticle(board);
		if(file != null && file.getFile_Name() != null && !file.getFile_Name().equals("")) {
        	file.setBoard_Id(board.getBoard_Id());
        	file.setFile_Id(boardRepository.selectMaxFileId()+1);
        	boardRepository.insertFileData(file);
        }
	}

	@Override
	public List<Board> selectArticleListByCategory(int category_Id, int page) {
		//
		int start=(page-1)*10;
		//시작 페이지를 계산하고 게시판에서 해당 페이지 카테고리 게시물을 10개를 가지고 온다. 가지고 온다.
		return boardRepository.selectArticleListByCategory(category_Id, start, start+10);
	}

	@Override
	public List<Board> selectArticleListByCategory(int category_Id) {
		// TODO Auto-generated method stub
		//별도의 페이지가 지정되지 않았을 때 100개 까지 게시물 페이지를 가지고 온다.
		return boardRepository.selectArticleListByCategory(category_Id, 0, 100);
	}
	@Transactional
	public Board selectArticle(int board_Id) {
		//해당 게시물을 확인시 조회수를 갱신하고 하고 게시물을 가지고 온다.
		boardRepository.updateReadCount(board_Id);
		return boardRepository.selectArticle(board_Id);
	}

	@Override
	public BoardUploadFile getFile(int file_Id) {
		//게시물에 파일이 있다면 가지고 오도록 한다.
		return boardRepository.getFile(file_Id);
	}

	@Transactional
	public void replyArticle(Board board) {
		//답글 순서를 업데이트하고 파일이 없는 답글 올리기 
		//답글이 하나라도 있을 떈 ...
		if(board.getReply_Board_Step()>0)
		{
			int sum=0;
				//다음 답글 스탭의 갯수를 반환
			Integer ceil_Num =ceil_Num=(boardRepository.selectCustomMaxReplyNo(board.getBoard_Master_Id(),
					board.getReply_Board_Step(), board.getReply_Board_Number()));
			if(ceil_Num==null)
			{
				sum=boardRepository.selectMaxZeroSameStep(board.getBoard_Master_Id(),
						board.getReply_Board_Step(), board.getReply_Board_Number());	
			}else
			{
				sum =boardRepository.selectMaxCeilSameStep(board.getBoard_Master_Id(),
						board.getReply_Board_Step(), board.getReply_Board_Number(),ceil_Num);
			}

		
				//리플 갯수를 반환
			System.out.println("확인용 sum : "+sum);
			System.out.println("확인용 CEILINGNUM : "+ceil_Num);
			//System.out.println("확인용 getReply_number : "+board.getReply_Board_Number());
			//중간에 생기는 게시물에 대해 뒤로 한칸 씩 밀겠다 
			boardRepository.updateReplyNumber(board.getBoard_Master_Id(), board.getReply_Board_Number()+sum);	
			//해당 sum위치 
			board.setBoard_Id(boardRepository.selectMaxArticleNo()+1);//보더 아이디 추가 1
		//	board.setReply_Board_StartBoard(board.getReply_Board_Number());
			board.setReply_Board_Number(board.getReply_Board_Number()+1+sum);// 보더 넘버1개 추가하면서 
			board.setReply_Board_Step(board.getReply_Board_Step()+1);	
		
		}
		else {
			//답글이 없는 게시물의 때 갱신방식
			board.setBoard_Id(boardRepository.selectMaxArticleNo()+1);//게시물 최대 아이디 지정
			//board.setReply_Board_StartBoard(board.getReply_Board_Number());//게시물 
			board.setReply_Board_Number(boardRepository.selectMaxReplyNo(board.getBoard_Master_Id())+1);//답글 갯수 0->1
			board.setReply_Board_Step(board.getReply_Board_Step()+1);	//게시물 답글스탭0->1로
			
		}
		
		
		boardRepository.replyArticle(board);
	}

	@Transactional
	public void replyArticle(Board board, BoardUploadFile file) {
		//답글 순서를 업데이트하고 파일이 없는 답글 올리기 

		if(board.getReply_Board_Step()>0)
		{
			int sum=0;
			//다음 답글 스탭의 갯수를 반환
			Integer ceil_Num =ceil_Num=(boardRepository.selectCustomMaxReplyNo(board.getBoard_Master_Id(),
					board.getReply_Board_Step(), board.getReply_Board_Number()));
			if(ceil_Num==null)
			{
				sum=boardRepository.selectMaxZeroSameStep(board.getBoard_Master_Id(),
						board.getReply_Board_Step(), board.getReply_Board_Number());	
			}else
			{
				sum =boardRepository.selectMaxCeilSameStep(board.getBoard_Master_Id(),
						board.getReply_Board_Step(), board.getReply_Board_Number(),ceil_Num);
			}
			
			System.out.println("확인용 sum : "+sum);
			System.out.println("확인용 reply_no : "+ceil_Num);
			System.out.println("확인용 getReply_number : "+board.getReply_Board_Number());
			
			boardRepository.updateReplyNumber(board.getBoard_Master_Id(), board.getReply_Board_Number()+sum);	
			board.setBoard_Id(boardRepository.selectMaxArticleNo()+1);
			board.setReply_Board_StartBoard(board.getReply_Board_Number());
			//리플의 갯수를 늘리고 리플의 스탭을 늘린다.
			board.setReply_Board_Number(board.getReply_Board_Number()+1+sum);
			board.setReply_Board_Step(board.getReply_Board_Step()+1);	
		
		}
		else {
			//답글이 없는 게시물의 때 갱신방식
			board.setBoard_Id(boardRepository.selectMaxArticleNo()+1);
			
			board.setReply_Board_StartBoard(board.getReply_Board_Number());
			
			board.setReply_Board_Number(boardRepository.selectMaxReplyNo(board.getBoard_Master_Id())+1);
			
			board.setReply_Board_Step(board.getReply_Board_Step()+1);	
		}
		boardRepository.replyArticle(board);
		System.out.println("파일 id : "+file.getFile_Id());
		if(file != null && file.getFile_Name() != null && !file.getFile_Name().equals("")) {
        	file.setBoard_Id(board.getBoard_Id());
           	file.setFile_Id(boardRepository.selectMaxFileId()+1);
        	boardRepository.insertFileData(file);
        }
		
	}

	@Override
	public String getPassword(int board_Id) {
		// 게시물 비밀번호를 가져온다
		return boardRepository.getPassword(board_Id);
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
        if(file != null && file.getFile_Name() != null && !file.getFile_Name().equals("")) {
        	file.setBoard_Id(board.getBoard_Id());
//        	System.out.println(file.toString());
        	if(file.getFile_Id()>0) {
        		boardRepository.updateFileData(file);
        	}else {
        		boardRepository.insertFileData(file);
        	}
        }
	}

	@Override
	public Board selectDeleteArticle(int board_Id) {
		// 게시물을 지운다. 
		System.out.println("선택게시물지우기");
		
		return boardRepository.selectDeleteArticle(board_Id);
	}

	@Transactional
	public void deleteArticle(int board_Id,int board_Master_Id, int reply_Board_Number) {
		// 게시물을 지우면 답글도 지우고 없으면 게시물만 지운다.
		System.out.println("게시물지우기");
		if(reply_Board_Number>0) {
			System.out.println("master_id : "+board_Master_Id);
			System.out.println("reply_Number : "+reply_Board_Number);
			
			boardRepository.test(board_Master_Id,reply_Board_Number);
			boardRepository.deleteReplyFileData(board_Id);
			boardRepository.deleteArticleByBoardId(board_Id);
			
		}else if(reply_Board_Number==0){
			//같은 마스터아이디를 가진것을 모두 제거
			boardRepository.deleteFileData(board_Id);
			boardRepository.deleteArticleByMasterId(board_Id);
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
	public int selectTotalArticleCountByCategoryId(int category_Id) {
		// 특정 카테고리의 게시물 숫자를 반환한다.
		return boardRepository.selectTotalArticleCountByCategoryId(category_Id);
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
