package eun.myself.myapp.board.model;

public class BoardUploadFile {
	
	//파일 아이디 보드 아이디
	private int file_Id;
	private int board_Id;
	
	private String file_Name;
	private long file_Size;
	private String file_Content_Type;
	private byte[] file_Data;
	public int getFile_Id() {
		return file_Id;
	}
	public void setFile_Id(int file_Id) {
		this.file_Id = file_Id;
	}
	public int getBoard_Id() {
		return board_Id;
	}
	public void setBoard_Id(int board_Id) {
		this.board_Id = board_Id;
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
	public byte[] getFile_Data() {
		return file_Data;
	}
	public void setFile_Data(byte[] file_Data) {
		this.file_Data = file_Data;
	}
	
}
