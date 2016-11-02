package com.task.dao.Imp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.task.dao.TaskDao;
import com.task.dto.Category;
import com.task.dto.Task;

public class TaskDaoImp implements TaskDao 
{
	private Connection conn;
	private static final String INSERT_Task = "INSERT INTO mytaskdatabase.task(id,categoryid,name,date,time,reminder,status,description) VALUES(?,?,?,?,?,?,?,?)";
	private static final String Select_All_Task ="SELECT * FROM mytaskdatabase.task";
	public TaskDaoImp()
	{
		super();
		DataSource dataSource=new MysqlDataSource();
		try {
			conn=dataSource.getConnection("root","root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean addTask(Task task) throws SQLException, IOException 
	{
		 PreparedStatement pstmt = conn.prepareStatement(INSERT_Task);
		 	pstmt.setInt(1, task.getId());
		 	pstmt.setInt(2, task.getCategoryId());
			pstmt.setString(3, task.getName());
			pstmt.setDate(4,new java.sql.Date(task.getDate().getTime()));
			pstmt.setString(5, task.getTime());
			pstmt.setInt(6, task.getReminder());
			pstmt.setString(7, task.getStatus());  
			pstmt.setString(8, task.getDescription());  
			 int result=pstmt.executeUpdate();
	                if(result>=1)
	                {
	                    return true;
	                }
	                return false;
		
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

	@Override
	public List<Task> findAllTask() throws SQLException 
	{
		List<Task> listOfTask = new ArrayList<Task>();
		PreparedStatement pstmt=conn.prepareStatement(Select_All_Task);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			
			Task task = new Task();
			task.setId(rs.getInt("id"));
			task.setName(rs.getString("name"));
			
			listOfTask.add(task);
		}
		return listOfTask;
	}

}
