package com.task.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.task.dto.Category;
import com.task.dto.Subtask;
import com.task.dto.Task;

public interface SubtaskDao 
{
	public List<Subtask> findAllItemByID(int taskId) throws SQLException;
	public List<Subtask> findAllItemStatus(int taskId) throws SQLException;
	public List findAllItem() throws SQLException;
	//Subtask getSubtaskById(int taskId) throws SQLException;
	boolean addSubtask(Subtask  subtask)throws SQLException,IOException;
	boolean updateSubtask(Subtask  subtask)throws SQLException,IOException;
	boolean updateSubtaskStatus(Subtask  subtask)throws SQLException,IOException;
	boolean deleteSubtask(Subtask  subtask)throws SQLException,IOException;
	public void close();
}
