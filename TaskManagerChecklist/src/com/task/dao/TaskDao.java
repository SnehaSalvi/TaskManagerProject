package com.task.dao;

import java.io.IOException;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.task.dto.Category;
import com.task.dto.Subtask;
import com.task.dto.Task;

public interface TaskDao 
{
	boolean addTask(Task  task)throws SQLException,IOException;
	List<Task> findAllTask() throws SQLException;
	public List<Task> findAllTaskByReminderToday(Date date,String time) throws SQLException;
	public List<Task> findAllTaskByReminder(Date date) throws SQLException;
	public Task getTaskName(int taskId) throws SQLException ;
	public void close();
}