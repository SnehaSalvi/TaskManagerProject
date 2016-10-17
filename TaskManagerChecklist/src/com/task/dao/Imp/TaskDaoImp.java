package com.task.dao.Imp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import com.task.dao.TaskDao;
import com.task.dto.Task;

public class TaskDaoImp implements TaskDao 
{
	private Connection conn;
	
	public TaskDaoImp()
	{
		super();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	@Override
	public boolean addTask(Task task) throws SQLException, IOException 
	{
		// TODO Auto-generated method stub
		return false;
	}

}
