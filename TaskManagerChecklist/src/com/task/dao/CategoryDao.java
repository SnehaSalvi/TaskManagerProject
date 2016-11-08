package com.task.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.task.dto.Category;


public interface CategoryDao 
{
	Category getCategoryName(String categoryName) throws SQLException;
	boolean addCategory(Category  category)throws SQLException,IOException;
	List<Category> findAllCategory() throws SQLException;
	public void close();
}
