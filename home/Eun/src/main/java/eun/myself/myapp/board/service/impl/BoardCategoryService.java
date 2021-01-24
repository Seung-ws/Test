package eun.myself.myapp.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eun.myself.myapp.board.dao.IBoardCategoryRepository;
import eun.myself.myapp.board.model.BoardCategory;
import eun.myself.myapp.board.service.IBoardCategoryService;

@Service
public class BoardCategoryService implements IBoardCategoryService{

	@Autowired
	IBoardCategoryRepository boardCategoryRepository;
	
	@Override
	public List<BoardCategory> selectAllCategory() {
		// 전체 카테고리 리스트를 불러온다.
		return boardCategoryRepository.selectAllCategory();
	}

	@Override
	public List<BoardCategory> selectAllCategoryByClass1(int class1) {
		// 전체 카테고리리스트의 ???
		return boardCategoryRepository.selectAllCategoryByClass1(class1);
	}

	@Override
	public void insertNewCategory(BoardCategory boardCategory) {
		// 새로운 카테고리를 삽입한다.
		boardCategory.setCategory_id(boardCategoryRepository.selectMaxCategoryId());
		boardCategoryRepository.insertNewCategory(boardCategory);
		
	}

	@Override
	public void updateCategory(BoardCategory boardCategory) {
		//카테고리 정보를 업데이트 한다.
		boardCategoryRepository.updateCategory(boardCategory);
	}

	@Override
	public void deleteCategory(int category_id) {
		// 카테고리를 삭제한다.
		boardCategoryRepository.deleteCategory(category_id);
	}
	

}
