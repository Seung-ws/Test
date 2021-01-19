package eun.myself.myapp.board.model;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

public class Board {
	//게시판, 카테고리 
	private int board_id;
	private int category_id;	
	//닉, 아이디
	private String writer;
	private String writer_id;
	//게시물 비번 제목, 내용, 시간
	private String board_password;
	private String title;
	private String content;
	private Timestamp writeDate;
	//게시물 레벨 리플 정보
	private int master_id;
	private int read_count;
	private int reply_number;
	private int reply_step;
	private int seq;
	private int page;
	private BoardCategory category;
	//파일정보
	private MultipartFile file;
	private String file_id;
	private String file_name;
	private long file_size;
	private String file_content_type;
	
	
	
	
}
