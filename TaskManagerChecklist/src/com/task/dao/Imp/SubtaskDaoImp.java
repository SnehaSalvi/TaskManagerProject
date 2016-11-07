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
import com.task.dao.SubtaskDao;
import com.task.dto.Subtask;
import com.task.dto.Task;

public class SubtaskDaoImp implements SubtaskDao 
{
	private Connection conn;
	private static final String INSERT_SubTask = "INSERT INTO mytaskdatabase.subtask(id,taskid,name,status) VALUES(?,?,?,?)";
	private static final String GET_TaskItem = "select s.id,s.taskid,s.name,s.status from mytaskdatabase.task t, mytaskdatabase.subtask s where t.id=s.taskid and s.status='Item Scheduled' and t.id=?";
	private static final String GET_TaskItem_Status = "select s.id,s.taskid,s.name,s.status from mytaskdatabase.task t, mytaskdatabase.subtask s where t.id=s.taskid and s.status='Item Completed' and t.id=?";
	private static final String UPDATE_Subtask = "UPDATE mytaskdatabase.subtask SET taskid=?,name=?,status=? where id=?";
	private static final String UPDATE_Subtask_Status = "UPDATE mytaskdatabase.subtask SET status='Item Completed' where id=?";
	private static final String Delete_Subtask = "DELETE FROM mytaskdatabase.subtask where id=?";
	private static final String GET_All_Task = "select name from mytaskdatabase.task t, mytaskdatabase.subtask s where t.id=s.taskid";
	public SubtaskDaoImp() 
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
	public boolean addSubtask(Subtask subtask) throws SQLException, IOException 
	{
		PreparedStatement pstmt = conn.prepareStatement(INSERT_SubTask);
	 	pstmt.setInt(1, subtask.getSubtaskID());
	 	pstmt.setInt(2, subtask.getTaskId());
		pstmt.setString(3, subtask.getName());
		pstmt.setString(4, subtask.getStatus());  
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

	/*@Override
	public Subtask getSubtaskById(int taskId) throws SQLException 
	{
			PreparedStatement pstmt = conn.prepareStatement(GET_TaskItem);
			pstmt.setInt(1, taskId);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			Subtask sub=new Subtask();
			sub.setName(rs.getString(1));
			
			return sub;
		
	}*/

	@Override
	public List<Subtask> findAllItemByID(int taskId) throws SQLException 
	{
		List<Subtask> listOfItem = new ArrayList<Subtask>();
		PreparedStatement pstmt = conn.prepareStatement(GET_TaskItem);
		pstmt.setInt(1, taskId);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			
			Subtask sub=new Subtask();
			sub.setSubtaskID(rs.getInt(1));
			sub.setTaskId(rs.getInt(2));
			
			sub.setName(rs.getString(3));
			sub.setStatus(rs.getString(4));
			listOfItem.add(sub);
		}
		
		return listOfItem;
	}

	@Override
	public boolean updateSubtask(Subtask subtask) throws SQLException, IOException 
	{
		 PreparedStatement pstmt = conn.prepareStatement(UPDATE_Subtask);
	        
         
			pstmt.setInt(1, subtask.getTaskId());
			pstmt.setString(2, subtask.getName());
			pstmt.setString(3, subtask.getStatus());
			pstmt.setInt(4, subtask.getSubtaskID());
	                int result=pstmt.executeUpdate();
	                if(result>=1)
	                {
	                    return true;
	                }
	                return false;
	}

	@Override
	public boolean deleteSubtask(Subtask subtask) throws SQLException, IOException 
	{
		PreparedStatement pstmt = conn.prepareStatement(Delete_Subtask);
      
		pstmt.setInt(1, subtask.getSubtaskID());
                int result=pstmt.executeUpdate();
                if(result>=1)
                {
                    return true;
                }
                return false;
	}

	@Override
	public List findAllItem() throws SQLException 
	{
		List listOfItem = new ArrayList();
		PreparedStatement pstmt = conn.prepareStatement(GET_All_Task);
	
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Task task=new Task();
			task.setTaskId(rs.getInt(1));
			task.setName(rs.getString(2));
			Subtask sub=new Subtask();
			sub.setSubtaskID(rs.getInt(3));
			
			sub.setName(rs.getString(4));
			listOfItem.add(task);
			listOfItem.add(sub);
		}
		
		return listOfItem;
	}

	@Override
	public List<Subtask> findAllItemStatus(int taskId) throws SQLException 
	{
		List<Subtask> listOfItem = new ArrayList<Subtask>();
		PreparedStatement pstmt = conn.prepareStatement(GET_TaskItem_Status);
		pstmt.setInt(1, taskId);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			
			Subtask sub=new Subtask();
			sub.setSubtaskID(rs.getInt(1));
			sub.setTaskId(rs.getInt(2));
			
			sub.setName(rs.getString(3));
			sub.setStatus(rs.getString(4));
			listOfItem.add(sub);
		}
		
		return listOfItem;
	}

	@Override
	public boolean updateSubtaskStatus(Subtask subtask) throws SQLException, IOException 
	{
		 PreparedStatement pstmt = conn.prepareStatement(UPDATE_Subtask_Status);
	        
         
			pstmt.setInt(1, subtask.getTaskId());
			pstmt.setString(2, subtask.getName());
			pstmt.setString(3, subtask.getStatus());
			pstmt.setInt(4, subtask.getSubtaskID());
	                int result=pstmt.executeUpdate();
	                if(result>=1)
	                {
	                    return true;
	                }
	                return false;
	}

}
