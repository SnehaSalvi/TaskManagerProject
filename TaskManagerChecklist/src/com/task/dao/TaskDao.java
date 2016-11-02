package com.task.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.task.dto.Category;
import com.task.dto.Task;

public interface TaskDao 
{
	boolean addTask(Task  task)throws SQLException,IOException;
	List<Task> findAllTask() throws SQLException;
	public void close();
}