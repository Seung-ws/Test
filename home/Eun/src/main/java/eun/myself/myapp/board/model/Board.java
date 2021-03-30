package eun.myself.myapp.board.model;



import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

public class Board {
	//게시판, 카테고리 
	private int board_Id;
	private int category_Id;	
	//닉, 아이디
	private String board_Writer;
	private String board_Writer_Id;
	//게시물 비번 제목, 내용, 시간
	private String board_Password;
	private String board_Title;
	private String board_Content;
	private String board_WriteDate;
	//게시물 레벨 리플 정보
	private int board_Master_Id;
	private int reply_Board_StartBoard;//스타트 보드삭제로 답글 전체삭제


	private int reply_Board_Number;// 같은 리플의 수준에서의 순서
	private int reply_Board_Step;//리플의 수준 
	private int read_Count;//읽기 정보
	
	private int seq;//db엔 없다.게시글을 가져올 때 호출한다
	private int page;//db엔 없다.게시글을 가져올 때 호출한다
	private BoardCategory category; //db엔 없다.게시글을 가져올 때 호출한다
	
	//파일정보//db엔 없다.게시글을 가져올 때 호출한다
	private MultipartFile file;
	private int file_Id;
	private String file_Name;
	private long file_Size;
	private String file_Content_Type;
	
	public int getBoard_Id() {
		return board_Id;
	}
	public void setBoard_Id(int board_Id) {
		this.board_Id = board_Id;
	}
	public int getCategory_Id() {
		return category_Id;
	}
	public void setCategory_Id(int category_Id) {
		this.category_Id = category_Id;
	}
	
	public String getBoard_Writer() {
		return board_Writer;
	}
	public void setBoard_Writer(String board_Writer) {
		this.board_Writer = board_Writer;
	}
	public String getBoard_Writer_Id() {
		return board_Writer_Id;
	}
	public void setBoard_Writer_Id(String board_Writer_Id) {
		this.board_Writer_Id = board_Writer_Id;
	}
	public String getBoard_Password() {
		return board_Password;
	}
	public void setBoard_Password(String board_Password) {
		this.board_Password = board_Password;
	}
	public String getBoard_Title() {
		return board_Title;
	}
	public void setBoard_Title(String board_Title) {
		this.board_Title = board_Title;
	}
	public String getBoard_Content() {
		return board_Content;
	}
	public void setBoard_Content(String board_Content) {
		this.board_Content = board_Content;
	}
	public String getBoard_WriteDate() {
		return board_WriteDate;
	}
	public void setBoard_WriteDate(String board_WriteDate) {
		this.board_WriteDate = board_WriteDate;
	}
	public int getBoard_Master_Id() {
		return board_Master_Id;
	}
	public void setBoard_Master_Id(int board_Master_Id) {
		this.board_Master_Id = board_Master_Id;
	}
	public int getReply_Board_StartBoard() {
		return reply_Board_StartBoard;
	}
	public void setReply_Board_StartBoard(int reply_Board_StartBoard) {
		this.reply_Board_StartBoard = reply_Board_StartBoard;
	}
	public int getReply_Board_Number() {
		return reply_Board_Number;
	}
	public void setReply_Board_Number(int reply_Board_Number) {
		this.reply_Board_Number = reply_Board_Number;
	}
	public int getReply_Board_Step() {
		return reply_Board_Step;
	}
	public void setReply_Board_Step(int reply_board_Step) {
		this.reply_Board_Step = reply_board_Step;
	}
	public int getRead_Count() {
		return read_Count;
	}
	public void setRead_Count(int read_Count) {
		this.read_Count = read_Count;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public BoardCategory getCategory() {
		return category;
	}
	public void setCategory(BoardCategory category) {
		this.category = category;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public int getFile_Id() {
		return file_Id;
	}
	public void setFile_Id(int file_Id) {
		this.file_Id = file_Id;
	}
	public String getFile_Name() {
		return file_Name;
	}
	public void setFile_Name(String file_Name) {
		this.file_Name = file_Name;
	}
	public long getFile_Size() {
		return file_Size;
	}
	public void setFile_Size(long file_Size) {
		this.file_Size = file_Size;
	}
	public String getFile_Content_Type() {
		return file_Content_Type;
	}
	public void setFile_Content_Type(String file_Content_Type) {
		this.file_Content_Type = file_Content_Type;
	}
	
	
	
	
}
