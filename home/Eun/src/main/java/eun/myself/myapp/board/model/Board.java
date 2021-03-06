package eun.myself.myapp.board.model;



import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

public class Board {
	//�Խ���, ī�װ� 
	private int board_id;
	private int category_id;	
	//��, ���̵�
	private String writer;
	private String writer_id;
	//�Խù� ��� ����, ����, �ð�
	private String board_password;
	private String title;
	private String content;
	private String write_date;
	//�Խù� ���� ���� ����
	private int master_id;
	private int reply_parents_number;

	private int read_count;//�б� ����
	private int reply_number;// ���� ������ ���ؿ����� ����
	private int reply_step;//������ ���� 
	private int seq;
	private int page;
	private BoardCategory category;
	//��������
	private MultipartFile file;
	private int file_id;
	private String file_name;
	private long file_size;
	private String file_content_type;
	
	
	public int getBoard_id() {
		return board_id;
	}
	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getWriter_id() {
		return writer_id;
	}
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}
	public String getBoard_password() {
		return board_password;
	}
	public void setBoard_password(String board_password) {
		this.board_password = board_password;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriteDate() {
		return write_date;
	}
	public void setWriteDate(String write_date) {
		this.write_date = write_date;
	}
	public int getMaster_id() {
		return master_id;
	}
	public void setMaster_id(int master_id) {
		this.master_id = master_id;
	}
	public int getRead_count() {
		return read_count;
	}
	public void setRead_count(int read_count) {
		this.read_count = read_count;
	}
	public int getReply_number() {
		return reply_number;
	}
	public void setReply_number(int reply_number) {
		this.reply_number = reply_number;
	}
	public int getReply_step() {
		return reply_step;
	}
	public void setReply_step(int reply_step) {
		this.reply_step = reply_step;
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
	public int getFile_id() {
		return file_id;
	}
	public void setFile_id(int file_id) {
		this.file_id = file_id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public long getFile_size() {
		return file_size;
	}
	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}
	public String getFile_content_type() {
		return file_content_type;
	}
	public void setFile_content_type(String file_content_type) {
		this.file_content_type = file_content_type;
	}
	
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	public int getReply_parents_number() {
		return reply_parents_number;
	}
	public void setReply_parents_number(int reply_parents_number) {
		this.reply_parents_number = reply_parents_number;
	}
	
}
