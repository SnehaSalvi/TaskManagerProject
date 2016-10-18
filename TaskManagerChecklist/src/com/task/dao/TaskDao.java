package com.task.dao;

import java.io.IOException;
import java.sql.SQLException;

import com.task.dto.Task;

public interface TaskDao 
{
	boolean addTask(Task  task)throws SQLException,IOException;
	
}