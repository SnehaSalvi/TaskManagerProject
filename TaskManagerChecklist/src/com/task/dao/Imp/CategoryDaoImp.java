package com.task.dao.Imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.task.dao.CategoryDao;
import com.task.dto.Category;

public class CategoryDaoImp implements CategoryDao 
{
		private static final String Get_Category_By_Name = "SELECT id FROM mytaskdatabase.category where name=?";
		private Connection conn;
	
		public CategoryDaoImp() 
		{
			DataSource dataSource = new MysqlDataSource();
			try 
				{
					conn = dataSource.getConnection("root","root");
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
		}
	@Override
	public Category getCategoryName(String categoryName) throws SQLException 
	{
		PreparedStatement pstmt = conn.prepareStatement(Get_Category_By_Name);
		pstmt.setString(1, categoryName);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		Category category = new Category();
		category.setCategoryId(rs.getInt(1));
		category.setName(rs.getString(2));
		return category;
	}

	@Override
	public List<Category> findAllCategory() throws SQLException 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
