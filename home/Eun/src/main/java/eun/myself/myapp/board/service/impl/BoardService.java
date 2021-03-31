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
	
	//���ÿ� ó�� �ɶ� ���� �߻��� ���� ó��
	@Transactional
	public void insertArticle(Board board) {
		// ������ ������ �����ؼ� ���̵� ��ġ�� �ʰ� �����ϰ� �����Ѵ�.
		board.setBoard_Id(boardRepository.selectMaxArticleNo()+1);
		boardRepository.insertArticle(board);
	}

	//���ÿ� ó�� �ɶ� ���� �߻��� ���� ó��
	@Transactional
	public void insertArticle(Board board, BoardUploadFile file) {
		// ������ ������ �����ؼ� ���̵� ��ġ�� �ʰ� �����ϰ� �����Ѵ�.
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
		//���� �������� ����ϰ� �Խ��ǿ��� �ش� ������ ī�װ� �Խù��� 10���� ������ �´�. ������ �´�.
		return boardRepository.selectArticleListByCategory(category_Id, start, start+10);
	}

	@Override
	public List<Board> selectArticleListByCategory(int category_Id) {
		// TODO Auto-generated method stub
		//������ �������� �������� �ʾ��� �� 100�� ���� �Խù� �������� ������ �´�.
		return boardRepository.selectArticleListByCategory(category_Id, 0, 100);
	}
	@Transactional
	public Board selectArticle(int board_Id) {
		//�ش� �Խù��� Ȯ�ν� ��ȸ���� �����ϰ� �ϰ� �Խù��� ������ �´�.
		boardRepository.updateReadCount(board_Id);
		return boardRepository.selectArticle(board_Id);
	}

	@Override
	public BoardUploadFile getFile(int file_Id) {
		//�Խù��� ������ �ִٸ� ������ ������ �Ѵ�.
		return boardRepository.getFile(file_Id);
	}

	@Transactional
	public void replyArticle(Board board) {
		//��� ������ ������Ʈ�ϰ� ������ ���� ��� �ø��� 
		//����� �ϳ��� ���� �� ...
		if(board.getReply_Board_Step()>0)
		{
			int sum=0;
				//���� ��� ������ ������ ��ȯ
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

		
				//���� ������ ��ȯ
			System.out.println("Ȯ�ο� sum : "+sum);
			System.out.println("Ȯ�ο� CEILINGNUM : "+ceil_Num);
			//System.out.println("Ȯ�ο� getReply_number : "+board.getReply_Board_Number());
			//�߰��� ����� �Խù��� ���� �ڷ� ��ĭ �� �аڴ� 
			boardRepository.updateReplyNumber(board.getBoard_Master_Id(), board.getReply_Board_Number()+sum);	
			//�ش� sum��ġ 
			board.setBoard_Id(boardRepository.selectMaxArticleNo()+1);//���� ���̵� �߰� 1
		//	board.setReply_Board_StartBoard(board.getReply_Board_Number());
			board.setReply_Board_Number(board.getReply_Board_Number()+1+sum);// ���� �ѹ�1�� �߰��ϸ鼭 
			board.setReply_Board_Step(board.getReply_Board_Step()+1);	
		
		}
		else {
			//����� ���� �Խù��� �� ���Ź��
			board.setBoard_Id(boardRepository.selectMaxArticleNo()+1);//�Խù� �ִ� ���̵� ����
			//board.setReply_Board_StartBoard(board.getReply_Board_Number());//�Խù� 
			board.setReply_Board_Number(boardRepository.selectMaxReplyNo(board.getBoard_Master_Id())+1);//��� ���� 0->1
			board.setReply_Board_Step(board.getReply_Board_Step()+1);	//�Խù� ��۽���0->1��
			
		}
		
		
		boardRepository.replyArticle(board);
	}

	@Transactional
	public void replyArticle(Board board, BoardUploadFile file) {
		//��� ������ ������Ʈ�ϰ� ������ ���� ��� �ø��� 

		if(board.getReply_Board_Step()>0)
		{
			int sum=0;
			//���� ��� ������ ������ ��ȯ
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
			
			System.out.println("Ȯ�ο� sum : "+sum);
			System.out.println("Ȯ�ο� reply_no : "+ceil_Num);
			System.out.println("Ȯ�ο� getReply_number : "+board.getReply_Board_Number());
			
			boardRepository.updateReplyNumber(board.getBoard_Master_Id(), board.getReply_Board_Number()+sum);	
			board.setBoard_Id(boardRepository.selectMaxArticleNo()+1);
			board.setReply_Board_StartBoard(board.getReply_Board_Number());
			//������ ������ �ø��� ������ ������ �ø���.
			board.setReply_Board_Number(board.getReply_Board_Number()+1+sum);
			board.setReply_Board_Step(board.getReply_Board_Step()+1);	
		
		}
		else {
			//����� ���� �Խù��� �� ���Ź��
			board.setBoard_Id(boardRepository.selectMaxArticleNo()+1);
			
			board.setReply_Board_StartBoard(board.getReply_Board_Number());
			
			board.setReply_Board_Number(boardRepository.selectMaxReplyNo(board.getBoard_Master_Id())+1);
			
			board.setReply_Board_Step(board.getReply_Board_Step()+1);	
		}
		boardRepository.replyArticle(board);
		System.out.println("���� id : "+file.getFile_Id());
		if(file != null && file.getFile_Name() != null && !file.getFile_Name().equals("")) {
        	file.setBoard_Id(board.getBoard_Id());
           	file.setFile_Id(boardRepository.selectMaxFileId()+1);
        	boardRepository.insertFileData(file);
        }
		
	}

	@Override
	public String getPassword(int board_Id) {
		// �Խù� ��й�ȣ�� �����´�
		return boardRepository.getPassword(board_Id);
	}

	@Override
	public void updateArticle(Board board) {
		// �Խù��� ������Ʈ �Ѵ�. ���Ͼ���
		boardRepository.updateArticle(board);
	}

	@Transactional
	public void updateArticle(Board board, BoardUploadFile file) {
		// �Խù��� ������Ʈ�Ѵ�. ���� ����
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
		// �Խù��� �����. 
		System.out.println("���ðԽù������");
		
		return boardRepository.selectDeleteArticle(board_Id);
	}

	@Transactional
	public void deleteArticle(int board_Id,int board_Master_Id, int reply_Board_Number) {
		// �Խù��� ����� ��۵� ����� ������ �Խù��� �����.
		System.out.println("�Խù������");
		if(reply_Board_Number>0) {
			System.out.println("master_id : "+board_Master_Id);
			System.out.println("reply_Number : "+reply_Board_Number);
			
			boardRepository.test(board_Master_Id,reply_Board_Number);
			boardRepository.deleteReplyFileData(board_Id);
			boardRepository.deleteArticleByBoardId(board_Id);
			
		}else if(reply_Board_Number==0){
			//���� �����;��̵� �������� ��� ����
			boardRepository.deleteFileData(board_Id);
			boardRepository.deleteArticleByMasterId(board_Id);
		}else {
			throw new RuntimeException("WRONG_REPLYNUMBER");
		}
	}

	@Override
	public int selectTotalArticleCount() {
		// ��ü �Խù��� ���ڸ� ��ȯ�Ѵ�.
		return boardRepository.selectTotalArticleCount();
	}

	@Override
	public int selectTotalArticleCountByCategoryId(int category_Id) {
		// Ư�� ī�װ��� �Խù� ���ڸ� ��ȯ�Ѵ�.
		return boardRepository.selectTotalArticleCountByCategoryId(category_Id);
	}

	@Override
	public List<Board> searchListByContentKeyword(String keyword, int page) {
		// Ű���� �˻� �� �ش� ������������ŭ ��ȯ�Ѵ�.
		int start = (page-1) * 10;
		return boardRepository.searchListByContentKeyword("%"+keyword+"%", start, start+10);
	}

	@Override
	public int selectTotalArticleCountByKeyword(String keyword) {
		// Ű���� �˻��� �Խù� ���� ��ȯ�Ѵ�.
		return boardRepository.selectTotalArticleCountByKeyword("%"+keyword+"%");
	}
	
	
}
