package com.task.dao.Imp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.task.dao.CategoryDao;
import com.task.dto.Category;
import com.task.dto.Task;

public class CategoryDaoImp implements CategoryDao 
{
		private static final String Get_Category_By_Name = "SELECT id FROM mytaskdatabase.category where name=?";
		private static final String Select_All_Category = "SELECT * FROM mytaskdatabase.category";
		private static final String INSERT_Cat = "INSERT INTO mytaskdatabase.category(id,name) VALUES(?,?)";
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
		public boolean addCategory(Category category) throws SQLException, IOException 
		{
			PreparedStatement pstmt = conn.prepareStatement(INSERT_Cat);
		 	pstmt.setInt(1, category.getCategoryId());
		 
			pstmt.setString(2, category.getName());
			
			 int result=pstmt.executeUpdate();
	                if(result>=1)
	                {
	                    return true;
	                }
	                return false;
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
		List<Category> listOfCategory = new ArrayList<Category>();
		PreparedStatement pstmt=conn.prepareStatement(Select_All_Category);
		ResultSet rs = pstmt.executeQuery();
		//System.out.println(rs.);
		while (rs.next()) {
			
			Category category = new Category();
			category.setName(rs.getString("name"));
		System.out.println(rs.getString("name"));
			listOfCategory.add(category);
		}
		return listOfCategory;
	}

	@Override
	public void close() 
	{
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
