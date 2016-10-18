package com.task.dao;

import java.sql.SQLException;
import java.util.List;

import com.task.dto.Category;

public interface CategoryDao 
{
	Category getCategoryName(String categoryName) throws SQLException;
	List<Category> findAllCategory() throws SQLException;
}
