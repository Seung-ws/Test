package eun.myself.myapp.board.dao;

import java.util.List;

import eun.myself.myapp.board.model.BoardCategory;

public interface IBoardCategoryRepository {
	int selectMaxCategoryId();
	List<BoardCategory> selectAllCategory();
	List<BoardCategory> selectAllCategoryByClass1(int category_Class1);
	void insertNewCategory(BoardCategory boardCategory);
	void updateCategory(BoardCategory boardCategory);
	void deleteCategory(int category_Id);
}
