package com.task.dao.Imp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.task.dao.TaskDao;
import com.task.dto.Task;

public class TaskDaoImp implements TaskDao 
{
	private Connection conn;
	private static final String INSERT_Task = "INSERT INTO mytaskdatabase.task(id,categoryid,name,time,reminder,status,description) VALUES(?,?,?,?,?,?,?)";
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
			//pstmt.setDate(4,(Date) task.getDate());
			pstmt.setString(4, task.getTime());
			pstmt.setInt(5, task.getReminder());
			pstmt.setString(6, task.getStatus());  
			pstmt.setString(7, task.getDescription());  
			 int result=pstmt.executeUpdate();
	                if(result>=1)
	                {
	                    return true;
	                }
	                return false;
		
	}

}
