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
		// TODO Auto-generated method stub
		return boardCategoryRepository.selectAllCategory();
	}

	@Override
	public List<BoardCategory> selectAllCategoryByClass1(int class1) {
		// TODO Auto-generated method stub
		return boardCategoryRepository.selectAllCategoryByClass1(class1);
	}

	@Override
	public void insertNewCategory(BoardCategory boardCategory) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void updateCategory(BoardCategory boardCategory) {
		// TODO Auto-generated method stub
		boardCategoryRepository.updateCategory(boardCategory);
	}

	@Override
	public void deleteCategory(int category_id) {
		// TODO Auto-generated method stub
		boardCategoryRepository.deleteCategory(category_id);
	}
	

}
